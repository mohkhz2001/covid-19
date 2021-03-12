package com.mohammadKZ.CoronaVirus_COVID19_statistics.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mohammadKZ.CoronaVirus_COVID19_statistics.Adapter.NewsAdapter;
import com.example.covid_19.R;
import com.mohammadKZ.CoronaVirus_COVID19_statistics.model.News;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
// api query -->  https://newsapi.org/v2/top-headlines?language=en&q=CoronaVirus&apiKey=6df8db6156bd46eeab0aaa3821bf6206

    View view;
    RequestQueue requestQueue;
    RecyclerView recyclerView; //  list of news
    TextView connect;
    SpinKitView spin_kit;

    ArrayList<News> news;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();

        InitViews();
        ControllerViews();
        getNews();

        return view;
    }

    // make the component as new
    private void InitViews() {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        spin_kit = view.findViewById(R.id.spin_kit);
        connect = view.findViewById(R.id.connect);
    }

    private void ControllerViews() {

    }

    // get news from API
    private void getNews() {

//        String url = "https://newsapi.org/v2/top-headlines?language=en&q=CoronaVirus&apiKey=6df8db6156bd46eeab0aaa3821bf6206";
        String url = "https://api.currentsapi.services/v1/search?language=en&keywords=covid-19&apiKey=e9mD52nEOuZr2yYSKNyrqzVP-LwhqZnEOG4oWma3tuqk_7Ej";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("news");

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    news = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        News news1 = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), News.class);
                        if (news1.getTitle().length() <= 150)
                            news.add(news1);
                    }
                    System.out.println();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setAdapter();
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

//        StringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    // set the recyclerView item
    private void setAdapter() {
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(newsAdapter);

        connect.setVisibility(View.GONE);
        spin_kit.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
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
                            getNews();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }

    }

}