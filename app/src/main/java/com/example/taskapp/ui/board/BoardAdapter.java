package com.example.taskapp.ui.board;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taskapp.R;

public class BoardAdapter extends PagerAdapter {

    private String[] titles = new String[]{"ЛЕВ", "ТИГР", "ВОЛК"};
    private String[] desc = new String[]{
            "Лев он хыщник,",
            "Мой братан тигр ",
            "Волк в цирке не выступает"};
    private String[] desc2 = new String[]{
            "Лев он царь зверей ",
            "24/7 тигр",
            "Безумно можно быть первым!"};
    private Integer[] images = new Integer[]{R.drawable.lion, R.drawable.tiger, R.drawable.wolf};

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_board,container,false);
        TextView textTitle = view.findViewById(R.id.textTitle);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textDesc = view.findViewById(R.id.textDesc);
        TextView textDescTwo = view.findViewById(R.id.textDescTwo);
        Button buttonStart = view.findViewById(R.id.btnGetStart);
        imageView.setImageResource(images[position]);
        textDesc.setText(desc[position]);
        textDescTwo.setText(desc2[position]);
        textTitle.setText(titles[position]);
        container.addView(view);
        if (position == 2){
            buttonStart.setVisibility(View.VISIBLE);
        }else {
            buttonStart.setVisibility(View.GONE);
        }
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}