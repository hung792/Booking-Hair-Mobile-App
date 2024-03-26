package com.example.hairsara.Request;

public class ChiNhanhRequest {
    private String tenChiNhanh;
    private String diaChi;

    public ChiNhanhRequest(String tenChiNhanh, String diaChi) {
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}

