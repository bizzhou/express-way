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
     * @return
     */
    @Override
    public List<Map<String, Object>> getMostFreqFlights() {
        String query = "SELECT * FROM Flight ORDER BY date_of_week DESC";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // get connection
            connection = connectionUtil.getConn();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            List<Map<String, Object>> data = helper.converResultToList(rs);

            return data;


        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            // close jdbc connection
            connectionUtil.close(connection, preparedStatement, null, rs);

        }

    }

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

        String query = "SELECT DISTINCT C.account_number " +
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
    public ArrayList<ArrayList<Leg>> getFareInformation(ArrayList<ArrayList<Leg>> routes, FlightSearch fs) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();

            String query = "SELECT * FROM `Legs` L, `Fare` F WHERE L.flight_number = ? " +
                    "AND L.airline_id = ? AND F.fare_type = ? AND F.class = ?" +
                    "AND L.flight_number = F.flight_number " +
                    "AND L.airline_id = F.airline_id AND L.leg_number = F.leg_number";

            for(ArrayList list : routes){
                for(Object i : list){
                    Leg leg = (Leg)i;
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, leg.getFlightNumber());
                    ps.setString(2, leg.getAirlineId());
                    ps.setString(3, fs.getFareType());
                    ps.setString(4, fs.getClassType());

                    rs = ps.executeQuery();

                    while(rs.next()){
                        ((Leg) i).setFare(rs.getDouble("fare"));
                        ((Leg) i).setClassType(rs.getString("class"));
                        ((Leg) i).setFareType(rs.getString("fare_type"));
                    }

                }
            }

            System.out.println(routes);
            return routes;

        }catch (Exception e){
            e.printStackTrace();
            return null;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

    }


}
