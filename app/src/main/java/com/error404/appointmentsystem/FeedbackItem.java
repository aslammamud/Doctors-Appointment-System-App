package com.error404.appointmentsystem;

public class FeedbackItem {
    String namefeedback;
    String emailfeedback;
    String ratingfeedback;
    String ratingdoctorid;
    String ratingdoctorname;
    String messagefeedback;

    public FeedbackItem() {
    }

    public FeedbackItem(String namefeedback, String emailfeedback, String ratingfeedback, String ratingdoctorid, String ratingdoctorname, String messagefeedback) {
        this.namefeedback = namefeedback;
        this.emailfeedback = emailfeedback;
        this.ratingfeedback = ratingfeedback;
        this.messagefeedback = messagefeedback;
        this.ratingdoctorid = ratingdoctorid;
        this.ratingdoctorname = ratingdoctorname;
    }

    public String getRatingdoctorid() {
        return ratingdoctorid;
    }

    public void setRatingdoctorid(String ratingdoctorid) {
        this.ratingdoctorid = ratingdoctorid;
    }

    public String getRatingdoctorname() {
        return ratingdoctorname;
    }

    public void setRatingdoctorname(String ratingdoctorname) {
        this.ratingdoctorname = ratingdoctorname;
    }

    public String getNamefeedback() {
        return namefeedback;
    }

    public void setNamefeedback(String namefeedback) {
        this.namefeedback = namefeedback;
    }

    public String getEmailfeedback() {
        return emailfeedback;
    }

    public void setEmailfeedback(String emailfeedback) {
        this.emailfeedback = emailfeedback;
    }

    public String getRatingfeedback() {
        return ratingfeedback;
    }

    public void setRatingfeedback(String ratingfeedback) {
        this.ratingfeedback = ratingfeedback;
    }

    public String getMessagefeedback() {
        return messagefeedback;
    }

    public void setMessagefeedback(String messagefeedback) {
        this.messagefeedback = messagefeedback;
    }
}
