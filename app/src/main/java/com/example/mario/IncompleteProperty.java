package com.example.mario;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Entity(tableName = "properties")
public class IncompleteProperty  implements Parcelable
{

    @PrimaryKey(autoGenerate = true)


    @ColumnInfo(name = "pid") private int PID;
    //@ColumnInfo(name = "hid") private String HID;

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
    @ColumnInfo(name = "inTime") private String InTime;
    @ColumnInfo(name = "outTime") private String OutTime;
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

    @ColumnInfo(name = "pic1") private String picUri_1;
    @ColumnInfo(name = "pic2") private String picUri_2;

    @ColumnInfo(name = "pic3") private String picUri_3;
    @ColumnInfo(name = "pic4") private String picUri_4;




    @Ignore private int i=0;

    IncompleteProperty() { }

    IncompleteProperty(Parcel parcel)
    {
        PID = parcel.readInt();
        Floors = parcel.readInt();
        MinStayTime = parcel.readInt();
        SecurityMultiplier = parcel.readInt();
        NoticePeriod = parcel.readInt();
        Name = parcel.readString();
        Type = parcel.readString();
        Address = parcel.readString();
        Landmark = parcel.readString();
        ShortDescription = parcel.readString();
        Rules = parcel.readString();
        InTime = parcel.readString();
        OutTime = parcel.readString();
        //photosUri=new ArrayList<>();
        //parcel.readStringArray(picUri_1);
        picUri_1=parcel.readString();
        picUri_2=parcel.readString();
        picUri_3=parcel.readString();
        picUri_4=parcel.readString();



        boolean[] amenities = new boolean[15];
        parcel.readBooleanArray(amenities);
        lift = amenities[0];
        parking = amenities[1];
        cctv = amenities[2];
        power = amenities[3];
        playground = amenities[4];
        pool = amenities[5];
        garden = amenities[6];
        gym = amenities[7];
        tv = amenities[8];
        fridge = amenities[9];
        washMac = amenities[10];
        water = amenities[11];
        wifi = amenities[12];
        sofa = amenities[13];
        ttable = amenities[14];
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(PID);
        //dest.writeInt(HID);
        dest.writeInt(Floors);
        dest.writeInt(MinStayTime);
        dest.writeInt(SecurityMultiplier);
        dest.writeInt(NoticePeriod);

        dest.writeString(Name);
        dest.writeString(Type);
        dest.writeString(Address);
        dest.writeString(Landmark);
        dest.writeString(ShortDescription);
        dest.writeString(Rules);
        dest.writeString(InTime);
        dest.writeString(OutTime);
        dest.writeString(picUri_1);
        dest.writeString(picUri_2);
        dest.writeString(picUri_3);
        dest.writeString(picUri_4);


        dest.writeBooleanArray(new boolean[] {lift, parking, cctv, power, playground, pool, garden, gym, tv, fridge, washMac, water, wifi, sofa, ttable});
    }

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
    String getInTime() { return InTime; }
    String getOutTime() { return OutTime; }

    public String getPicUri_1()
    {
        return picUri_1;
    }

    public void setPicUri_1(String picUri_1)
    {
        this.picUri_1 = picUri_1;
    }

    public String getPicUri_2()
    {
        return picUri_2;
    }

    public void setPicUri_2(String picUri_2)
    {
        this.picUri_2 = picUri_2;
    }


    public String getPicUri_3()
    {
        return picUri_3;
    }

    public void setPicUri_3(String picUri_3)
    {
        this.picUri_3 = picUri_3;
    }

    public String getPicUri_4()
    {
        return picUri_4;
    }

    public void setPicUri_4(String picUri_4)
    {
        this.picUri_4 = picUri_4;
    }

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
    void setInTime(String rules) { InTime = rules; }
    void setOutTime(String rules) { OutTime = rules; }
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



    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<IncompleteProperty> CREATOR = new Parcelable.Creator<IncompleteProperty>()
    {

        @Override
        public IncompleteProperty createFromParcel(Parcel source)
        {
            return new IncompleteProperty(source);
        }

        @Override
        public IncompleteProperty[] newArray(int size) {
            return new IncompleteProperty[size];
        }
    };
}
