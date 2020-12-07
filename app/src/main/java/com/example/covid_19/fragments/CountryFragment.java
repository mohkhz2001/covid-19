package com.example.covid_19.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.covid_19.R;
import com.example.covid_19.model.Country;
import com.github.premnirmal.textcounter.CounterView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class CountryFragment extends Fragment {

    List<Country> countries = new ArrayList<>();// list of the info country
    View view;
    String[] countriesName;// the country name for spinner
    RequestQueue requestQueue;
    private Spinner spinner;
    TextView txt_patient_all, txt_death_all, txt_recovered_all, connect, txt_search, txt_24_hours, txt_Total_country;
    CardView total_infected_card, total_recovered_card, Total_death_card, death_cardview, infected_cardView;
    CounterView txt_patient_number, txt_death_number;
    RelativeLayout relative_top, relative_main, spinner_layout;
    SpinKitView spin_kit;

    private String url_api = "https://coronavirus-19-api.herokuapp.com/countries";// API url

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        InitViews();
        ApiRequest();

        ViewsController();


        return view;
    }

    // make the component as new
    private void InitViews() {
        death_cardview = view.findViewById(R.id.death_cardview);
        infected_cardView = view.findViewById(R.id.infected_cardView);
        spinner_layout = view.findViewById(R.id.spinner_layout);
        txt_search = view.findViewById(R.id.txt_search);
        txt_24_hours = view.findViewById(R.id.txt_24_hours);
        txt_Total_country = view.findViewById(R.id.txt_Total_country);
        total_infected_card = view.findViewById(R.id.total_infected_card);
        total_recovered_card = view.findViewById(R.id.total_recovered_card);
        Total_death_card = view.findViewById(R.id.Total_death_card);
        relative_top = view.findViewById(R.id.relative_top);
        relative_main = view.findViewById(R.id.relative_main);
        spinner = view.findViewById(R.id.spinner);
        txt_patient_number = view.findViewById(R.id.txt_patient_number);
        txt_death_number = view.findViewById(R.id.txt_death_number);
        txt_patient_all = view.findViewById(R.id.txt_patient_all);
        txt_death_all = view.findViewById(R.id.txt_death_all);
        txt_recovered_all = view.findViewById(R.id.txt_recovered_all);
        spin_kit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);

    }

    // api request and get the info
    private void ApiRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    Log.e("lentgh", "aa  " + jsonArray.length());

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Country country = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), Country.class);
                        countries.add(country);
                    }


                    spin_kit.setVisibility(View.GONE);
                    connect.setVisibility(View.GONE);
                    txt_search.setVisibility(View.VISIBLE);
                    txt_24_hours.setVisibility(View.VISIBLE);
                    txt_Total_country.setVisibility(View.VISIBLE);
                    total_infected_card.setVisibility(View.VISIBLE);
                    total_recovered_card.setVisibility(View.VISIBLE);
                    Total_death_card.setVisibility(View.VISIBLE);
                    relative_top.setVisibility(View.VISIBLE);
                    relative_main.setVisibility(View.VISIBLE);
                    spinner_layout.setVisibility(View.VISIBLE);
                    death_cardview.setVisibility(View.VISIBLE);
                    infected_cardView.setVisibility(View.VISIBLE);
                    Spinner(countries);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "connection failed" + " ");
                Toast.makeText(getContext(), "check the internet connection...", Toast.LENGTH_SHORT).show();
                alertDialog();// alert for connection

            }
        });

        requestQueue.add(stringRequest);
    }

    // set the view controller ==> spinner controller
    private void ViewsController() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ShowInfo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // set the spinner value ==> country name
    private void Spinner(List<Country> countries1) {
        //Creating the ArrayAdapter instance having the country list
        countriesName = new String[countries.size() - 1];
        int counter = 1;
        for (int i = 0; i < countries1.size() - 1; i++) {
            countriesName[i] = countries1.get(counter).getCountry();
            counter++;
        }

        ArrayAdapter aa = null;
        try {
            aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, countriesName);
        } catch (Exception e) {
            e.getMessage();
        }
        if (aa != null)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    //set the component values
    private void ShowInfo(int i) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        i++;

        try {
            txt_patient_all.setText(decimalFormat.format(Integer.valueOf(countries.get(i).getCases())));
            txt_death_all.setText(decimalFormat.format(Integer.valueOf(countries.get(i).getDeaths())));
            txt_recovered_all.setText(decimalFormat.format(Integer.valueOf(countries.get(i).getRecovered())));

            //set counter-uo effect

            txt_patient_number.setEndValue(Integer.parseInt(countries.get(i).getTodayCases()));
            txt_patient_number.start();
            txt_death_number.setEndValue(Integer.parseInt(countries.get(i).getTodayDeaths()));
            txt_death_number.start();
        } catch (Exception e) {
            e.getMessage();
        }

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
                            ApiRequest();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    // json array example

    //{"country":"World","cases":41420389,"todayCases":395659,
    // "deaths":1134868,"todayDeaths":6020,"recovered":30829609,
    // "active":9455912,"critical":73951,"casesPerOneMillion":5314,
    // "deathsPerOneMillion":145,"totalTests":0,"testsPerOneMillion":0},
    //===========================================================================
    //{"country":"USA","cases":8574104,"todayCases":53154,"deaths":227167,
    // "todayDeaths":983,"recovered":5576742,"active":2770195,"critical":15665,
    // "casesPerOneMillion":25857,"deathsPerOneMillion":685,
    // "totalTests":128572375,"testsPerOneMillion":387739},
}