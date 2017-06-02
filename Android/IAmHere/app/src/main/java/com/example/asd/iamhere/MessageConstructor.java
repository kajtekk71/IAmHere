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
        //failsafe
        double lattitude = 5;
        double longtitude = 5;
        if(location!=null)
        {
            lattitude = location.getLatitude();
            longtitude = location.getLongitude();
        }
        //eof failsafe
        StringBuilder sb = new StringBuilder();
        sb.append("Coordinates: \n");
        sb.append("Lat: " + Double.toString(lattitude) + "\n");
        sb.append("Long: " + Double.toString(longtitude) + "\n");
        String mapLink = "http://maps.google.com/maps?q=" + Double.toString(lattitude) + "," + Double.toString(longtitude);
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
