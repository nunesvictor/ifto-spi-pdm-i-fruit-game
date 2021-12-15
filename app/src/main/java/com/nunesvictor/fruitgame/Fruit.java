package com.nunesvictor.fruitgame;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Fruit implements Parcelable {
    private final String name;
    private final Integer value;
    private final Integer image;

    public Fruit(String name, Integer value, Integer image) {
        this.name = name;
        this.value = value;
        this.image = image;
    }

    protected Fruit(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readInt();
        }
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(value);
        }
        if (image == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(image);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getImage() {
        return image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", image=" + image +
                '}';
    }
}
