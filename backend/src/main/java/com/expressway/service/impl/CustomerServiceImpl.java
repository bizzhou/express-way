package com.expressway.service.impl;

import com.expressway.model.Customer;
import com.expressway.model.User;
import com.expressway.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private Helper helper;

    @Autowired
    private ConnectionUtil connectionUtil;

    @Override
    public Map validateUser(User user) {

        String query = "SELECT role, person_id, username " +
                "FROM User " +
                "WHERE User.username = ? AND User.password = ? AND User.role = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = connectionUtil.getConn();

        try {

            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, "user");
            rs = ps.executeQuery();

            Map custMap = new HashMap();

            while (rs.next()){
                custMap.put("role", rs.getString(1));
                custMap.put("id", rs.getString(2));
            }

            return custMap;


        } catch (SQLException e) {

            e.printStackTrace();
            // if no match return null;
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }


    }

    @Override
    public boolean addUser(Customer user) {


        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) " +
                             "VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
        String customerQuery = "INSERT INTO Customer (id, account_number, username, credit_card, telephone, email, rating) " +
                               "VALUE (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();

            int i = 1;

            ps = conn.prepareStatement(personQuery);
            ps.setString(i++, user.getFirst_name());
            ps.setString(i++, user.getLast_name());
            ps.setString(i++, user.getAddress());
            ps.setString(i++, user.getCity());
            ps.setString(i++, user.getState());
            ps.setInt(i++, user.getZip_code());

            ps.executeUpdate();

            i = 1;
            ps = conn.prepareStatement(last_id);
            rs = ps.executeQuery();

            int lastInsertedId = 0;

            while (rs.next()){
                lastInsertedId = rs.getInt(1);
            }

            if(lastInsertedId == 0){
                System.out.printf("Cannot get the last inserted id");
                return false;
            }

            ps = conn.prepareStatement(userQuery);
            ps.setString(i++,user.getUsername());
            ps.setString(i++,user.getPassword());
            ps.setString(i++,"user");
            ps.setInt(i++,lastInsertedId);

            ps.executeUpdate();

            i = 1;
            ps = conn.prepareStatement(customerQuery);
            ps.setInt(i++, lastInsertedId);
            ps.setString(i++, user.getUsername() + user.getZip_code());
            ps.setString(i++, user.getUsername());
            ps.setString(i++, user.getCredit_card());
            ps.setString(i++, user.getTelephone());
            ps.setString(i++, user.getEmail());
            ps.setInt(i++, 10);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return true;

    }

    @Override
    public boolean deleteUser(int personId) {

        List<String> queryList = new ArrayList<>();

        queryList.add("DELETE FROM Customer WHERE Customer.id = ?");
        queryList.add("DELETE FROM User WHERE User.person_id = ?");
        queryList.add("DELETE FROM Person WHERE Person.id = ?");

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = connectionUtil.getConn();

            for (String query : queryList) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, personId);
                ps.executeUpdate();
            }

            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {

            connectionUtil.close(conn, ps, null, null);

        }

    }

    @Override
    public boolean updateUser(Customer user, int id) {

        String personQuery = "UPDATE Person " +
                "SET first_name = ?, last_name = ?, address = ?, city = ?, state = ?, zip_code = ? WHERE id = ?";
        String customerQuery = "UPDATE Customer SET credit_card = ?, telephone = ?, email = ?, rating = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement  ps = null;

        try{

            conn = connectionUtil.getConn();

            ps = conn.prepareStatement(personQuery);

            int i = 1;
            ps.setString(i++, user.getFirst_name());
            ps.setString(i++, user.getLast_name());
            ps.setString(i++, user.getAddress());
            ps.setString(i++, user.getCity());
            ps.setString(i++, user.getState());
            ps.setInt(i++, user.getZip_code());
            ps.setInt(i++, id);

            ps.executeUpdate();

            ps = conn.prepareStatement(customerQuery);

            i = 1;
            ps.setString(i++, user.getCredit_card());
            ps.setString(i++, user.getTelephone());
            ps.setString(i++, user.getEmail());
            ps.setInt(i++, user.getRating());
            ps.setInt(i++, id);

            ps.executeUpdate();


            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        } finally {

            connectionUtil.close(conn, ps, null, null);

        }


    }

    @Override
    public List getUsers() {

        String query = "SELECT Person.id, first_name, last_name, username, email, " +
                "address, city, state, zip_code, telephone, credit_card, rating " +
                "FROM Customer, Person " +
                "WHERE Customer.id = Person.id";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // get connection
            conn = connectionUtil.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            List<Map<String, Object>> data = helper.converResultToList(rs);
            return data;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            // close jdbc connection
            connectionUtil.close(conn, null, st, rs);

        }

    }

    @Override
    public List<Map<String, Object>> getCustomerReservations(String customerAccount) {

        String query = "SELECT * " +
                "FROM Customer C, Reservations R " +
                "WHERE C.account_number = ? " +
                "AND C.account_number = R.account_number; ";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> reservations = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, customerAccount);
            rs = ps.executeQuery();

            reservations = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return reservations;

    }

    @Override
    public List<Map<String, Object>> getTravelItinerary(String customerAccount, int resvNumber) {

        String query = "SELECT L.from_airport, L.to_airport " +
                "FROM Reservations R, Include Inc,Legs L " +
                "WHERE R.account_number = ? " +
                "AND R.reservation_number = ? " +
                "AND R.reservation_number = Inc.reservation_number " +
                "AND Inc.airline_id = L.airline_id " +
                "AND Inc.flight_number = L.flight_number " +
                "AND Inc.leg_number = L.leg_number;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> itinerary = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, customerAccount);
            ps.setInt(2, resvNumber);
            rs = ps.executeQuery();

            itinerary = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return itinerary;

    }

}
