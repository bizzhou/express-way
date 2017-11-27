package com.expressway.service.impl;

import com.expressway.model.FlightSearch;
import com.expressway.model.Flight;
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

    @Override
    public List<Map<String, Object>> serachFlight(FlightSearch flight) {
        System.out.println(flight);

        String query = "SELECT Legs.airline_id, Legs.flight_number, Legs.leg_number, Legs.from_airport, " +
                "Legs.to_airport, Legs.departure_time, Legs.to_airport, Fare.fare_type,  Fare.class, Fare.fare " +
                "FROM Legs, Fare " +
                "WHERE DATE(Legs.departure_time) = ? " +
                "AND from_airport = ? " +
                "AND to_airport = ? " +
                "AND Fare.leg_number = Legs.leg_number " +
                "AND Fare.fare_type = ? " +
                "AND Fare.class = ? " +
                "AND Fare.airline_id = Legs.airline_id";


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = connectionUtil.getConn();

        try {

            int statIndex = 1;
            ps = conn.prepareStatement(query);

            ps.setString(statIndex++, flight.getDepatureDate());

//            if(!flight.getReturnDate().equals("")){
//                ps.setString(statIndex++, flight.getDepatureDate());
//            }

            ps.setString(statIndex++, flight.getFromAirport());
            ps.setString(statIndex++, flight.getToAirport());
            ps.setString(statIndex++, flight.getFareType());
            ps.setString(statIndex++, flight.getClassType());

            rs = ps.executeQuery();

            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>(metaData.getColumnCount());

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }

                data.add(row);

            }

            return data;


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }

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


}
