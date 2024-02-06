package com.voven4ek.calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CalculatorFragment extends Fragment {

    private EditText etAmount;
    private EditText etTerm;
    private EditText etRate;

    public CalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        etAmount = view.findViewById(R.id.et_amount);
        etTerm = view.findViewById(R.id.et_term);
        etRate = view.findViewById(R.id.et_rate);
        Button btnCalculate = view.findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString();
            String termStr = etTerm.getText().toString();
            String rateStr = etRate.getText().toString();

            if (amountStr.isEmpty() || termStr.isEmpty() || rateStr.isEmpty()) {
                Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            int term = Integer.parseInt(termStr);
            double rate = Double.parseDouble(rateStr);

            double result = amount * Math.pow((1 + rate / 100), term);

            Bundle bundle = new Bundle();
            bundle.putDouble("result", result);
            bundle.putDouble("amount", amount);

            ResultFragment fragment = new ResultFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        });

        return view;
    }
}