package com.example.asd.iamhere;

import android.graphics.Bitmap;
import android.location.Location;

public class MessageConstructor {
    private String coordinates;
    private Bitmap screenshot;
    private String address;

    public MessageConstructor() {
    }
    public String buildMessage(){
        return address + "\n" + coordinates;
    }
    public Bitmap getBitmap(){
        return screenshot;
    }
    public MessageConstructor setCoordinates(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append("Coordinates: \n");
        sb.append("Lat: " + Double.toString(location.getLatitude()) + "\n");
        sb.append("Long: " + Double.toString(location.getLongitude()) + "\n");
        String mapLink = "http://maps.google.com/maps?q=" + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
        sb.append("Link to maps: " + mapLink + "\n");
        coordinates = sb.toString();
        return this;
    }
    public MessageConstructor setAddress(String address){
        this.address = address;
        return this;
    }
    public MessageConstructor setScreenshot(Bitmap screenshot) {
        this.screenshot = screenshot;
        return this;
    }
}
