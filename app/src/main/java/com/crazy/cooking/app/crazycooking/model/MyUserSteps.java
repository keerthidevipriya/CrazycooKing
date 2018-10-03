package com.crazy.cooking.app.crazycooking.model;

import android.os.Parcel;
import android.os.Parcelable;


public class MyUserSteps implements Parcelable{
    int id;
    String shortDescription,description,videoURL,thumbnailURL;

    public MyUserSteps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    protected MyUserSteps(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<MyUserSteps> CREATOR = new Creator<MyUserSteps>() {
        @Override
        public MyUserSteps createFromParcel(Parcel in) {
            return new MyUserSteps(in);
        }

        @Override
        public MyUserSteps[] newArray(int size) {
            return new MyUserSteps[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
