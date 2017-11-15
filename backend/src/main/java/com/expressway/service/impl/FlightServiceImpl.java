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

    @Override
    public List<Map<String, Object>> getMostFreqFlights() throws SQLException {
        String query = "SELECT * FROM Flight ORDER BY date_of_week DESC";
        List<Map<String, Object>>  data = new ArrayList<>();

        Connection connection = null;
        PreparedStatement  statement = null;
        ResultSet  rs = null;

        try {
            // get connection
            connection = ConnectionUtil.getConnection();
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            while(rs.next()) {
                Map<String, Object> row = new HashMap<>(metaData.getColumnCount());

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }


        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            // close jdbc connection
            close(connection, statement, null, rs);

        }

        return data;


    }

    /* close jdbc connection */
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
