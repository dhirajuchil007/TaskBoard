package com.velocityappsdj.taskboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    String [] slide_descriptions;


    public SliderAdapter(Context context,String[] slideDesc) {
        this.context = context;
        slide_descriptions=slideDesc;
    }

    public  int[] slide_images={
            R.drawable.add,R.drawable.swipe,R.drawable.card,R.drawable.arch
    };



    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView=view.findViewById(R.id.slideImage);
        TextView slideDesc=view.findViewById(R.id.slide_tv);

        slideImageView.setImageResource(slide_images[position]);
        slideDesc.setText(slide_descriptions[position]);

        container.addView(view);
        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
