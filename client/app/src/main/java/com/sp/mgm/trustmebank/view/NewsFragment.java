package com.sp.mgm.trustmebank.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sp.mgm.trustmebank.R;
import com.sp.mgm.trustmebank.adapter.NewsAdapter;
import com.sp.mgm.trustmebank.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.sp.mgm.trustmebank.BuildConfig;

public class NewsFragment extends Fragment {

    private View view;

    private RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        requestQueue = Volley.newRequestQueue(view.getContext());  // This setups up a new request queue which we will need to make HTTP requests.
        getListNews();

        return view;
    }

    private void getListNews() {


        String url = BuildConfig.SERVER_URL + "/news";

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        List<News> list = new ArrayList<>();

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                News news = new News();
                                news.setId(object.getInt("id"));
                                news.setTitle(object.getString("title"));
                                news.setBody(object.getString("body"));
                                news.setDate(object.getString("date"));
                                list.add(news);
                            }

                            RecyclerView recyclerView = view.findViewById(R.id.rv_news);
                            recyclerView.setHasFixedSize(true);
                            // use a linear layout manager
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            // define an adapter
                            RecyclerView.Adapter mAdapter = new NewsAdapter(list);
                            recyclerView.setAdapter(mAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

}
