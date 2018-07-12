package com.sp.mgm.trustmebank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        TextView txtIncome = view.findViewById(R.id.txt_income);
        txtIncome.setText(String.valueOf("€ 23.50"));

        return view;
    }
}
