package com.example.cardvalidator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button validateButton;
    private ImageView resultImage;
    private EditText cardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        validateButton = findViewById(R.id.button);
        validateButton.setOnClickListener(v -> {
            validateCard();
        });
        resultImage = findViewById(R.id.result_image);
        cardNumber = findViewById(R.id.card_number);
    }

    private void validateCard() {
        resultImage.setVisibility(View.VISIBLE);
        String number = cardNumber.getText().toString();
        int resultSum = 0;
        if (number.length() == 16) {
            for (int i = 0 ; i < number.length(); i++) {
                int digit = Integer.parseInt(number.charAt(i) + "");
                if (i % 2 == 0) {
                    digit *= 2;
                    if (digit > 9) {
                        digit -= 9;
                    }
                }
                resultSum += digit;
            }
        }
        Log.d("resultSum", String.valueOf(resultSum));
        if (resultSum % 10 == 0 && resultSum != 0) {
            resultImage.setImageResource(R.drawable.tick);
        } else {
            resultImage.setImageResource(R.drawable.cross);
        }
        resultImage.setVisibility(View.VISIBLE);
    }
}