package com.example.dontscare;

public class UserContact {
    private String name;
    private int imageId;
    private String location;
    private String status;

    public UserContact(String name,int imageId,String location,String status){
        this.name=name;
        this.imageId=imageId;
        this.location=location;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getLocation(){
        return location;
    }

    public String getStatus(){
        return status;
    }
}
