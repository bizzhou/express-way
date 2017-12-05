package com.expressway.service.impl;

import com.expressway.model.*;
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

    /**
     * validate user for login
     *
     * @param user information of user passed from frontend
     * @return map that contain role, username
     */
    @Override
    public Map validateUser(User user) {

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

            Map custMap = new HashMap();

            while (rs.next()) {
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

    /**
     * Add user
     *
     * @param user user object that contains user information
     * @returna true/false
     */
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

            while (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }

            if (lastInsertedId == 0) {
                System.out.printf("Cannot get the last inserted id");
                return false;
            }

            ps = conn.prepareStatement(userQuery);
            ps.setString(i++, user.getUsername());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, "user");
            ps.setInt(i++, lastInsertedId);

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

    /**
     * Delete one user
     *
     * @param personId user's person id
     * @return true/false
     */
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

    /**
     * update user information
     *
     * @param user object that contains user information passed from frontend
     * @param id   user's person id
     * @return true/false
     */
    @Override
    public boolean updateUser(Customer user, int id) {

        String personQuery = "UPDATE Person " +
                "SET first_name = ?, last_name = ?, address = ?, city = ?, state = ?, zip_code = ? WHERE id = ?";
        String customerQuery = "UPDATE Customer SET credit_card = ?, telephone = ?, email = ?, rating = ? WHERE id = ?";

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

    /**
     * get one user
     *
     * @param id user's personid
     * @return map of user information
     */
    @Override
    public Map getUser(int id) {

        String query = "SELECT account_number,Person.id, first_name, last_name, username, email, " +
                "address, city, state, zip_code, telephone, credit_card, rating " +
                "FROM Customer, Person " +
                "WHERE Customer.id = ? AND Person.id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setInt(2, id);
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

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }

    }

    /**
     * get all users
     *
     * @return list of users
     */
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

    @Override
    public List<Map<String, Object>> getReservationHistory(String customerAccount) {
        String query = "SELECT * " +
                "FROM Reservations R, Include I, Legs L " +
                "WHERE R.account_number = ? " +
                "AND R.reservation_number = I.reservation_number " +
                "AND I.leg_number = L.leg_number " +
                "AND L.airline_id = I.airline_id AND L.flight_number = I.flight_number";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> history = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, customerAccount);
            rs = ps.executeQuery();

            history = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return history;

    }

    @Override
    public List<Map<String, Object>> getBestSellerFlights() {
        String query = "SELECT I.flight_number, I.airline_id, COUNT(*) AS flight_count " +
                "FROM Include I, Reservations R " +
                "WHERE R.reservation_number = I.reservation_number " +
                "GROUP BY I.flight_number, I.airline_id " +
                "ORDER BY flight_count DESC " +
                "LIMIT 10;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> history = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            history = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return history;

    }

    @Override
    public List<Map<String, Object>> getPersonalizedFlights(String customerAccount) {
        String query = "SELECT I.flight_number, I.airline_id, COUNT(*) AS total_reserv " +
                "FROM Include I, Reservations R, Customer C " +
                "WHERE C.account_number = ? " +
                "AND C.account_number = R.account_number " +
                "AND R.reservation_number = I.reservation_number " +
                "GROUP BY I.flight_number, I.airline_id " +
                "ORDER BY total_reserv ASC " +
                "LIMIT 10;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> suggestions = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, customerAccount);
            rs = ps.executeQuery();

            suggestions = helper.converResultToList(rs);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

        return suggestions;
    }


    /**
     * Make one way reservation
     *
     * @param reservationContext Wrapper for reservation and include object
     * @return Integer
     */
    @Override
    public Integer oneWayResv(ReservationContext reservationContext) {

        Reservation reservation = reservationContext.getReservation();
        Include inc = reservationContext.getInclude();

        System.out.println(reservation);
        System.out.println(inc);


        String resvQuery = "INSERT INTO Reservations(account_number, total_fare, booking_fee) " +
                "VALUES (?, ?, ?)";

        String includeQuery = "INSERT INTO Include " +
                "(reservation_number, airline_id, flight_number, leg_number, passenger_lname, " +
                "passenger_fname, dept_date, seat_number, class, meal, from_stop_num) VALUES\n" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String last_inserted_reservation = "SELECT LAST_INSERT_ID() FROM reservations " +
                "WHERE account_number = ? LIMIT 1";


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(resvQuery);
            int i = 1;
            ps.setString(i++, reservation.getAccountNumber());
            ps.setDouble(i++, reservation.getTotalFare());
            ps.setDouble(i++, reservation.getBookingFare());
//            ps.setString(i++, reservation.getCustomer_rep_ssn());

            ps.executeUpdate();

            // get the user's last inserted id;
            ps = conn.prepareStatement(last_inserted_reservation);
            ps.setString(1, reservation.getAccountNumber());

            rs = ps.executeQuery();

            int lastInsertedId = 0;

            while (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }

            if (lastInsertedId == 0) {
                System.out.printf("Cannot get the last inserted id");
                return null;
            }

            System.out.println(lastInsertedId);

            //execute the include query

            i = 1;
            ps = conn.prepareStatement(includeQuery);
            ps.setInt(i++, lastInsertedId);
            ps.setString(i++, inc.getAirlineId());
            ps.setInt(i++, inc.getFlightNumber());
            ps.setInt(i++, inc.getLegNumber());
            ps.setString(i++, inc.getLastName());
            ps.setString(i++, inc.getFirstName());
            ps.setString(i++, inc.getDeptDate());
            ps.setInt(i++, inc.getSeatNumber());
            ps.setString(i++, inc.getFlightClass());
            ps.setString(i++, inc.getMeal());
            ps.setInt(i++, inc.getFromStop());

            ps.executeUpdate();

            System.out.println("Done.....");

            return lastInsertedId;


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }


    }


    /**
     * Make a two way reservation
     *
     * @return information about the reservation
     */
    @Override
    public List<Map> twoWayResv(List<ReservationContext> reservations) {

        int resvId = oneWayResv(reservations.get(0));
        System.out.println(reservations.get(0));
        System.out.println(reservations.get(1));
        int backResvId = oneWayResv(reservations.get(1));

        List<Map> list = new ArrayList<>();

        list.add(getReservationDetails(resvId));
        list.add(getReservationDetails(backResvId));

        return list;

    }


    @Override
    public Map getReservationDetails(int resvId) {

        String selectReservationQuery = "SELECT * FROM include WHERE reservation_number = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(selectReservationQuery);
            ps.setInt(1, resvId);
            rs = ps.executeQuery();

            Map<String, Object> map = new HashMap<>();
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {

                int colCount = metaData.getColumnCount();

                for (int idx = 1; idx <= colCount; idx++) {
                    map.put(metaData.getColumnName(idx), rs.getObject(idx));
                }

            }

            return map;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

    }


    /**
     * Reverse bid on one leg of the flight
     *
     * @param auction object that contains auction data passed from frontend
     * @return true/false
     */
    @Override
    public Boolean reverseBid(Auction auction) {

        String auctionQuery = "INSERT INTO Auctions (account_num, airline_id, flight_num, leg_number, class, dept_date, NYOP) VALUES (? , ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(auctionQuery);

            int i = 1;
            ps.setString(i++, auction.getAccountNumber());
            ps.setString(i++, auction.getAirlineId());
            ps.setInt(i++, auction.getFlightNumber());
            ps.setInt(i++, auction.getLegNumber());
            ps.setString(i++, auction.getFlightClass());
            ps.setString(i++, auction.getDepatureDate());
            ps.setDouble(i++, auction.getBidPrice());
            ps.executeUpdate();

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

    }


    /**
     * get current user's bid history
     *
     * @param account
     * @return List of bids
     */
    @Override
    public List getBids(String account) {

        String query = "SELECT account_num, reservation_number, airline_id, flight_num, " +
                "leg_number, class, dept_date, NYOP, is_accepted " +
                "FROM Auctions WHERE account_num = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            ps.setString(1, account);
            rs = ps.executeQuery();
            return helper.converResultToList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionUtil.close(conn, ps, null, rs);
        }

    }

    /**
     * insert entryies to the include table
     *
     * @param inc include object that correspond the to inlcude table
     * @return true/false
     */
    @Override
    public boolean insertInclude(Include inc) {

        String query = "INSERT INTO `Include` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            int i = 1;
            ps.setString(i++, inc.getReservationNumber());
            ps.setString(i++, inc.getAirlineId());
            ps.setInt(i++, inc.getFlightNumber());
            ps.setInt(i++, inc.getLegNumber());
            ps.setString(i++, inc.getLastName());
            ps.setString(i++, inc.getFirstName());
            ps.setString(i++, inc.getDeptDate());
            ps.setInt(i++, inc.getSeatNumber());
            ps.setString(i++, inc.getFlightClass());
            ps.setString(i++, inc.getMeal());
            ps.setInt(i++, inc.getFromStop());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }

    }

    public boolean cancelReservation(int reservationNumber) {

        String query = "DELETE FROM Reservations WHERE reservation_number = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionUtil.getConn();
            ps = conn.prepareStatement(query);
            int i = 1;
            ps.setInt(i++, reservationNumber);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {

            connectionUtil.close(conn, ps, null, rs);

        }


    }



}
