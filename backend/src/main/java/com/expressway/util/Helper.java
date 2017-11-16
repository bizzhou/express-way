package com.expressway.util;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
