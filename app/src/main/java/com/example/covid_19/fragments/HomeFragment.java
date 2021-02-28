package com.example.covid_19.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.HomeActivity;
import com.example.covid_19.R;
import com.example.covid_19.model.Country;
import com.example.covid_19.splashScreenActivity;
import com.github.premnirmal.textcounter.CounterView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    List<Country> countries = new ArrayList<>();// list of the info country
    View view;
    TextView TotalConfirmed, TotalDeaths, TotalRecovered, connect;
    TextView txt_24_hours, txt_Total_country;
    RequestQueue requestQueue;
    CounterView NewConfirmed, NewDeaths;
    RelativeLayout relative_main, relative_top;
    CardView death_cardview, infected_cardView, Total_death_card, total_recovered_card, total_infected_card;
    SpinKitView spin_kit;


    static final String url_api_2 = "https://coronavirus-19-api.herokuapp.com/countries"; // API url

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue = Volley.newRequestQueue(getContext());

        initViews();
        RequestAPI();

        return view;
    }

    // make the component as new
    private void initViews() {
        txt_24_hours = view.findViewById(R.id.txt_24_hours);
        txt_Total_country = view.findViewById(R.id.txt_Total_country);
        death_cardview = view.findViewById(R.id.death_cardview);
        infected_cardView = view.findViewById(R.id.infected_cardView);
        Total_death_card = view.findViewById(R.id.Total_death_card);
        total_recovered_card = view.findViewById(R.id.total_recovered_card);
        total_infected_card = view.findViewById(R.id.total_infected_card);
        NewConfirmed = view.findViewById(R.id.NewConfirmed);
        TotalConfirmed = view.findViewById(R.id.TotalConfirmed);
        NewDeaths = view.findViewById(R.id.NewDeaths);
        TotalDeaths = view.findViewById(R.id.TotalDeaths);
        TotalRecovered = view.findViewById(R.id.TotalRecovered);
        relative_top = view.findViewById(R.id.relative_top);
        spin_kit = view.findViewById(R.id.spin_kit);
        relative_main = view.findViewById(R.id.relative_main);
        connect = view.findViewById(R.id.connect);
    }

    // api request and get the info
    private void RequestAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("ERROR", "aa " + jsonArray.length());
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Country country = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), Country.class);
                        countries.add(country);
                    }

                    setInfo();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    Log.d("Error", "connection failed" + " ");
                    Toast.makeText(getContext(), "check the internet connection...", Toast.LENGTH_SHORT).show();
                    alertDialog();// alert for connection
                }catch (Exception eroor){
                    eroor.getMessage();
                }
            }
        });

        requestQueue.add(stringRequest);

    }

    //set info in component
    private void setInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");// set the decimal format

        TotalConfirmed.setText(decimalFormat.format(Integer.valueOf(countries.get(0).getCases())));
        TotalDeaths.setText(decimalFormat.format(Integer.valueOf(countries.get(0).getDeaths())));
        TotalRecovered.setText(decimalFormat.format(Integer.valueOf(countries.get(0).getRecovered())));
        // set counter-up effect
        NewConfirmed.setEndValue(Integer.parseInt(countries.get(0).getTodayCases()));
        NewConfirmed.start();
        NewDeaths.setEndValue(Integer.parseInt(countries.get(0).getTodayDeaths()));
        NewDeaths.start();

        // make the spinKit gone and other visible
        spin_kit.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        total_infected_card.setVisibility(View.VISIBLE);
        txt_24_hours.setVisibility(View.VISIBLE);
        txt_Total_country.setVisibility(View.VISIBLE);
        total_recovered_card.setVisibility(View.VISIBLE);
        Total_death_card.setVisibility(View.VISIBLE);
        infected_cardView.setVisibility(View.VISIBLE);
        death_cardview.setVisibility(View.VISIBLE);
        relative_top.setVisibility(View.VISIBLE);
        relative_main.setVisibility(View.VISIBLE);

    }

    // when error in connection
    private void alertDialog() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "reconnect",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            RequestAPI();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }

    }

}