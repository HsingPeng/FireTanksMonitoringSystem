package cn.edu.njupt.tanksms.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by DEEP on 2015/1/29.
 */
public class MainListItem implements Serializable{

    private int eid ;
    private String ename ;
    private int temp ;
    private int gage ;
    private int level ;

    public MainListItem(int eid) {
        this.eid = eid;
    }

    public MainListItem(int eid, String ename, int temp, int gage, int level) {
        this.eid = eid;
        this.ename = ename;
        this.temp = temp;
        this.gage = gage;
        this.level = level;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getGage() {
        return gage;
    }

    public void setGage(int gage) {
        this.gage = gage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainListItem)) return false;

        MainListItem that = (MainListItem) o;

        if (eid != that.eid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return eid;
    }

    @Override
    public String toString() {
        return "MainListItem{" +
                "eid=" + eid +
                ", ename='" + ename + '\'' +
                ", temp=" + temp +
                ", gage=" + gage +
                ", level=" + level +
                '}';
    }

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eid);
        dest.writeString(ename);
        dest.writeInt(temp);
        dest.writeInt(gage);
        dest.writeInt(level);
    }*/

}
