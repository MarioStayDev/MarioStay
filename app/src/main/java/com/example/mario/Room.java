package com.example.mario;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Room /*implements Parcelable*/ {

    private int roomNo, floor, beds, rent;
    private Map<String, Boolean> imap;
    //private Photos pics;

    Room() {
        imap = new HashMap<>();
    }

    public int getRoomNo() { return roomNo; }
    public int getFloor() { return floor; }
    public int getBeds() { return beds; }
    public int getRent() { return rent; }
    public Map getAmenities() { return imap; }

    public void setRoomNo(int r) { this.roomNo = r; }
    public void setFloor(int r) { this.floor = r; }
    public void setBeds(int r) { this.beds = r; }
    public void setRent(int r) { this.rent = r; }
    public void setAmenities(Map<String, Boolean> m) { imap.putAll(m); }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.roomNo);
        dest.writeInt(this.floor);
        dest.writeInt(this.beds);
        dest.writeInt(this.rent);
        dest.writeMap(imap);
    }

    private Room(Parcel source) {
        roomNo = source.readInt();
        floor = source.readInt();
        beds = source.readInt();
        rent = source.readInt();
        source.readMap(imap, getClass().getClassLoader());
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {

        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };*/

}
