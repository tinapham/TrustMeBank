package com.sp.mgm.trustmebank.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.sp.mgm.trustmebank.R;
import com.sp.mgm.trustmebank.dao.AccountDAO;
import com.sp.mgm.trustmebank.model.Account;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private View view;

    private RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        requestQueue = Volley.newRequestQueue(view.getContext());  // This setups up a new request queue which we will need to make HTTP requests.
        getAccountInfo();

        return view;
    }

    private void getAccountInfo() {

        String url = "https://trustmebank.com/user";

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {

                            TextView txtUsername = view.findViewById(R.id.txt_username);
                            txtUsername.setText(response.getString("username"));

                            TextView txtIncome = view.findViewById(R.id.txt_income);
                            txtIncome.setText("€ " + response.getString("balance"));

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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                AccountDAO db = new AccountDAO(view.getContext());
                Account account = db.getAccount(LoginActivity.USERNAME);
                params.put("Authorization", "Bearer " + account.getToken());
                return params;
            }
        };

        requestQueue.add(arrReq);


    }
}

