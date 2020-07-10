package com.example.postapp.ui.main.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.postapp.R;
import com.example.postapp.ui.main.model.SlideAdapter;


public class SliderFragment extends Fragment {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;

    private SlideAdapter sliderAdapter;
    private TextView[] dots;

    private Button nextBtn;
    private Button backBtn;

    private int currentPage;

    public SliderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        dotsLayout = view.findViewById(R.id.dots_layout);
        nextBtn = view.findViewById(R.id.next_btn);
        backBtn = view.findViewById(R.id.prev_btn);

        sliderAdapter = new SlideAdapter(getContext());

        viewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage + 1);
            }
        });

        backBtn.setOnClickListener(b -> viewPager.setCurrentItem(currentPage - 1));

        return view;
    }

    public void addDotsIndicator(int position){
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if (position == 0){
                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("הבא");
                backBtn.setText("");
            } else if (position == dots.length - 1){
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("סיים");
                backBtn.setText("חזור");

                if (nextBtn.getText().equals("סיים")){
                    nextBtn.setOnClickListener(view -> getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new MainFragment())
                            .commitNow());
                }

            }else {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("הבא");
                backBtn.setText("חזור");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}