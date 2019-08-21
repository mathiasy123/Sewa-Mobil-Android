package com.path_studio.arphatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

public class slider_adapter_choose_car extends PagerAdapter {
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

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
    }

}
