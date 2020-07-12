package com.example.postapp.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.postapp.R;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int [] slideImages = {
            R.drawable.easy_sales,
            R.drawable.clients,
            R.drawable.finger
    };

    public String [] slideHeading = {
            "מי אנחנו?",
            "מי אתה?",
            "מה אתה צריך לעשות?"
    };

    public String [] slideDescription = {
            "אנחנו חברת easy sales ואנחנו יכולים לספק לעסק שלך אפליקציה לפי דרישה שתענה על צרכיך בצורה הטובה ביותר. ",
            "את/ה הבחור/ה שרוצה לפתח את העסק שלו/ה בצורה הטובה ביותר",
            "כל מה שאתה צריך לעשות זה להסתכל על הפריטים שלנו וליצור קשר! \n" +
                    "E-mail: tsur@reshatot.co.il "
    };


    @Override
    public int getCount() {
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.test);
        TextView heading = view.findViewById(R.id.tv_heading);
        TextView description = view.findViewById(R.id.tv_lorem);

        imageView.setImageResource(slideImages[position]);
        heading.setText(slideHeading[position]);
        description.setText(slideDescription[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
