package com.example.mario;

import java.util.HashMap;
import java.util.Map;

public class Room
{

    private String roomId;
    private int noOfFloors;
    private int noOfBeds;
    private  int monRent;
    private Map<String,Boolean> roomAmmenities;
    private Map<String,String> roomPhotos;

    Room()
    {
        roomAmmenities = new HashMap<>();
        roomPhotos = new HashMap<>();
    }

    public String getRoomId()
    {
        return roomId;
    }

    public void setRoomId(String roomId)
    {
        this.roomId = roomId;
    }

    public int getNoOfFloors()
    {
        return noOfFloors;
    }

    public void setNoOfFloors(int noOfFloors)
    {
        this.noOfFloors = noOfFloors;
    }

    public int getNoOfBeds()
    {
        return noOfBeds;
    }

    public void setNoOfBeds(int noOfBeds)
    {
        this.noOfBeds = noOfBeds;
    }

    public int getMonRent()
    {
        return monRent;
    }

    public void setMonRent(int monRent)
    {
        this.monRent = monRent;
    }

    public Map<String, Boolean> getRoomAmmenities()
    {
        return roomAmmenities;
    }

    public void setRoomAmmenities(Map<String, Boolean> roomAmmenities)
    {
        this.roomAmmenities = roomAmmenities;
    }

    public Map<String, String> getRoomPhotos()
    {
        return roomPhotos;
    }

    public void setRoomPhotos(Map<String, String> roomPhotos)
    {
        this.roomPhotos = roomPhotos;
    }
}
