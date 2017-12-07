package com.expressway.service.impl;

import com.expressway.model.Auction;
import com.expressway.service.ManagerLevelService;
import com.expressway.util.ConnectionUtil;
import com.expressway.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerLevelServiceImpl implements ManagerLevelService {

    @Autowired
    private ConnectionUtil connectionUtil;

    @Autowired
    private Helper helper;

    /**
     * @return employee ssn, or -1 if not found
     */
    @Override
    public Map getEmployeeWithMostRevenue() {

        String query = "SELECT E.ssn " +
                "FROM Reservations R, Employee E " +
                "WHERE R.customer_rep_ssn = E.ssn " +
                "GROUP BY E.ssn " +
                "ORDER BY SUM(booking_fee) DESC " +
                "LIMIT 1;";


        String employeeDetails = "SELECT Person.id, first_name, last_name, USER.username, address, city, state, " +
                "zip_code, telephone, ssn, hourly_rate " +
                "FROM Employee, Person, User WHERE Employee.ssn = ? " +
                "AND Employee.id = Person.id " +
                "AND User.person_id = Person.id ";

        Connection conn = null;
        Statement sm = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer ssn = -1;


        try {

            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            while (rs.next()) {
                ssn = rs.getInt(1);
            }


            ps = conn.prepareStatement(employeeDetails);
            ps.setInt(1, ssn);

            rs = ps.executeQuery();

            Map<String, Object> map = new HashMap<>();

            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {

                int colCount = metaData.getColumnCount();

                for (int i = 1; i <= colCount; i++) {
                    map.put(metaData.getColumnName(i), rs.getObject(i));
                }

            }

            return map;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, sm, rs);

        }

    }


    /**
     * get user who spent most
     *
     * @return map of a user
     */
    @Override
    public Map getCustomerWithMostSpent() {

        String query = "SELECT C.account_number " +
                "FROM Reservations R, Customer C " +
                "WHERE R.account_number = C.account_number " +
                "GROUP BY C.account_number " +
                "ORDER BY SUM(booking_fee) DESC " +
                "LIMIT 1;";

        String custDetails = "SELECT Person.id, first_name, last_name, USER.username, telephone, account_number " +
                "FROM Customer, Person, User " +
                "WHERE Customer.account_number = ? " +
                "AND Customer.id = Person.id " +
                "AND User.person_id = Person.id";

        Connection conn = null;
        Statement sm = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String customerAcct = "";

        try {


            conn = connectionUtil.getConn();
            sm = conn.createStatement();
            rs = sm.executeQuery(query);

            while (rs.next()) {
                customerAcct = rs.getString(1);
            }

            ps = conn.prepareStatement(custDetails);
            ps.setString(1, customerAcct);

            rs = ps.executeQuery();

            Map<String, Object> map = new HashMap<>();

            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {

                int colCount = metaData.getColumnCount();

                for (int i = 1; i <= colCount; i++) {
                    map.put(metaData.getColumnName(i), rs.getObject(i));
                }

            }

            return map;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, sm, rs);

        }

    }

    /**
     * get revenue based on flight
     *
     * @param airline
     * @param flightNumber
     * @return list reservations and price of each reservations
     */
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

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            ps.setString(1, airline);
            ps.setInt(2, flightNumber);

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

    /**
     * get revenue based on destination city.
     *
     * @param destinationCity
     * @return list of revenues
     */
    @Override
    public List<Map<String, Object>> getRevenueByCity(String destinationCity) {

        String query = "SELECT DISTINCT Reservations.reservation_number , booking_fee " +
                "FROM Include, Legs, Reservations " +
                "WHERE Reservations.reservation_number = Include.reservation_number " +
                "AND Include.flight_number = Legs.flight_number " +
                "AND Include.airline_id = Legs.airline_id " +
                "AND Legs.to_airport IN (" +
                "  SELECT airport_id " +
                "     FROM Airport " +
                "     WHERE city = ? " +
                ");";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, destinationCity);
            rs = ps.executeQuery();

            return helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }

    /**
     * get revenue by customer
     *
     * @param customerAcct customer account
     * @return list of revenues
     */
    @Override
    public List<Map<String, Object>> getRevenueByCustomer(String customerAcct) {

        String query = "SELECT C.account_number, R.reservation_number, booking_fee " +
                "FROM Customer C, Reservations R " +
                "WHERE C.account_number = ? " +
                "AND R.account_number = C.account_number;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            // execute query
            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, customerAcct);
            rs = ps.executeQuery();

            return helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }

    /**
     * get reservation based on flight number and airline id
     *
     * @param airline
     * @param flightNumber
     * @return list of reservations
     */
    @Override
    public List<Map<String, Object>> getReservationByFlight(String airline, int flightNumber) {

        String query = "SELECT * " +
                "FROM Reservations " +
                "WHERE reservation_number IN" +
                "      (SELECT I.reservation_number " +
                "       FROM Include I, Airline A " +
                "       WHERE I.flight_number = ? " +
                "       AND A.airline_id=? " +
                "       AND I.airline_id = A.airline_id " +
                "       );";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            ps.setInt(1, flightNumber);
            ps.setString(2, airline);

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

    /**
     * get reservation based on customer's name
     *
     * @param customerName
     * @return list of reservations
     */
    @Override
    public List<Map<String, Object>> getReservationByCustomerName(String customerName) {
        String query = "SELECT * " +
                "FROM Reservations R " +
                "WHERE account_number IN" +
                "      (SELECT DISTINCT (account_number) " +
                "       FROM full_name, Customer " +
                "       WHERE full_name.`concat(first_name, ' ', last_name)` = ? " +
                "       AND full_name.id = Customer.id" +
                "       );";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            ps.setString(1, customerName);
            rs = ps.executeQuery();

            return helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }


    }

    /**
     * get monthly sales resport
     *
     * @param startDate
     * @param endDate
     * @return montly revenue
     */
    @Override
    public Double getMonthlySalesReport(String startDate, String endDate) {
        String query = "SELECT SUM(total_fare) " +
                "FROM Reservations " +
                "WHERE reservation_date BETWEEN ? AND ?;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double sales = -1;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            rs = ps.executeQuery();

            sales = 0;

            while (rs.next()) {
                sales = rs.getDouble(1);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return sales;
    }

    /**
     * get all user bids
     *
     * @return list of user bids
     */
    @Override
    public List getBids() {
        String query = "SELECT account_num, airline_id, flight_num, leg_number, class, dept_date, NYOP, is_accepted " +
                "FROM Auctions WHERE is_accepted = false";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            return helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            connectionUtil.close(conn, null, st, rs);

        }

    }

    /**
     * turn auction to reservation if the manager accept the reverse bid price
     *
     * @param auction object holds auction information
     * @return true/false
     */
    @Override
    public boolean auctionToReservation(Auction auction) {

        System.out.println(auction);

        String resvQuery = "INSERT INTO Reservations(account_number, total_fare, booking_fee) " +
                "VALUES (?, ?, ?)";

        String last_inserted_reservation = "SELECT LAST_INSERT_ID() FROM reservations " +
                "WHERE account_number = ? LIMIT 1";

        String updateResv = "UPDATE Auctions SET is_accepted = ? ,reservation_number = ? " +
                "WHERE account_num = ? AND flight_num = ? AND airline_id = ? AND leg_number = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(resvQuery);
            int i = 1;

            ps.setString(i++, auction.getAccountNumber());
            ps.setDouble(i++, auction.getBidPrice());
            ps.setDouble(i++, 20.0);

            ps.executeUpdate();

            ps = conn.prepareStatement(last_inserted_reservation);
            ps.setString(1, auction.getAccountNumber());

            rs = ps.executeQuery();

            int lastInsertedId = 0;

            while (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }

            if (lastInsertedId == 0) {
                return false;
            }

            i = 1;
            ps = conn.prepareStatement(updateResv);
            ps.setBoolean(i++, true);
            ps.setInt(i++, lastInsertedId);
            ps.setString(i++, auction.getAccountNumber());
            ps.setInt(i++, auction.getFlightNumber());
            ps.setString(i++, auction.getAirlineId());
            ps.setInt(i++, auction.getLegNumber());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            return false;
        }


    }

    @Override
    public boolean dumpData() {

        try{

            connectionUtil.backUp();
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


}
