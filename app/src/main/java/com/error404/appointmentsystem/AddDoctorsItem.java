package com.error404.appointmentsystem;

public class AddDoctorsItem {
    String name;
    String id;
    String email;
    String phone;
    String degree;
    String speciality;
    String imageName;
    String imageUri;
    boolean expanded;

    public AddDoctorsItem() {
    }

    public AddDoctorsItem(String name, String id, String email, String phone, String degree, String speciality, String imageName, String imageUri) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.degree = degree;
        this.speciality = speciality;
        this.imageName = imageName;
        this.imageUri = imageUri;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
