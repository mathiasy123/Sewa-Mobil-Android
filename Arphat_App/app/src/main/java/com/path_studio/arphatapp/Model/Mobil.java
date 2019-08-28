package com.path_studio.arphatapp.Model;

public class Mobil {

    private int id_jenis;
    private String plat;
    private int kursi;
    private int harga;
    private String status;

    public Mobil(){ }

    public Mobil(int jenis, String plat, int kursi, int harga, String status){
        this.id_jenis = jenis;
        this.plat = plat;
        this.kursi = kursi;
        this.harga = harga;
        this.status = status;
    }

    public int getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }

    public int getKursi() {
        return kursi;
    }

    public String getPlat() {
        return plat;
    }

    public int getId_jenis() {
        return id_jenis;
    }

}
