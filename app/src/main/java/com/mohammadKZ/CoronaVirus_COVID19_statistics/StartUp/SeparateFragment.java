package com.mohammadKZ.CoronaVirus_COVID19_statistics.StartUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid_19.R;


public class SeparateFragment extends Fragment {

    View view;
    Button next_btn;

    public SeparateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_separate, container, false);
        next_btn = view.findViewById(R.id.next_BTN);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                WashHandFragment washHandFragment = new WashHandFragment();
                fragmentTransaction.replace(R.id.frameLayout, washHandFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}