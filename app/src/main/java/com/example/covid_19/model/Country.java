package com.example.covid_19.model;

public class Country {

    private String country,  todayCases, cases, todayDeaths, deaths, recovered, active  ;

    public Country(String country, String todayCases, String cases, String todayDeaths, String deaths, String recovered, String active) {
        this.country = country;
        this.todayCases = todayCases;
        this.cases = cases;
        this.todayDeaths = todayDeaths;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
