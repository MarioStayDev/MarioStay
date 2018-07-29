package com.example.mario;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

class Property implements Parcelable {
	private int PID, HID, Floors;
	private int /* float */ MinStayTime, SecurityMultiplier, NoticePeriod;
	private String Name, Type, Address, Landmark, ShortDescription, Rules, inTime, outTime;
	//private GeoPoint Location;
	private Map<String, Boolean> Amenities;

	Property() {
		Amenities = new HashMap<>();
	}

	private Property(Parcel parcel) {
		this.PID = parcel.readInt();
		this.HID = parcel.readInt();
		this.Floors = parcel.readInt();

		this.MinStayTime = parcel.readInt();
		this.SecurityMultiplier = parcel.readInt();
		this.NoticePeriod = parcel.readInt();

		this.Name = parcel.readString();
		this.Type = parcel.readString();
		this.Address = parcel.readString();
		this.Landmark = parcel.readString();
		this.ShortDescription = parcel.readString();
		this.Rules = parcel.readString();
		this.inTime = parcel.readString();
		this.outTime = parcel.readString();

		parcel.readMap(this.Amenities, null);
	}

	/*Property(int propertyID, int hostID, int floors,
			int min, int secmul, int notper
			, String name, String type, String add, String land, String desc, String rules, GeoPoint loc, Map<String, Boolean> map) {
		PID = propertyID;
		HID = hostID;
		Floors = floors;
		MinStayTime = min;
		SecurityMultiplier = secmul;
		NoticePeriod = notper;
		Name = name;
		Type = type;
		Address = add;
		Landmark = land;
		ShortDescription = desc;
		Rules = rules;
		//Location = loc;
		Amenities = new HashMap<>();
		Amenities.putAll(map);
	}*/

	Property(IncompleteProperty p, Map<String, Boolean> map) {
		PID = p.getPID();
		HID = 1;
		Floors = p.getFloors();
		MinStayTime = p.getMinStayTime();
		SecurityMultiplier = p.getSecurityMultiplier();
		NoticePeriod = p.getNoticePeriod();
		Name = p.getName();
		Type = p.getType();
		Address = p.getAddress();
		Landmark = p.getLandmark();
		ShortDescription = p.getShortDescription();
		Rules = p.getRules();
		inTime = p.getInTime();
		outTime = p.getOutTime();

		//Location = loc;
		Amenities = map;
	}

	public int getPID() { return PID; }
	public int getHID() { return HID; }
	public int getFloors() { return Floors; }
	public int getMinStayTime() { return MinStayTime; }
	public int getSecurityMultiplier() { return SecurityMultiplier; }
	public int getNoticePeriod() { return NoticePeriod; }
	public String getName() { return Name; }
	public String getType() { return Type; }
	public String getAddress() { return Address; }
	public String getLandmark() { return Landmark; }
	public String getShortDescription() { return ShortDescription; }
	public String getRules() { return Rules; }
	public String getInTime() { return inTime; }
	public String getOutTime() { return outTime; }
	//GeoPoint getLocation() {return Location; }
	public Map getAmenities() { return Amenities; }

	public void setPID(int pID) { PID = pID; }
	public void setHID(int hID) { HID = hID; }
	public void setFloors(int floors) { Floors = floors; }
	public void setMinStayTime(int minStayTime) { MinStayTime = minStayTime; }
	public void setSecurityMultiplier(int securityMultiplier) { SecurityMultiplier = securityMultiplier; }
	public void setNoticePeriod(int noticePeriod) { NoticePeriod = noticePeriod; }
	public void setName(String name) { Name = name; }
	public void setType(String type) { Type = type; }
	public void setAddress(String address) { Address = address; }
	public void setLandmark(String landmark) { Landmark = landmark; }
	public void setShortDescription(String desc) { ShortDescription = desc; }
	public void setRules(String rules) { Rules = rules; }
	public void setInTime(String in) { inTime = in; }
	public void setOutTime(String in) { outTime = in; }
	//void setLocation(GeoPoint location) {Location = location; }
	public void setAmenities(Map<String, Boolean> amenities) { Amenities.clear();Amenities.putAll(amenities); }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.PID);
		dest.writeInt(this.HID);
		dest.writeInt(this.Floors);

		dest.writeInt(this.MinStayTime);
		dest.writeInt(this.SecurityMultiplier);
		dest.writeInt(this.NoticePeriod);

		dest.writeString(this.Name);
		dest.writeString(this.Type);
		dest.writeString(this.Address);
		dest.writeString(this.Landmark);
		dest.writeString(this.ShortDescription);
		dest.writeString(this.Rules);
		dest.writeString(this.inTime);
		dest.writeString(this.outTime);

		dest.writeMap(Amenities);

	}

	public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() {

		@Override
		public Property createFromParcel(Parcel source) {
			return new Property(source);
		}

		@Override
		public Property[] newArray(int size) {
			return new Property[size];
		}
	};
}