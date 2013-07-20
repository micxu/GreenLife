package com.buaa.baidumap;

import android.os.Parcel;
import android.os.Parcelable;

public class GPSPoint implements Parcelable{
	public GPSPoint(){
		
	}
	public GPSPoint(double x_, double y_){
		x = x_;
		y = y_;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public double x;
	public double y;
	String title;
	String id;
	
	public static final Parcelable.Creator<GPSPoint> CREATOR = new 
			Creator<GPSPoint>() {

				@Override
				public GPSPoint createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					GPSPoint point = new GPSPoint();
					point.x = source.readDouble();
					point.y = source.readDouble();
					point.title = source.readString();
					point.id = source.readString();
					return point;
				}

				@Override
				public GPSPoint[] newArray(int size) {
					// TODO Auto-generated method stub
					return new GPSPoint[size];
				}
		
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(x);
		dest.writeDouble(y);
		dest.writeString(id);
		dest.writeString(title);
	}

}
