package com.voven4ek.calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView tvResult = view.findViewById(R.id.tv_result);
        TextView tvInterest = view.findViewById(R.id.tv_interest);
        Button back = view.findViewById(R.id.back);

        back.setOnClickListener(v -> {
            CalculatorFragment fragment = new CalculatorFragment();

            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        });

        Bundle bundle = getArguments();
        assert bundle != null;
        double result = bundle.getDouble("result");

        double interest = result - bundle.getDouble("amount");

        tvResult.append("\n" + String.format("%.2f", result));
        tvInterest.append("\n" + String.format("%.2f", interest));

        return view;
    }
}