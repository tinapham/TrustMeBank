package com.sp.mgm.trustmebank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sp.mgm.trustmebank.BuildConfig;
import com.sp.mgm.trustmebank.R;
import com.sp.mgm.trustmebank.adapter.NewsAdapter;
import com.sp.mgm.trustmebank.adapter.TransactionAdapter;
import com.sp.mgm.trustmebank.dao.AccountDAO;
import com.sp.mgm.trustmebank.model.Account;
import com.sp.mgm.trustmebank.model.News;
import com.sp.mgm.trustmebank.model.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {

    private View view;

    private RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        requestQueue = Volley.newRequestQueue(view.getContext());  // This setups up a new request queue which we will need to make HTTP requests.
        getListNews();

        return view;
    }

    private void getListNews() {

        String url = BuildConfig.SERVER_URL + "/user/transaction";

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        List<Transaction> list = new ArrayList<>();

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                Transaction transaction = new Transaction();
                                transaction.setId(object.getString("id"));
                                transaction.setAmount(object.getDouble("amount"));
                                transaction.setFromUser(object.getString("from_user"));
                                transaction.setToUser(object.getString("to_user"));
                                transaction.setCreatedAt(object.getString("createdAt"));
                                transaction.setSend(LoginActivity.USERNAME);
                                list.add(transaction);
                            }

                            RecyclerView recyclerView = view.findViewById(R.id.rv_transactions);
                            recyclerView.setHasFixedSize(true);
                            // use a linear layout manager
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            // define an adapter
                            RecyclerView.Adapter mAdapter = new TransactionAdapter(list);
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
                        //delete token in db
                        AccountDAO db = new AccountDAO(getContext());
                        db.deleteAccount(LoginActivity.USERNAME);

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        ){
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
