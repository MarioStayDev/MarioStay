package com.example.mario;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "properties")
public class IncompleteProperty {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid") private int PID;
    //@ColumnInfo(name = "hid") private int HID;
    @ColumnInfo(name = "floors") private int Floors;

    @ColumnInfo(name = "minStayTime") private int MinStayTime;
    @ColumnInfo(name = "pidsecurityMultiplier") private int SecurityMultiplier;
    @ColumnInfo(name = "noticePeriod") private int NoticePeriod;

    @ColumnInfo(name = "name") private String Name;
    @ColumnInfo(name = "type") private String Type;
    @ColumnInfo(name = "address") private String Address;
    @ColumnInfo(name = "landmark") private String Landmark;
    @ColumnInfo(name = "shortDescription") private String ShortDescription;
    @ColumnInfo(name = "rules") private String Rules;
    //private GeoPoint Location;

    //@ColumnInfo(name = "amenities") private Map<String, Boolean> Amenities;
    @ColumnInfo(name = "amenitiesLift") private boolean lift;
    @ColumnInfo(name = "amenitiesParking") private boolean parking;
    @ColumnInfo(name = "amenitiesCCTV") private boolean cctv;
    @ColumnInfo(name = "amenitiesPower") private boolean power;
    @ColumnInfo(name = "amenitiesPlayground") private boolean playground;
    @ColumnInfo(name = "amenitiesPool") private boolean pool;
    @ColumnInfo(name = "amenitiesGarden") private boolean garden;
    @ColumnInfo(name = "amenitiesGym") private boolean gym;
    @ColumnInfo(name = "amenitiesTV") private boolean tv;
    @ColumnInfo(name = "amenitiesRefrigerator") private boolean fridge;
    @ColumnInfo(name = "amenitiesWashingMac") private boolean washMac;
    @ColumnInfo(name = "amenitiesWater") private boolean water;
    @ColumnInfo(name = "amenitiesWifi") private boolean wifi;
    @ColumnInfo(name = "amenitiesSofa") private boolean sofa;
    @ColumnInfo(name = "amenitiesTable") private boolean ttable;

    int getPID() { return PID; }
    //int getHID() { return HID; }
    int getFloors() { return Floors; }
    int getMinStayTime() { return MinStayTime; }
    int getSecurityMultiplier() { return SecurityMultiplier; }
    int getNoticePeriod() { return NoticePeriod; }
    String getName() { return Name; }
    String getType() { return Type; }
    String getAddress() { return Address; }
    String getLandmark() { return Landmark; }
    String getShortDescription() { return ShortDescription; }
    String getRules() { return Rules; }
    //GeoPoint getLocation() {return Location; }
    /*Map getAmenities() {
        HashMap<String, Boolean> Amenities = new HashMap<>();
        Amenities.put("Lift Service", a);
        Amenities.put("Parking", b);
        Amenities.put("CCTV camera", c);
        Amenities.put("Power backup", d);
        Amenities.put("Playground", e);
        Amenities.put("Swimming pool", f);
        Amenities.put("Garden", g);
        Amenities.put("Gym", h);
        Amenities.put("Television", i);
        Amenities.put("Refrigerator", j);
        Amenities.put("Washing machine", k);
        Amenities.put("Water purifier", l);
        Amenities.put("Wifi", m);
        Amenities.put("Sofa", n);
        Amenities.put("Table", o);
        return Amenities;
    }*/
    boolean getLift() { return lift; }
    boolean getParking() { return parking; }
    boolean getCctv() { return cctv; }
    boolean getPower() { return power; }
    boolean getPlayground() { return playground; }
    boolean getPool() { return pool; }
    boolean getGarden() { return garden; }
    boolean getGym() { return gym; }
    boolean getTv() { return tv; }
    boolean getFridge() { return fridge; }
    boolean getWashMac() { return washMac; }
    boolean getWater() { return water; }
    boolean getWifi() { return wifi; }
    boolean getSofa() { return sofa; }
    boolean getTtable() { return ttable; }


    void setPID(int pID) { PID = pID; }
    //void setHID(int hID) { HID = hID; }
    void setFloors(int floors) { Floors = floors; }
    void setMinStayTime(int minStayTime) { MinStayTime = minStayTime; }
    void setSecurityMultiplier(int securityMultiplier) { SecurityMultiplier = securityMultiplier; }
    void setNoticePeriod(int noticePeriod) { NoticePeriod = noticePeriod; }
    void setName(String name) { Name = name; }
    void setType(String type) { Type = type; }
    void setAddress(String address) { Address = address; }
    void setLandmark(String landmark) { Landmark = landmark; }
    void setShortDescription(String desc) { ShortDescription = desc; }
    void setRules(String rules) { Rules = rules; }
    //void setLocation(GeoPoint location) {Location = location; }
    /*void setAmenities(Map<String, Boolean> amenities) {
        a = amenities.get("Lift Service");
        b = amenities.get("Parking");
        c = amenities.get("CCTV camera");
        d = amenities.get("Power backup");
        e = amenities.get("Playground");
        f = amenities.get("Swimming pool");
        g = amenities.get("Garden");
        h = amenities.get("Gym");
        i = amenities.get("Television");
        j = amenities.get("Refrigerator");
        k = amenities.get("Washing machine");
        l = amenities.get("Water purifier");
        m = amenities.get("Wifi");
        n = amenities.get("Sofa");
        o = amenities.get("Table");
    }*/
    void setLift(boolean a) { lift = a; }
    void setParking(boolean a) { parking = a; }
    void setCctv(boolean a) { cctv = a; }
    void setPower(boolean a) { power = a; }
    void setPlayground(boolean a) { playground = a; }
    void setPool(boolean a) { pool = a; }
    void setGarden(boolean a) { garden = a; }
    void setGym(boolean a) { gym = a; }
    void setTv(boolean a) { tv = a; }
    void setFridge(boolean a) { fridge = a; }
    void setWashMac(boolean a) { washMac = a; }
    void setWater(boolean a) { water = a; }
    void setWifi(boolean a) { wifi = a; }
    void setSofa(boolean a) { sofa = a; }
    void setTtable(boolean a) { ttable = a; }

}