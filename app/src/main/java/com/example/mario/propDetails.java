package com.example.mario;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class propDetails
{
    private static final propDetails INSTANCE = new propDetails();

    public int  propHID, propFloors;
        public int propMinStayTime, propSecurityMultiplier, propNoticePeriod;
        public String propName, propType, propAddress, propLandmark, propShortDescription, propRules,propInTime,propOutTime;
        public GeoPoint propLocation;
        public HashMap<String, Boolean > Amenities=new HashMap<>();

        private propDetails()
        {

        }

        public static propDetails getInstance()
    //(String Name,String Address,String Landmark,String BuildType,int NoOfFloors,int MinStayTime,
      //                      int SecMUlti,String InTime,String OutTime,int NoticePeriod,GeoPoint Location,String Desc,String PropRules)
        {
            //this.PID = parcel.readInt();
            //this.HID = parcel.readInt();
            /**this.Floors = NoOfFloors;

            this.MinStayTime = MinStayTime;
            this.SecurityMultiplier = SecMUlti;
            this.NoticePeriod = NoticePeriod;
            this.inTime=InTime;
            this.outTime=OutTime;
            this.Location=Location;
            this.Name = Name;
            this.Type = BuildType;
            this.Address = Address;
            this.Landmark = Landmark;
            this.ShortDescription = Desc;
            this.Rules = PropRules;*/

            return INSTANCE;


        }






    }

