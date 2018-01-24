package com.example.vuphu.giatui2;

/**
 * Created by vuphu on 12/14/2017.
 */

public class nguoiDung {

    private String email;
    private String name;
    private String phoneNumber;
    private String diaChi;
    private String location;
    private long point;
    private long donHang,donHangTon;

    public nguoiDung() {
    }

    public nguoiDung(String email, String name, String phoneNumber, String diaChi, String location) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.diaChi = diaChi;
        this.location = location;
        this.point = 0;
        this.donHang = 0;
        this.donHangTon = 0;
    }

    public nguoiDung(String email, String name, String phoneNumber, String diaChi, String location, long point, long donHang, long donHangTon) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.diaChi = diaChi;
        this.location = location;
        this.point = point;
        this.donHang = donHang;
        this.donHangTon = donHangTon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getDonHang() {
        return donHang;
    }

    public void setDonHang(long donHang) {
        this.donHang = donHang;
    }

    public long getDonHangTon() {
        return donHangTon;
    }

    public void setDonHangTon(long donHangTon) {
        this.donHangTon = donHangTon;
    }
}
