package com.herokuapp.ezhao.gameoflife;

import android.os.Parcel;
import android.os.Parcelable;

public class PointsArray implements Parcelable {
    private int[] points;

    public PointsArray(int size) {
        points = new int[size];
    }

    private PointsArray(Parcel inp) {
        inp.readIntArray(points);
    }

    public int get(int i) {
        return points[i];
    }

    public void set(int i, int val) {
        points[i] = val;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(points);
    }

    public static final Parcelable.Creator<PointsArray> CREATOR
            = new Parcelable.Creator<PointsArray>() {

        @Override
        public PointsArray createFromParcel(Parcel source) {
            return new PointsArray(source);
        }

        @Override
        public PointsArray[] newArray(int size) {
            return new PointsArray[size];
        }
    };
}
