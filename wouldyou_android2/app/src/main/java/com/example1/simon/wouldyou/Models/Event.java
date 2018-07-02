package com.example1.simon.wouldyou.Models;

public class Event {

    String name;
    String description;
    double lat;
    double lon;
    String time_start_event;
    String time_end_event;
    int user_id;
    int category_id;
    int typology_id;

    public Event(String name, String description, double lat, double lon, String time_start_event, String time_end_event, int user_id, int category_id, int typology_id) {
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.time_start_event = time_start_event;
        this.time_end_event = time_end_event;
        this.user_id = user_id;
        this.category_id = category_id;
        this.typology_id = typology_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTime_start_event() {
        return time_start_event;
    }

    public void setTime_start_event(String time_start_event) {
        this.time_start_event = time_start_event;
    }

    public String getTime_end_event() {
        return time_end_event;
    }

    public void setTime_end_event(String time_end_event) {
        this.time_end_event = time_end_event;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getTypology_id() {
        return typology_id;
    }

    public void setTypology_id(int typology_id) {
        this.typology_id = typology_id;
    }

}
