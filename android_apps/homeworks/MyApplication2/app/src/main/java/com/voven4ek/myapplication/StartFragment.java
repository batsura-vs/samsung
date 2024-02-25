package com.voven4ek.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voven4ek.myapplication.databinding.FragmentStartBinding;
import com.voven4ek.myapplication.model.OrderViewModel;

public class StartFragment extends Fragment {
    private FragmentStartBinding binding;
    private OrderViewModel orderViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.orderOneCupcake.setOnClickListener(v -> {
            orderCupcake(1);
        });
        binding.orderSixCupcakes.setOnClickListener(v -> {
            orderCupcake(6);
        });
        binding.orderTwelveCupcakes.setOnClickListener(v -> {
            orderCupcake(12);
        });
    }

    private void orderCupcake(int number) {
        orderViewModel.setQuantity(number);
        Navigation.findNavController(requireView()).navigate(R.id.action_startFragment_to_flavorFragment3);
    }
}