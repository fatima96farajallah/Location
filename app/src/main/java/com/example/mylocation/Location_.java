package com.example.mylocation;

public class Location_ {

    int id;
    String CreateAt;
    String UpdateAt;
    double Longitude;
    double Latitude;

    public Location_(int id, String createAt, String updateAt, double longitude, double latitude) {
        this.id = id;
        CreateAt = createAt;
        UpdateAt = updateAt;
        Longitude = longitude;
        Latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        UpdateAt = updateAt;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    @Override
    public String toString() {
        return "locations{" +
                "CreateAt='" + CreateAt + '\'' +
                ", UpdateAt='" + UpdateAt + '\'' +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                '}';
    }
}
