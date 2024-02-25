package com.voven4ek.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {
    public static final double PRICE_ONE = 100.0;
    private MutableLiveData<Integer> _quantity = new MutableLiveData<>(0);
    private MutableLiveData<Double> _price = new MutableLiveData<>(0.0);
    public LiveData<Double> price = _price;
    private MutableLiveData<String> _flavor = new MutableLiveData<>("");
    public LiveData<String> flavor = _flavor;

    public void setFlavor(String flavor) {
        _flavor.setValue(flavor);
    }

    public void setQuantity(int quantity) {
        _quantity.setValue(quantity);
        calcprice();
    }

    private void calcprice() {
        _price.setValue(_quantity.getValue() * PRICE_ONE);
    }
}
