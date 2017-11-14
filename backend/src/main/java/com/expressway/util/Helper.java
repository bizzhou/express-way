package com.expressway.util;

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


}
