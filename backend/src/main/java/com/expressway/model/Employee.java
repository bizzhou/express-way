package com.expressway.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee extends Person{

    @JsonProperty
    private boolean isManger;

    private String username;
    private String password;
    private String ssn;
    private Double hourly_rate;
    private String telephone;

    public Employee() {
    }

    public boolean isManger() {
        return isManger;
    }

    public void setManger(boolean isManger) {
        this.isManger = isManger;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }



    public Double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(Double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ssn='" + ssn + '\'' +
                ", isManger=" + isManger +
                ", hourly_rate=" + hourly_rate +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
