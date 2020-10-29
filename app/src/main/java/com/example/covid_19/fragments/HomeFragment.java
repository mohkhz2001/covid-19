package com.example.covid_19.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;
import com.example.covid_19.model.Country;
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
    TextView NewConfirmed, TotalConfirmed, NewDeaths, TotalDeaths, TotalRecovered, connect;
    RequestQueue requestQueue;
    LinearLayout parent;
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
        NewConfirmed = view.findViewById(R.id.NewConfirmed);
        TotalConfirmed = view.findViewById(R.id.TotalConfirmed);
        NewDeaths = view.findViewById(R.id.NewDeaths);
        TotalDeaths = view.findViewById(R.id.TotalDeaths);
        TotalRecovered = view.findViewById(R.id.TotalRecovered);
        parent = view.findViewById(R.id.parent);
        spin_kit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);
    }

    // api request and get the info
    private void RequestAPI(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("ERROR" , "aa " + jsonArray.length() );
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
                Log.d("Error", "connection failed" + " ");
                Toast.makeText(getContext() ,"اتصال اینترنت را چک کنید" , Toast.LENGTH_SHORT).show();
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
        NewConfirmed.setText(decimalFormat.format(Integer.valueOf(countries.get(0).getTodayCases())));
        NewDeaths.setText(decimalFormat.format(Integer.valueOf(countries.get(0).getTodayDeaths())));

        // make the spinKit gone and other visible
        spin_kit.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);

    }


}