package com.example1.simon.wouldyou.Models;

public class Message {

    int id;
    String text;
    int user_id;
    int event_id;

    public Message(int id, String text, int user_id, int event_id) {
        this.id = id;
        this.text = text;
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

}
