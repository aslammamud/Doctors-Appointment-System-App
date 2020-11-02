package com.error404.appointmentsystem;

public class GetAppointmentItem {
    String name;
    String age;
    String gender;
    String bloodgroup;
    String symptoms;
    String phone;
    String address;
    String date;
    String time;
    String doctorname;
    String doctorid;

    public GetAppointmentItem(String name) {
    }

    public GetAppointmentItem(String name, String age, String gender, String bloodgroup, String symptoms, String phone, String address, String date, String time, String doctorname, String doctorid) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.symptoms = symptoms;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.time = time;
        this.doctorname = doctorname;
        this.doctorid = doctorid;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
