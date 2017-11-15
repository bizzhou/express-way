package com.expressway.service.impl;

import com.expressway.service.ManagerLevelService;
import com.expressway.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerLevelServiceImpl implements ManagerLevelService{

    @Autowired
    ConnectionUtil connectionUtil;

    /**
     *
     * @return employee ssn, or -1 if not found
     */
    @Override
    public Integer getEmployeeWithMostRevenue() {
        String query = "SELECT E.ssn " +
                "FROM Reservations R, Employee E " +
                "WHERE R.customer_rep_ssn = E.ssn " +
                "GROUP BY E.ssn " +
                "ORDER BY SUM(booking_fee) DESC " +
                "LIMIT 1;";

        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
        Integer ssn = -1;

        try {

            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            while(rs.next()) {
                ssn = rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, sm, rs);

        }

        return ssn;

    }

    @Override
    public String getCustomerWithMostSpent() {
        String query = "SELECT C.account_number " +
                "FROM Reservations R, Customer C " +
                "WHERE R.account_number = C.account_number " +
                "GROUP BY C.account_number " +
                "ORDER BY SUM(booking_fee) DESC " +
                "LIMIT 1;";

        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
        String customerAcct = "";

        try {

            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            while(rs.next()) {
                customerAcct = rs.getString(1);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, null, sm, rs);

        }

        return customerAcct;
    }

    @Override
    public List<Map<String, Object>> getRevenueByFlight(String airline, int flightNumber) {

        String query = "SELECT DISTINCT I.reservation_number, booking_fee " +
                "FROM Include I, Reservations R " +
                "WHERE I.airline_id = ? " +
                "AND I.flight_number = ? " +
                "AND R.reservation_number = I.reservation_number;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;

        List<Map<String, Object>> data = new ArrayList<>();

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            ps.setString(1, airline);
            ps.setInt(2, flightNumber);

            rs = ps.executeQuery();
            metaData  = rs.getMetaData();

            while (rs.next()) {

                int colCount = metaData.getColumnCount();
                Map<String, Object> row = new HashMap<>(colCount);

                for (int i  = 1; i <= colCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }

                data.add(row);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return data;
    }
}
