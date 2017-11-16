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

    Connection connection = ConnectionUtil.getConnection();


    @Override
    public Map validateUser(User user) {

        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, "user");
            ResultSet rs = statement.executeQuery();

            Map custMap = new HashMap();

            System.out.println(rs.getFetchSize());

            while (rs.next()){
                custMap.put("role", rs.getString(1));
                custMap.put("id", rs.getString(2));
            }

            return custMap;


        } catch (SQLException e) {

            e.printStackTrace();
            // if no match return null;
            return null;

        }

    }

    @Override
    public boolean addUser(Customer user) {


        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
        String customerQuery = "INSERT INTO Customer (id, account_number, username, credit_card, telephone, email, rating) VALUE (?, ?, ?, ?, ?, ?, ?)";

        try {

            int i = 1;

            PreparedStatement personStatement = connection.prepareStatement(personQuery);
            personStatement.setString(i++, user.getFirstname());
            personStatement.setString(i++, user.getLastname());
            personStatement.setString(i++, user.getAddress());
            personStatement.setString(i++, user.getCity());
            personStatement.setString(i++, user.getState());
            personStatement.setString(i++, user.getZipcode());

            personStatement.executeUpdate();

            i = 1;
            PreparedStatement idStatement = connection.prepareStatement(last_id);
            ResultSet resultSet = idStatement.executeQuery();

            int lastInsertedId = 0;
            while (resultSet.next()){
                lastInsertedId = resultSet.getInt(1);
            }

            if(lastInsertedId == 0){
                System.out.printf("Cannot get the last inserted id");
                return false;
            }

            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(i++,user.getUsername());
            userStatement.setString(i++,user.getPassword());
            userStatement.setString(i++,"user");
            userStatement.setInt(i++,lastInsertedId);

            userStatement.executeUpdate();

            i = 1;
            PreparedStatement employeeStatement = connection.prepareStatement(customerQuery);
            employeeStatement.setInt(i++, lastInsertedId);
            employeeStatement.setString(i++, user.getUsername() + user.getZipcode());
            employeeStatement.setString(i++, user.getUsername());
            employeeStatement.setString(i++, user.getCreditcard());
            employeeStatement.setString(i++, user.getTelephone());
            employeeStatement.setString(i++, user.getEmail());
            employeeStatement.setInt(i++, 10);

            employeeStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

        return true;

    }

    @Override
    public boolean deleteUser(int personId) {

        List<String> queryList = new ArrayList<>();
        queryList.add("DELETE FROM Customer WHERE Customer.id = ?");
        queryList.add("DELETE FROM User WHERE User.person_id = ?");
        queryList.add("DELETE FROM Person WHERE Person.id = ?");

        try {

            for (String query : queryList) {
                PreparedStatement curQuery = connection.prepareStatement(query);
                curQuery.setInt(1, personId);
                curQuery.executeUpdate();
            }
            return true;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
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
            ps.setString(i++, user.getFirstname());
            ps.setString(i++, user.getLastname());
            ps.setString(i++, user.getAddress());
            ps.setString(i++, user.getCity());
            ps.setString(i++, user.getState());
            ps.setInt(i++, Integer.parseInt(user.getZipcode()));
            ps.setInt(i++, id);

            ps.executeUpdate();

            ps = conn.prepareStatement(customerQuery);

            i = 1;
            ps.setString(i++, user.getCreditcard());
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

        String query = "SELECT Person.id, first_name, last_name, username, email, address, city, state, " +
                       "zip_code, telephone, credit_card, rating " +
                       "FROM Customer, Person;";

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
            connectionUtil.close(connection, null, st, rs);

        }

        return data;


    }

}
