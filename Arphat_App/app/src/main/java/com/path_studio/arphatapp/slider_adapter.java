package com.path_studio.arphatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class slider_adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public slider_adapter(Context context){
        this.context = context;
    }

    //Arrays
    public int [] bg_image = {
            R.drawable.test_02,
            R.drawable.test_03,
            R.drawable.test_04
    };

    public String [] slide_headings = {
            "User",
            "Driver",
            "Ready"
    };

    public String [] slide_descs = {
            "Selalu hadir menjangkau seluruh pelosok negeri",
            "Melayani dengan sepenuh hati adalah tugas kami",
            "Selalu hadir menjangkau seluruh pelosok negeri"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.template_slider, container, false);

        ImageView slideImageView    = (ImageView) view.findViewById(R.id.img_slider);
        TextView slideHeading       = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription   = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(bg_image[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }

}
