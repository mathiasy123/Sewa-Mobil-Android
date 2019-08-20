package com.path_studio.arphatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class slider_adapter_promotion extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public slider_adapter_promotion(Context context){
        this.context = context;
    }

    //Arrays
    public int [] bg_image = {
            R.drawable.promo_01,
            R.drawable.promo_02,
            R.drawable.promo_03
    };

    @Override
    public int getCount() {
        return bg_image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.template_slider_promotion, container, false);

        ImageView slideImageView    = (ImageView) view.findViewById(R.id.promo_slider);

        slideImageView.setImageResource(bg_image[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }

}
