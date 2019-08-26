package com.path_studio.arphatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.viewpager.widget.PagerAdapter;

public class slider_adapter_choose_car extends PagerAdapter implements CompoundButton.OnCheckedChangeListener{
    Context context;
    LayoutInflater layoutInflater;

    private Switch use_driver;

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

        use_driver = (Switch) view.findViewById(R.id.include_driver);
        use_driver.setOnCheckedChangeListener(this);

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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        SharedPreferences mSettings = context.getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        if(isChecked){
            //switch is on
            editor.putBoolean("iclude_driver", true);
        }else{
            //switch is off
            editor.putBoolean("iclude_driver", false);
        }
        editor.apply();
    }
}
