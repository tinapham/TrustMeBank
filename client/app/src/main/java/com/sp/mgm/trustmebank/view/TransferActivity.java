package com.sp.mgm.trustmebank.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sp.mgm.trustmebank.R;
import com.sp.mgm.trustmebank.dao.AccountDAO;
import com.sp.mgm.trustmebank.model.Account;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransferActivity extends AppCompatActivity {

    private RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    private EditText mAmountView;
    private EditText mReceiverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAmountView = findViewById(R.id.txt_money);
        mReceiverView = findViewById(R.id.txt_receiver);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.

    }

    public void onSaveClick(View view) throws Exception {
        if (mAmountView.getText().toString().equals("")) {
            mAmountView.setError("You need to add the amount");
            return;
        }
        if (mReceiverView.getText().toString().equals("")) {
            mReceiverView.setError("You need to add the receiver");
            return;
        }
        Double amount = Double.valueOf(mAmountView.getText().toString());
        String receiver = mReceiverView.getText().toString();
        Log.d("TRANSFER", receiver + " " + amount);

        String url = "https://trustmebank.com/user/transfer";
//        String url = "https://172.31.240.218/user/transfer";

        Map<String, Double> params = new HashMap<>();
        params.put("amount", amount);

        JSONObject parameters = new JSONObject(params);
        parameters.put("to_user", receiver);

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                      response
                        try {
                            if (response.get("success").toString().equals("true")) {
                                showAlertDialog();
                            } else {
                                mReceiverView.setError(response.getString("message"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                      error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                AccountDAO db = new AccountDAO(getApplicationContext());
                Account account = db.getAccount(LoginActivity.USERNAME);
                params.put("Authorization", "Bearer " + account.getToken());
                return params;
            }
        };

        requestQueue.add(arrReq);

    }

    public void onCancelClick(View view) {
        //back to previous activity
        this.onBackPressed();
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage("Transfer successful!!!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(TransferActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}