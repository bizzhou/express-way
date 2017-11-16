package com.expressway.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
public class Helper {

    public static Integer integerId(String s){

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


}
