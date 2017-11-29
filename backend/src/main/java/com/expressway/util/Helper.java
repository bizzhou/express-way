package com.expressway.util;

import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@Repository
public class Helper {

    public Integer integerId(String s){

        Integer sum = 0;

        for(int i = 0; i < s.length(); i++){

            if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                sum *= 10;
                sum += s.charAt(i) - '0';
            }
        }

        return sum;

    }

    /**
     * Get first day of the month
     * @param year
     * @param month
     * @return yyyy/mm/01
     */
    public String getStartDate(String year, String month) {
        return year + "/" + month + "/01";
    }

    /**
     * Get last day of the month
     * @param year
     * @param month
     * @return yyyy/mm/LAST_DAY
     */
    public String getEndDate(String year, String month) {
        String randomDate = month + "/" + "01/" + year;
        LocalDate convertedDate = LocalDate.parse(randomDate, DateTimeFormatter.ofPattern("M/d/yyyy"));
        convertedDate = convertedDate.withDayOfMonth(
                convertedDate.getMonth().length(convertedDate.isLeapYear()));
        return convertedDate + "";
    }

    public Date convertStringToDate(String dateStr) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;

        try {

            date = formatter.parse(dateStr);

        } catch (ParseException p) {

            p.printStackTrace();

        }

        return date;

    }


    public List converResultToList(ResultSet rs) throws SQLException {

        List<Map<String, Object>> data = new ArrayList<>();

        ResultSetMetaData metaData = rs.getMetaData();

        while(rs.next()) {
            int colCount = metaData.getColumnCount();
            Map<String, Object> row = new HashMap<>(colCount);

            for (int i = 1; i <= colCount; i++) {
                row.put(metaData.getColumnName(i), rs.getObject(i));
            }
            data.add(row);
        }

        return data;

    }





}
