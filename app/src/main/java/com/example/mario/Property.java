package com.example.mario;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

class Property implements Parcelable
{
	private String PID,HID;
	private int  Floors;
	private int /* float */ MinStayTime, SecurityMultiplier, NoticePeriod;
	private String Name, Type, Address, Landmark, ShortDescription, Rules, inTime, outTime;
	//private GeoPoint Location;
	private Map<String, Boolean> Amenities;
	private List<Uri> propPicUri;

	Property()
	{
		Amenities = new HashMap<>();
		propPicUri=new ArrayList<>();
	}

	private Property(Parcel parcel)
	{
		this.PID = parcel.readString();
		this.HID = parcel.readString();
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
		Amenities = new HashMap<>();
		parcel.readMap(this.Amenities, null);
		propPicUri= new ArrayList<>();
		parcel.readList(propPicUri,Uri.class.getClassLoader());

	}

	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.PID);
		dest.writeString(this.HID);
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
		dest.writeList(propPicUri);



	}


	Property(IncompleteProperty property, Map<String, Boolean> map)
	{
		//PID = property.getPID();
		//HID = property.getHID();
		Floors = property.getFloors();
		MinStayTime = property.getMinStayTime();
		SecurityMultiplier = property.getSecurityMultiplier();
		NoticePeriod = property.getNoticePeriod();
		Name = property.getName();
		Type = property.getType();
		Address = property.getAddress();
		Landmark = property.getLandmark();
		ShortDescription = property.getShortDescription();
		Rules = property.getRules();
		inTime=property.getInTime();
		outTime=property.getOutTime();

		//Location = loc;
		Amenities = new HashMap<>();
		Amenities.putAll(map);
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
		Location = loc;
		Amenities = new HashMap<>();
		Amenities.putAll(map);
	}*/

	public List<Uri> getPropPicUri()
	{
		return propPicUri;
	}

	public void setPropPicUri(List<Uri> propPicUri)
	{
		this.propPicUri = propPicUri;

	}

	String getPID() { return PID; }
	String getHID() { return HID; }
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
	public String getInTime() { return inTime; }
	public String getOutTime() { return outTime; }
	//GeoPoint getLocation() {return Location; }
	Map<String, Boolean> getAmenities() { return Amenities; }
	//public List<Room> getRooms() { return rooms; }

	void setPID(String pID) { PID = pID; }
	void setHID(String hID) { HID = hID; }
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
	public void setInTime(String in) { inTime = in; }
	public void setOutTime(String in) { outTime = in; }
	//void setLocation(GeoPoint location) {Location = location; }
	void setAmenities(Map<String, Boolean> amenities) { Amenities.clear();Amenities.putAll(amenities); }
	//public void setRooms(List<Room> r) { rooms.clear();if(r!=null)rooms.addAll(r); }

	@Override
	public int describeContents()
	{
		return 0;
	}




	public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>()
	{

		@Override
		public Property createFromParcel(Parcel source)
		{
			return new Property(source);
		}

		@Override
		public Property[] newArray(int size)
		{
			return new Property[size];
		}
	};
}
