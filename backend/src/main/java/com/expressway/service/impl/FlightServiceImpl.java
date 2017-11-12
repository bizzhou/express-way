package com.expressway.service.impl;

import com.expressway.model.FlightSearch;
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

        String query = "SELECT Legs.airline_id, Legs.flight_number, Legs.leg_number, Legs.from_airport, Legs.to_airport, Legs.departure_time, Legs.to_airport, Fare.fare_type,  Fare.class, Fare.fare FROM Legs, Fare WHERE DATE(Legs.departure_time) = ? AND from_airport = ? AND to_airport = ? AND Fare.leg_number = Legs.leg_number AND Fare.fare_type = ? AND Fare.class = ?";


        try {
            PreparedStatement statement = conn.prepareStatement(query);
//            statement.setDate(1, new Date(flight.getStartDate().getTime()));
            statement.setString(1, "2017-11-11");
            statement.setString(2, flight.getFromAirport());
            statement.setString(3, flight.getToAirport());
            statement.setString(4, flight.getFareType());
            statement.setString(5, flight.getClassType());

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


}
