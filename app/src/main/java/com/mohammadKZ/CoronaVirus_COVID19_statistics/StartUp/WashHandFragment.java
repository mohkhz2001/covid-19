package com.mohammadKZ.CoronaVirus_COVID19_statistics.StartUp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid_19.R;
import com.mohammadKZ.CoronaVirus_COVID19_statistics.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class WashHandFragment extends Fragment {

    View view;
    Button finish_btn;
    BottomNavigationView navigation;

    public WashHandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wash_hand, container, false);



        finish_btn = view.findViewById(R.id.finish_btn);
        navigation = getActivity().findViewById(R.id.navigation);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                fragmentTransaction.commit();
                navigation.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}