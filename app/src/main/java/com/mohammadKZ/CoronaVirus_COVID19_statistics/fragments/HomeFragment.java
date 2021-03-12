package com.mohammadKZ.oronavirus_COVID19_statistics.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mohammadKZ.oronavirus_COVID19_statistics.model.Country;
import com.github.premnirmal.textcounter.CounterView;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class HomeFragment extends Fragment {

    Country country;
    View view;
    TextView TotalConfirmed, TotalDeaths, TotalRecovered, connect;
    RequestQueue requestQueue;
    CounterView NewConfirmed, NewDeaths, NewRecovered;
    SpinKitView spin_kit;
    ConstraintLayout constraintLayout;


    static final String url_api_2 = "https://disease.sh/v3/covid-19/all"; // API url

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
        NewConfirmed = view.findViewById(R.id.NewConfirmed);
        TotalConfirmed = view.findViewById(R.id.TotalConfirmed);
        NewDeaths = view.findViewById(R.id.NewDeaths);
        TotalDeaths = view.findViewById(R.id.TotalDeaths);
        NewRecovered = view.findViewById(R.id.NewRecovered);
        TotalRecovered = view.findViewById(R.id.TotalRecovered);
        spin_kit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);
        constraintLayout = view.findViewById(R.id.constraintLayout);
    }

    // api request and get the info
    private void RequestAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("ERROR", "aa " + jsonObject.length());


                    country = new Country("World", jsonObject.getString("todayCases"), jsonObject.getString("cases"), jsonObject.getString("todayDeaths"),
                            jsonObject.getString("deaths"), jsonObject.getString("recovered"), jsonObject.getString("active"), jsonObject.getString("todayRecovered") , null);

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
                } catch (Exception eroor) {
                    eroor.getMessage();
                }
            }
        });

        requestQueue.add(stringRequest);

    }

    //set info in component
    private void setInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");// set the decimal format

        TotalConfirmed.setText(decimalFormat.format(Integer.valueOf(country.getCases())));
        TotalDeaths.setText(decimalFormat.format(Integer.valueOf(country.getDeaths())));
        TotalRecovered.setText(decimalFormat.format(Integer.valueOf(country.getRecovered())));
        TotalRecovered.setText(decimalFormat.format(Integer.valueOf(country.getRecovered())));


        // set counter-up effect
        NewConfirmed.setEndValue(Integer.parseInt(country.getTodayCases()));
        NewConfirmed.start();
        NewDeaths.setEndValue(Integer.parseInt(country.getTodayDeaths()));
        NewDeaths.start();
        NewRecovered.setEndValue(Integer.valueOf(country.getTodayRecovered()));
        NewRecovered.start();

        // make the spinKit gone and other visible
        spin_kit.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);
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