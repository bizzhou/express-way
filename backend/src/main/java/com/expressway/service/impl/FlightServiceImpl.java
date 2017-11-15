package com.expressway.service.impl;

import com.expressway.model.FlightSearch;
import com.expressway.model.Flight;
import com.expressway.service.FlightService;
import com.expressway.util.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService {

    Connection conn = ConnectionUtil.getConnection();

    @Override
    public List<Map<String, Object>> serachFlight(FlightSearch flight) {

        System.out.println(flight);

        String query = "SELECT Legs.airline_id, Legs.flight_number, Legs.leg_number, Legs.from_airport, Legs.to_airport, Legs.departure_time, Legs.to_airport, Fare.fare_type,  Fare.class, Fare.fare FROM Legs, Fare WHERE DATE(Legs.departure_time) = ? AND from_airport = ? AND to_airport = ? AND Fare.leg_number = Legs.leg_number AND Fare.fare_type = ? AND Fare.class = ? AND Fare.airline_id = Legs.airline_id";

        try {
            int statIndex = 1;
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(statIndex++, flight.getDepatureDate());

//            if(!flight.getReturnDate().equals("")){
//                statement.setString(statIndex++, flight.getDepatureDate());
//            }

            statement.setString(statIndex++, flight.getFromAirport());
            statement.setString(statIndex++, flight.getToAirport());
            statement.setString(statIndex++, flight.getFareType());
            statement.setString(statIndex++, flight.getClassType());

            ResultSet rs = statement.executeQuery();

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

        }

    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> getMostFreqFlights() {
        String query = "SELECT * FROM Flight ORDER BY date_of_week DESC";
        List<Map<String, Object>> data = new ArrayList<>();

        Connection connection = null;
        PreparedStatement  preparedStatement = null;
        ResultSet  rs = null;

        try {
            // get connection
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            while(rs.next()) {
                int colCount = metaData.getColumnCount();
                Map<String, Object> row = new HashMap<>(colCount);

                for (int i = 1; i <= colCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            // close jdbc connection
            close(connection, preparedStatement, null, rs);

        }

        return data;

    }

    /**
     *
     * @param airportId
     * @return
     */
    public List<Map<String, Object>> getFlightsForAirport(String airportId) {
        String query = "SELECT airline_id, F.flight_number, leg_number, from_airport, to_airport, " +
                "departure_time, arrival_time, seating_capacity " +
                "FROM Legs L, Flight F " +
                "WHERE L.flight_number = F.flight_number " +
                "AND L.from_airport = 'JFK' " +
                "AND L.airline_id = F.airline;";

        List<Map<String, Object>> data = new ArrayList<>();

        Connection connection = null;
        PreparedStatement  preparedStatement = null;
        ResultSet  resultSet = null;

        try {

            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();

            while(resultSet.next()) {
                int colCount = metaData.getColumnCount();
                Map<String, Object> row = new HashMap<>(colCount);

                for (int i = 1; i <= colCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }

                data.add(row);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            close(connection, preparedStatement, null, resultSet);

        }

        return data;

    }

    /**
     * Close JDBC connection
     * @param connection
     * @param preparedStatement
     * @param statement
     * @param resultSet
     */
    private void close(Connection connection, PreparedStatement preparedStatement,
                       Statement statement, ResultSet resultSet) {

        try {
            if (connection != null)
                connection.close();

            if (preparedStatement != null)
                preparedStatement.close();

            if (statement != null)
                statement.close();

            if (resultSet != null)
                resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
