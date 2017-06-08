package com.example.android.restful.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nandaz on 07/06/2017.
 */

public class DataItem implements Parcelable {

    private String itemName;
    private String category;
    private String description;
    private Integer sort;
    private double price;
    private String image;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemName);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeValue(this.sort);
        dest.writeDouble(this.price);
        dest.writeString(this.image);
    }

    public DataItem() {
    }

    protected DataItem(Parcel in) {
        this.itemName = in.readString();
        this.category = in.readString();
        this.description = in.readString();
        this.sort = (Integer) in.readValue(Integer.class.getClassLoader());
        this.price = in.readDouble();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}