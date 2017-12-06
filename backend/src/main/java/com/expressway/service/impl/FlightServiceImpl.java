package com.expressway.service.impl;

import com.expressway.model.FlightSearch;
import com.expressway.model.Flight;
import com.expressway.model.Leg;
import com.expressway.service.FlightService;
import com.expressway.util.ConnectionUtil;
import com.expressway.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private ConnectionUtil connectionUtil;

    @Autowired
    private Helper helper;


    /**
     * @param airportId
     * @return
     */
    public List<Map<String, Object>> getFlightsForAirport(String airportId) {

        String query = "SELECT airline_id, F.flight_number, leg_number, from_airport, to_airport, " +
                "departure_time, arrival_time, seating_capacity " +
                "FROM Legs L, Flight F " +
                "WHERE L.flight_number = F.flight_number " +
                "AND L.from_airport = ? " +
                "AND L.airline_id = F.airline;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            // set parameters and execute
            ps.setString(1, airportId);
            rs = ps.executeQuery();

            List<Map<String, Object>> data = helper.converResultToList(rs);

            return data;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }

    @Override
    public List<String> getSeatsReservedOnFlight(String airline, int flightNumber) {

        String query = "SELECT DISTINCT C.account_number, I.seat_number " +
                "FROM Flight F, Customer C, Include I, Reservations R " +
                "WHERE F.flight_number = ? " +
                "AND F.airline = ? " +
                "AND I.flight_number = F.flight_number " +
                "AND I.airline_id = F.airline " +
                "AND R.reservation_number = I.reservation_number  " +
                "AND R.account_number = C.account_number;";


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            // set parameters and execute
            ps.setInt(1, flightNumber);
            ps.setString(2, airline);
            rs = ps.executeQuery();


            List<String> data = helper.converResultToList(rs);
            return data;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }


    public List getAllLegs() {

        // get all legs
        String query = "SELECT from_airport, to_airport FROM Legs;";
        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;

        List<Map<String, String>> legs = new ArrayList<>();

        try {

            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            legs = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, sm, rs);

        }

        return legs;
    }


    @Override
    public ArrayList<Leg> getLegsByAirport(String fromAirport, String toAirport) {

        String query = "SELECT * FROM Legs " +
                "WHERE from_airport = ? " +
                "AND to_airport = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        ArrayList<Leg> legs = new ArrayList<>();
        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, fromAirport);
            ps.setString(2, toAirport);
            rs = ps.executeQuery();

            Leg leg = new Leg();
            while (rs.next()) {

                leg.setAirlineId(rs.getString("airline_id"));
                leg.setFlightNumber(rs.getInt("flight_number"));
                leg.setLegNumber(rs.getInt("leg_number"));
                leg.setFromAirport(rs.getString("from_airport"));
                leg.setToAirport(rs.getString("to_airport"));
                leg.setDepartureTime(rs.getString("departure_time"));
                leg.setArrivalTime(rs.getString("arrival_time"));

                legs.add(leg);

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return legs;

    }

    @Override
    public List<Object> getAllFlights() {

        String query = "SELECT F.flight_number, F.airline, F.date_of_week, F.seating_capacity, " +
                "L.from_airport, L.to_airport " +
                "FROM Flight F, Legs L " +
                "WHERE F.flight_number = L.flight_number " +
                "AND F.airline = L.airline_id;";

        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;

        List<Object> flights = new ArrayList<>();

        try {
            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            flights = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, sm, rs);

        }
        return flights;
    }

    public ArrayList<ArrayList<Leg>> getFareInformation(ArrayList<ArrayList<Leg>> routes, FlightSearch fs) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println(fs);
        System.out.println(routes);

        try {

            conn = connectionUtil.getConn();

            String query = "SELECT * FROM `Legs` L, `Fare` F WHERE L.flight_number = ? " +
                    "AND L.airline_id = ? AND L.leg_number = ? AND F.fare_type = ? AND F.class = ?" +
                    "AND L.flight_number = F.flight_number " +
                    "AND L.airline_id = F.airline_id AND L.leg_number = F.leg_number";

            for (ArrayList list : routes) {

                for (Object i : list) {

                    Leg leg = (Leg) i;
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, leg.getFlightNumber());
                    ps.setString(2, leg.getAirlineId());
                    ps.setInt(3, leg.getLegNumber());
                    ps.setString(4, fs.getFareType());
                    ps.setString(5, fs.getClassType());

                    rs = ps.executeQuery();

                    while (rs.next()) {
                        ((Leg) i).setFare(rs.getDouble("fare"));
                        ((Leg) i).setClassType(rs.getString("class"));
                        ((Leg) i).setFareType(rs.getString("fare_type"));
                    }
                }
            }

            return routes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }
    }

    @Override
    public List<Map<String, Object>> getOnTimeFlights() {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        List<Map<String, Object>> result = null;
        try {

            // ideal departure/arrival time = actual OR actual is NULL
            String query = "SELECT * FROM Legs " +
                    "WHERE (Legs.departure_time = Legs.actual_departure_time " +
                    "AND Legs.arrival_time = Legs.actual_arrival_time) " +
                    "OR (Legs.actual_departure_time IS NULL AND Legs.actual_arrival_time IS NULL);";

            conn = connectionUtil.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            result = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, st, rs);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getDelayedFlights() {


        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        List<Map<String, Object>> result = null;
        try {

            String query = "SELECT * FROM Legs " +
                    "WHERE Legs.departure_time < Legs.actual_departure_time " +
                    "OR Legs.arrival_time < Legs.actual_arrival_time; ";

            conn = connectionUtil.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            result = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, st, rs);
        }

        return result;

    }


    /**
     * get remaining seats of a flight
     *
     * @param airline
     * @param flightNumber
     * @param classType
     * @return List
     */
    public List<Integer> getRemainingSeats(String airline, Integer flightNumber, String classType) {
        System.out.println("Enter get remain");
        String query = "SELECT seat_number FROM Include WHERE airline_id = ? AND flight_number = ?";
        String seatCapacityQuery = "SELECT seating_capacity FROM Flight WHERE airline =? AND flight_number = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            // get taken seats
            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, airline);
            ps.setInt(2, flightNumber);
            rs = ps.executeQuery();

            List res = helper.converResultToList(rs);
            List<Integer> takenSeats = new ArrayList<>();

            if (res.size() != 0) {
                for (Object e : res) {
                    takenSeats.add(Integer.parseInt((String) ((HashMap) e).get("seat_number")));
                }
            }

            // get the seating capacity of a given flight
            ps = conn.prepareStatement(seatCapacityQuery);
            ps.setString(1, airline);
            ps.setInt(2, flightNumber);
            rs = ps.executeQuery();

            int seatCapacity = 1;

            while (rs.next()) {
                seatCapacity = rs.getInt(1);
            }

            System.out.println(seatCapacity);

            // partition the flight seats into three classes
            int firstClass = 10;
            int businessClass = (seatCapacity - firstClass) / 2;

            List remainingSeats = new ArrayList();


            // build array for first class

            if (classType.equals("first")) {


                for (int i = 1; i <= firstClass; i++) {
                    if (!takenSeats.contains(i)) {
                        remainingSeats.add(i);
                    }
                }

                return remainingSeats;

            } else if (classType.equals("business")) {

                for (int i = firstClass + 1; i <= businessClass + firstClass; i++) {
                    if (!takenSeats.contains(i)) {
                        remainingSeats.add(i);
                    }
                }

                return remainingSeats;

            } else if (classType.equals("economy")) {

                for (int i = firstClass + businessClass + 1; i <= seatCapacity; i++) {
                    if (!takenSeats.contains(i)) {
                        remainingSeats.add(i);
                    }
                }

                return remainingSeats;

            } else {

                return null;

            }


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }


    }


}
