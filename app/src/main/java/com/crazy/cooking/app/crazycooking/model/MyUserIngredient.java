package com.crazy.cooking.app.crazycooking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyUserIngredient implements Parcelable {
    double quantity;
    String measure,ingredient;

    public MyUserIngredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected MyUserIngredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyUserIngredient> CREATOR = new Creator<MyUserIngredient>() {
        @Override
        public MyUserIngredient createFromParcel(Parcel in) {
            return new MyUserIngredient(in);
        }

        @Override
        public MyUserIngredient[] newArray(int size) {
            return new MyUserIngredient[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
