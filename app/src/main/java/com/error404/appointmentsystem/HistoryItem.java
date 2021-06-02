package com.error404.appointmentsystem;

public class HistoryItem {
    String date;
    String message;
    String doctorid;

    public HistoryItem() {
    }

    public HistoryItem(String date, String message, String doctorid) {
        this.date = date;
        this.message = message;
        this.doctorid = doctorid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }
}