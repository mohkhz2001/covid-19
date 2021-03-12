package com.mohammadKZ.CoronaVirus_COVID19_statistics.StartUp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid_19.R;

import static android.content.Context.MODE_PRIVATE;

public class WearMaskFragment extends Fragment {
    View view;

    Button next_btn;

    public WearMaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wear_mask, container, false);
        sharedPreferences();
        next_btn = view.findViewById(R.id.next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SeparateFragment seperateFragment = new SeparateFragment();
                fragmentTransaction.replace(R.id.frameLayout, seperateFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    // set sharedPreferences (start-up) ==> true
    private void sharedPreferences() {

        // Storing data into SharedPreferences
        SharedPreferences sh = this.getActivity().getSharedPreferences("start-up", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sh.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("start-up", "true");

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();

    }


}