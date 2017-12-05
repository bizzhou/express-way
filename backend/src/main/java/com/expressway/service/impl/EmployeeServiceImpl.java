package com.expressway.service.impl;

import com.expressway.model.Employee;
import com.expressway.model.User;
import com.expressway.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ConnectionUtil connectionUtil;

    @Autowired
    private Helper helper;

    @Override
    public Map validateEmployee(User user) {

        String query = "SELECT role, person_id, username " +
                "FROM User " +
                "WHERE User.username = ? AND User.password = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = connectionUtil.getConn();

        try {

            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            rs = ps.executeQuery();

            Map empMap = new HashMap();

            System.out.println(rs.getFetchSize());

            while (rs.next()) {
                empMap.put("role", rs.getString(1));
                empMap.put("id", rs.getString(2));
            }

            return empMap;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

        return null;
    }

    @Override
    public boolean addEmployee(Employee emp) {

        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
        String employeeQuery = "INSERT INTO Employee (ssn, id, username, is_manager, hourly_rate, telephone) VALUE (?, ?, ?, ?, ?, ?)";

        System.out.println(emp);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = connectionUtil.getConn();

        try {

            int i = 1;

            ps = conn.prepareStatement(personQuery);
            ps.setString(i++, emp.getFirst_name());
            ps.setString(i++, emp.getLast_name());
            ps.setString(i++, emp.getAddress());
            ps.setString(i++, emp.getCity());
            ps.setString(i++, emp.getState());
            ps.setInt(i++, emp.getZip_code());

            ps.executeUpdate();

            i = 1;
            ps = conn.prepareStatement(last_id);
            rs = ps.executeQuery();

            int lastInsertedId = 0;
            while (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }

            if (lastInsertedId == 0) {
                System.out.printf("Cannot get the last inserted id");
                return false;
            }

            ps = conn.prepareStatement(userQuery);
            ps.setString(i++, emp.getUsername());
            ps.setString(i++, emp.getPassword());
            ps.setString(i++, "employee");
            ps.setInt(i++, lastInsertedId);

            ps.executeUpdate();

            i = 1;
            ps = conn.prepareStatement(employeeQuery);
            ps.setString(i++, emp.getSsn());
            ps.setInt(i++, lastInsertedId);
            ps.setString(i++, emp.getUsername());
            ps.setBoolean(i++, emp.isManger());
            ps.setDouble(i++, emp.getHourly_rate());
            ps.setString(i++, emp.getTelephone());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

    }

    @Override
    public boolean deleteEmployee(int id) {

        List<String> queryList = new ArrayList<>();
        queryList.add("DELETE FROM Employee WHERE Employee.id = ?");
        queryList.add("DELETE FROM User WHERE User.person_id = ?");
        queryList.add("DELETE FROM Person WHERE Person.id = ?");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = connectionUtil.getConn();

        try {


            for (String query : queryList) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            System.out.println("Deletion Complete " + id);

            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;


        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }


    }

    @Override
    public List getAllEmployee() {

        String query = "SELECT Person.id, first_name, last_name, USER.username, address, city, state, zip_code, telephone, ssn, hourly_rate " +
                "FROM Employee, Person, User " +
                "WHERE Employee.id = Person.id AND User.person_id = Person.id AND User.role = ?";


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // get connection
            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, "employee");
            rs = ps.executeQuery();

            List<Map<String, Object>> data = helper.converResultToList(rs);

            return data;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            // close jdbc connection
//            connectionUtil.close(conn, ps, null, rs);

        }


    }

    @Override
    public boolean updateEmployee(Employee user, int id) {

        System.out.println(user);


        String personQuery = "UPDATE Person " +
                "SET first_name = ?, last_name = ?, address = ?, city = ?, state = ?, zip_code = ? WHERE id = ?";
        String employeeQuery = "UPDATE Employee SET ssn = ?, telephone = ?, hourly_rate = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;


        try {

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

            ps = conn.prepareStatement(employeeQuery);

            i = 1;
            ps.setString(i++, user.getSsn());
            ps.setString(i++, user.getTelephone());
            ps.setDouble(i++, user.getHourly_rate());
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
    public List getCustomerEmails() {

        String query = "SELECT account_number, email FROM Customer " +
                "WHERE email IS NOT NULL;";

        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;

        List<Map<String, Object>> emailList = new ArrayList<>();

        try {

            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);
            emailList = helper.converResultToList(rs);
//            while (rs.next()) {
//
//                emailList.add(rs.getString(1));
//
//            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, sm, rs);
        }

        return emailList;

    }

    @Override
    public List<Map<String, Object>> getFlightSuggestions(int customerId) {

        String query = "SELECT I.flight_number, I.airline_id, COUNT(*) AS total_reserv " +
                "FROM Include I, Reservations R, Customer C " +
                "WHERE C.id = ? " +
                "AND C.account_number = R.account_number " +
                "AND R.reservation_number = I.reservation_number " +
                "GROUP BY I.flight_number, I.airline_id " +
                "ORDER BY total_reserv DESC " +
                "LIMIT 10;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

//        List<Map<String, Object>> suggestions = new ArrayList<>();
        List<Map<String, Object>> suggestions = null;
        conn = connectionUtil.getConn();

        try {


            ps = conn.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            suggestions = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);
        }

        return suggestions;
    }
}
