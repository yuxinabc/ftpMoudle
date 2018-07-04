package com.synertone.ftpmoudle.model;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class FTPPictureModel implements Comparable,Parcelable {
    private String name;
    private String date;
    private String url;
    private final static String FTP="ftp:";

    public FTPPictureModel(String name, String date, String url) {
        this.name = name;
        this.date = date;
        this.url = url;
    }

    protected FTPPictureModel(Parcel in) {
        name = in.readString();
        date = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FTPPictureModel> CREATOR = new Creator<FTPPictureModel>() {
        @Override
        public FTPPictureModel createFromParcel(Parcel in) {
            return new FTPPictureModel(in);
        }

        @Override
        public FTPPictureModel[] newArray(int size) {
            return new FTPPictureModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return  FTP+url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FTPPictureModel that = (FTPPictureModel) o;
        return that.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        FTPPictureModel that= (FTPPictureModel) o;
        return that.getDate().compareTo(this.getDate());
    }
}
