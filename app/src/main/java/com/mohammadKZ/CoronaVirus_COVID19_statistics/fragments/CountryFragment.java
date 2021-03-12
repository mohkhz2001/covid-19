package com.mohammadKZ.CoronaVirus_COVID19_statistics.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;
import com.mohammadKZ.CoronaVirus_COVID19_statistics.model.Country;
import com.github.premnirmal.textcounter.CounterView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

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
    TextView txt_patient_all, txt_death_all, txt_recovered_all, connect , countryName;
    CounterView txt_patient_number, txt_death_number , txt_Recovered_number;
    SpinKitView spin_kit;
    ConstraintLayout constraintLayout;
    ImageView flag;

    private String url_api = "https://disease.sh/v3/covid-19/countries";// API url

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
        spinner = view.findViewById(R.id.spinner);
        txt_patient_number = view.findViewById(R.id.txt_patient_number);
        txt_death_number = view.findViewById(R.id.txt_death_number);
        txt_patient_all = view.findViewById(R.id.txt_patient_all);
        txt_death_all = view.findViewById(R.id.txt_death_all);
        txt_recovered_all = view.findViewById(R.id.txt_recovered_all);
        spin_kit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        txt_Recovered_number = view.findViewById(R.id.txt_Recovered_number);
        flag = view.findViewById(R.id.flag);
        countryName = view.findViewById(R.id.countryName);

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
                        country.setFlag(jsonArray.getJSONObject(i).getJSONObject("countryInfo").getString("flag"));
                        countries.add(country);
                    }

                    System.out.println();
                    spin_kit.setVisibility(View.GONE);
                    connect.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);
                    Spinner(countries);


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
                } catch (Exception e) {
                    e.getMessage();
                }

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
            countryName.setText(countries.get(i).getCountry());
            Picasso.get().load(countries.get(i).getFlag()).into(flag);

            //set counter-uo effect
            if (countries.get(i).getTodayCases().equals("0")){
                txt_patient_number.setText("N/A");
            }else
            {
                txt_patient_number.setEndValue(Integer.parseInt(countries.get(i).getTodayCases()));
                txt_patient_number.start();
            }

            if (countries.get(i).getTodayDeaths().equals("0")){
                txt_death_number.setText("N/A");
            }else {
                txt_death_number.setEndValue(Integer.parseInt(countries.get(i).getTodayDeaths()));
                txt_death_number.start();
            }

            if (countries.get(i).getTodayRecovered().equals("0")){
                txt_Recovered_number.setText("N/A");
            }else {
                txt_Recovered_number.setEndValue(Integer.parseInt(countries.get(i).getTodayDeaths()));
                txt_Recovered_number.start();
            }

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

}