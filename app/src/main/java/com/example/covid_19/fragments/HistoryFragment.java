package com.example.covid_19.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.Adapter.HistoryAdapter;
import com.example.covid_19.R;
import com.example.covid_19.model.Country;
import com.example.covid_19.model.History;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HistoryFragment extends Fragment {

    View view;

    Spinner spinnerDay;
    SearchableSpinner spinnerCountry;
    Button confirm;
    ArrayList<String> countries = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ArrayList<History> histories = new ArrayList<>();
    CardView cardView;
    SpinKitView spinKit;
    TextView connect;
    ConstraintLayout constrain;
    int day = 0;
    int position = 0;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        InitViews();
        ControllerViews();
        getCountry();
        setDaySpinner();

        return view;
    }

    private void InitViews() {
        spinnerDay = view.findViewById(R.id.spinner_days);
        spinnerCountry = view.findViewById(R.id.spinner_country);
        confirm = view.findViewById(R.id.confirm);
        recyclerView = view.findViewById(R.id.recyclerView);
        cardView = view.findViewById(R.id.cardView);
        spinKit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);
        constrain = view.findViewById(R.id.constrain);
    }

    private void ControllerViews() {

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHistory();
            }
        });

    }

    private void getCountry() {
        String url = "https://disease.sh/v3/covid-19/historical?lastdays=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

//                    int counter = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Country country = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), Country.class);
                        int number = countries.indexOf(country.getCountry());
                        if (number == -1) {
                            countries.add(country.getCountry());
                        }
                    }

                    System.out.println();
                    setCountrySpinner();

                } catch (JSONException e) {
                    try {

                        Log.d("Error", "connection failed" + " ");
                        Toast.makeText(getContext(), "check the internet connection...", Toast.LENGTH_SHORT).show();
                        alertDialog();// alert for connection
                    }catch (Exception eroor){
                        eroor.getMessage();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void getHistory() {
        String url = "https://disease.sh/v3/covid-19/historical/" + countries.get(position) + "?lastdays=" + day + 1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(response);

                    JSONObject timeline = jsonObject.getJSONObject("timeline");
                    JSONObject cases = timeline.getJSONObject("cases");
                    JSONObject deaths = timeline.getJSONObject("deaths");
                    JSONObject recovered = timeline.getJSONObject("recovered");


                    for (int i = 1; i <= day + 1; i++) {
                        History history = new History();
                        String date = CalculateDate(i);
                        history.setCases(cases.getString(date));
                        history.setDeaths(deaths.getString(date));
                        history.setRecovered(recovered.getString(date));

                        history.setDate(date);
                        histories.add(history);
                    }

                    System.out.println();
                    CalculateData(histories);
                    setAdapter();

                } catch (JSONException e) {
                    e.getMessage();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private String CalculateDate(int i) {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -i);
        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("M/d/yy");// m/d/y
        String inActiveDate = null;
        inActiveDate = format1.format(date);
        return inActiveDate;
    }

    private void setDaySpinner() {

        // day spinner
        //set day in array
        String[] day = new String[29];
        for (int i = 1; i <= 29; i++) {
            day[i - 1] = Integer.toString(i);
        }
        ArrayAdapter dayAdapter = null;
        try {
            dayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, day);
        } catch (Exception e) {
            e.getMessage();
        }
        spinnerDay.setAdapter(dayAdapter);


    }

    private void setCountrySpinner() {
        // country spinner

        ArrayAdapter<History> countryAdapter = null;
        try {
            countryAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, countries);
        } catch (Exception e) {
            e.getMessage();
        }
        if (countryAdapter != null)
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(countryAdapter);

        constrain.setVisibility(View.VISIBLE);
        spinKit.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
    }

    private void setAdapter() {
        HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), histories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(historyAdapter);

        cardView.setVisibility(View.GONE);
    }

    private void CalculateData(ArrayList<History> histories) {

        for (int i = 0; i < histories.size() - 1; i++) {

            histories.get(i).setNewCases(String.valueOf(Integer.valueOf(histories.get(i).getCases()) - Integer.valueOf(histories.get(i + 1).getCases())));
            histories.get(i).setNewDeath(String.valueOf(Integer.valueOf(histories.get(i).getDeaths()) - Integer.valueOf(histories.get(i + 1).getDeaths())));
            histories.get(i).setNewRecovered(String.valueOf(Integer.valueOf(histories.get(i).getRecovered()) - Integer.valueOf(histories.get(i + 1).getRecovered())));
        }
        histories.remove(histories.size() - 1);
        System.out.println();

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
                            getCountry();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }

    }

}