package com.crazy.cooking.app.crazycooking.model;

import android.os.Parcel;
import android.os.Parcelable;


public class MyUserJson implements Parcelable{
    int id;
    String name,ingredientsMain,stepsMain;

    public MyUserJson(int sid, String sname, String ingredientsMain, String stepsMain) {
        this.id = sid;
        this.name = sname;
        this.ingredientsMain = ingredientsMain;
        this.stepsMain = stepsMain;
    }

    public String getIngredientsMain() {
        return ingredientsMain;
    }

    public void setIngredientsMain(String ingredientsMain) {
        this.ingredientsMain = ingredientsMain;
    }

    public String getStepsMain() {
        return stepsMain;
    }

    public void setStepsMain(String stepsMain) {
        this.stepsMain = stepsMain;
    }

    public static Creator<MyUserJson> getCREATOR() {
        return CREATOR;
    }

    public MyUserJson() {

    }

    protected MyUserJson(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientsMain = in.readString();
        stepsMain = in.readString();
    }

    public static final Creator<MyUserJson> CREATOR = new Creator<MyUserJson>() {
        @Override
        public MyUserJson createFromParcel(Parcel in) {
            return new MyUserJson(in);
        }

        @Override
        public MyUserJson[] newArray(int size) {
            return new MyUserJson[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(ingredientsMain);
        dest.writeString(stepsMain);
    }
}
