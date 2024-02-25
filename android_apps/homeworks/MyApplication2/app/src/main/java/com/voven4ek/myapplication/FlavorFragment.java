package com.voven4ek.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.voven4ek.myapplication.databinding.FragmentFlavorBinding;
import com.voven4ek.myapplication.model.OrderViewModel;

public class FlavorFragment extends Fragment {
    private FragmentFlavorBinding binding;
    private OrderViewModel orderViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFlavorBinding.inflate(inflater, container, false);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        binding.setViewModel(orderViewModel);
        return inflater.inflate(R.layout.fragment_flavor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nextButton.setOnClickListener(v -> {
            orderViewModel.setFlavor(binding.vanilla.getText().toString());
            Navigation.findNavController(requireView()).navigate(R.id.action_flavorFragment3_to_pickupFragment);
        });
    }
}