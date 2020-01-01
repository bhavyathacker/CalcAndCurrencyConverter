package com.bhavyathacker.calcandcurrencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    private Button btnCalculator, btnCurrencyConvert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        btnCalculator = findViewById(R.id.btnCalculator);
        btnCurrencyConvert = findViewById(R.id.btnCurrencyConvert);
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this,MainActivity.class));
            }
        });

        btnCurrencyConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this,CurrencyConverterActivity.class));

            }
        });
    }
}
