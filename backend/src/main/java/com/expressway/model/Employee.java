package com.expressway.model;

public class Employee extends Person{

    private String username;
    private String password;

    private String ssn;
    private Boolean isManger;
    private Double hourly_rate;
    private String telephone;

    public Employee() {
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

    public Boolean getManger() {
        return isManger;
    }

    public void setManger(Boolean manger) {
        isManger = manger;
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
}
