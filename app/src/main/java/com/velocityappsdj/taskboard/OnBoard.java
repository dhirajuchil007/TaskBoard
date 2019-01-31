
package com.velocityappsdj.taskboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoard extends AppCompatActivity {
    ViewPager slideViewPager;
    LinearLayout linearLayout;

    SliderAdapter sliderAdapter;
    TextView []dots;

    Button prevButton;
    Button nextButton;
    int mCurrentPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

         String[] slide_descriptions={getString(R.string.addTaskIns),getString(R.string.swipeIns),getString(R.string.cardIns),
                getString(R.string.archins) };

        slideViewPager=findViewById(R.id.viewpager);
        linearLayout=findViewById(R.id.page_indicator);
        prevButton=findViewById(R.id.button_back);
        nextButton=findViewById(R.id.button_next);

        sliderAdapter=new SliderAdapter(this,slide_descriptions);
        slideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(viewListener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage==dots.length-1)
                    onBackPressed();
                slideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentPage==0)
                    onBackPressed();
                slideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });




    }
    public void addDotsIndicator(int position){
        dots=new TextView[sliderAdapter.getCount()];
        linearLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.transParentWhite));


            linearLayout.addView(dots[i]);

        }

        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
          //  dots[position].setTextSize(37);
        }

    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage=position;

            if(position==0)
            {
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                //prevButton.setVisibility(View.INVISIBLE);
                prevButton.setText(getString(R.string.skip));
            }
            else if(position==dots.length-1)
            {
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);

                nextButton.setText(getString(R.string.finish));
                prevButton.setText(getString(R.string.back));


            }

            else {
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);
                prevButton.setText(getString(R.string.back));
                nextButton.setText(getString(R.string.next));

            }




        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
