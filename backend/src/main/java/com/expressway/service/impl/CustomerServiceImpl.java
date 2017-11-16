package com.expressway.service.impl;

import com.expressway.model.Customer;
import com.expressway.model.User;
import com.expressway.service.CustomerService;
import com.expressway.util.ConnectionUtil;
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
    private ConnectionUtil connectionUtil;

    @Override
    public Map validateUser(User user) {

        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

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

        List<Map<String, Object>> data = new ArrayList<>();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;

        try {
            // get connection
            conn = connectionUtil.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            metaData = rs.getMetaData();

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
            connectionUtil.close(conn, null, st, rs);

        }

        return data;


    }

}
