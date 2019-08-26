package com.path_studio.arphatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

public class slider_adapter_choose_car extends PagerAdapter{
    Context context;
    LayoutInflater layoutInflater;

    public int [] mobil = {
            R.drawable.mobil_alya,
            R.drawable.mobil_yaris,
            R.drawable.mobil_avanza,
            R.drawable.mobil_calya,
            R.drawable.mobil_inova,
            R.drawable.mobil_xenia,
            R.drawable.mobil_mobilio,
            R.drawable.mobil_elf,
            R.drawable.mobil_hiace
    };

    public String[] merek_mobil = {
            "Toyota Calya",
            "Toyota Yaris",
            "Toyota Avanza",
            "Toyota Calya",
            "Toyota Kijang Inova",
            "Daihatsu Xenia",
            "Honda Mobilio",
            "Isuzu Elf",
            "Toyota Hiace"
    };

    public int[] jml_kapasitas = {7,5,7,7,7,7,6,12,16};

    private String[] tipe_mobil = {"Small","Medium","Large"};

    public slider_adapter_choose_car(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return mobil.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (LinearLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.template_slider_choose_car, container, false);

        //set gambar dan tulisan sesuai dengan database
        ImageView slideImageView    = (ImageView) view.findViewById(R.id.gambar_mobil);
        slideImageView.setImageResource(mobil[position]);

        TextView merek = (TextView) view.findViewById(R.id.merek_mobil);
        merek.setText(merek_mobil[position]);

        TextView kapasitas = (TextView) view.findViewById(R.id.kapasitas_penumpang);
        kapasitas.setText(jml_kapasitas[position] + " person");

        RelativeLayout capasity = (RelativeLayout) view.findViewById(R.id.car_capasity);
        TextView teks_kapasitas = (TextView) view.findViewById(R.id.text_capasity);

        if((jml_kapasitas[position])>0 && (jml_kapasitas[position])<=5){
            //small
            capasity.setBackground(ContextCompat.getDrawable(context,R.drawable.car_type_green));
            teks_kapasitas.setText(tipe_mobil[0]);
        }else if((jml_kapasitas[position])>5 && (jml_kapasitas[position])<=10){
            //medium
            capasity.setBackground(ContextCompat.getDrawable(context,R.drawable.car_type_orange));
            teks_kapasitas.setText(tipe_mobil[1]);
        }else if((jml_kapasitas[position])>10 && (jml_kapasitas[position])<=20){
            //large
            capasity.setBackground(ContextCompat.getDrawable(context,R.drawable.car_type_red));
            teks_kapasitas.setText(tipe_mobil[2]);
        }

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
    }

}
