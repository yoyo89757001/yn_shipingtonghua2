package com.example.yinian.menkou.yn_shipingtonghua.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class BaoCunBean implements Parcelable {

    private String phone;
    private String pwd;
    private boolean isA; //是否是家属端 true是  false不是
    private String jigou;
    private String jigouId;
    private String nurseName;
    private String nurseCode;
    private String headImg;
    private String token;
    private String registrationId;
    private String roomId;
    private String userId;


    protected BaoCunBean(Parcel in) {
        phone = in.readString();
        pwd = in.readString();
        isA = in.readByte() != 0;
        jigou = in.readString();
        jigouId = in.readString();
        nurseName = in.readString();
        nurseCode = in.readString();
        headImg = in.readString();
        token = in.readString();
        registrationId = in.readString();
        roomId = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
        dest.writeString(pwd);
        dest.writeByte((byte) (isA ? 1 : 0));
        dest.writeString(jigou);
        dest.writeString(jigouId);
        dest.writeString(nurseName);
        dest.writeString(nurseCode);
        dest.writeString(headImg);
        dest.writeString(token);
        dest.writeString(registrationId);
        dest.writeString(roomId);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaoCunBean> CREATOR = new Creator<BaoCunBean>() {
        @Override
        public BaoCunBean createFromParcel(Parcel in) {
            return new BaoCunBean(in);
        }

        @Override
        public BaoCunBean[] newArray(int size) {
            return new BaoCunBean[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getNurseCode() {
        return nurseCode;
    }

    public void setNurseCode(String nurseCode) {
        this.nurseCode = nurseCode;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getJigou() {
        return jigou;
    }

    public void setJigou(String jigou) {
        this.jigou = jigou;
    }

    public String getJigouId() {
        return jigouId;
    }

    public void setJigouId(String jigouId) {
        this.jigouId = jigouId;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public BaoCunBean() {
    }

    @Override
    public String toString() {
        return "BaoCunBean{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", isA=" + isA +
                ", jigou='" + jigou + '\'' +
                ", jigouId='" + jigouId + '\'' +
                ", nurseName='" + nurseName + '\'' +
                ", nurseCode='" + nurseCode + '\'' +
                ", headImg='" + headImg + '\'' +
                ", token='" + token + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
