package com.aire.android.aidlconn.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ZhuPeipei
 * @date 2021/12/4 11:49
 */
public class Book implements Parcelable {
    private String name;
    private float price;

    public Book() {
    }

    public Book(String name, float price) {
        this.name = name;
        this.price = price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
    }

    public void readFromParcel(Parcel parcel) {
        name = parcel.readString();
        price = parcel.readFloat();
    }
}
