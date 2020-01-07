package com.example.dontscare.bean;

public class ContactBean {
    private String name;
    private int imageId;
    private String location;
    private String status;

    public ContactBean(String name, int imageId, String location, String status){
        this.name=name;
        this.imageId=imageId;
        this.location=location;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
