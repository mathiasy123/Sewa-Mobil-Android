package com.path_studio.arphatapp.Model;

public class Jenis_Mobil {

    private int id_jenis;
    private String jenis_mobil;

    public Jenis_Mobil(){ }

    public Jenis_Mobil(int id, String jenis_mobil){
        this.id_jenis = id;
        this.jenis_mobil = jenis_mobil;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public String getJenis_mobil() {
        return jenis_mobil;
    }

}
