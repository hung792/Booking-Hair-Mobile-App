package com.example.hairsara.Request;

public class DichVuRequest {
        private String tenDichVu;
        private String moTa;

        public DichVuRequest(String tenDichVu, String moTa) {
            this.tenDichVu = tenDichVu;
            this.moTa = moTa;
        }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}

