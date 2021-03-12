package com.mohammadKZ.CoronaVirus_COVID19_statistics.model;

public class Country {

    private String country, todayCases, cases, todayDeaths, deaths, recovered, active, todayRecovered, flag;

    public Country(String country, String todayCases, String cases, String todayDeaths, String deaths, String recovered, String active, String todayRecovered, String flag) {
        this.country = country;
        this.todayCases = todayCases;
        this.cases = cases;
        this.todayDeaths = todayDeaths;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.todayRecovered = todayRecovered;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
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
