package com.bhavyathacker.calcandcurrencyconverter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyConverterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CurrencyConverterActivi";
    CurrencyConverterApi currencyConverterApi;

    private EditText editTextUpperSelection, editTextLowerSelection, editTextInput, editTextOutput;
    private ImageView imgUpperSelection, imgLowerSelection;
    private Button btnConvert;
    private String inputValue;
    private String firstSelectedValue;
    private String secondSelectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        initViews();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free.currconv.com/api/v7/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyConverterApi = retrofit.create(CurrencyConverterApi.class);
//        getCurrencyData();
    }

    private void initViews() {
        editTextUpperSelection = findViewById(R.id.editTextUpperSelection);
        editTextLowerSelection = findViewById(R.id.editTextLowerSelection);
        imgUpperSelection = findViewById(R.id.imageViewUpperSelection);
        imgLowerSelection = findViewById(R.id.imageViewLowerSelection);
        editTextInput = findViewById(R.id.editTextInput);
        editTextOutput = findViewById(R.id.editTextOutput);
        btnConvert = findViewById(R.id.buttonConvert);
        editTextUpperSelection.setOnClickListener(this);
        editTextLowerSelection.setOnClickListener(this);
        imgUpperSelection.setOnClickListener(this);
        imgLowerSelection.setOnClickListener(this);
        editTextInput.setOnClickListener(this);
        btnConvert.setOnClickListener(this);


    }

    private void getCurrencyData(String firstSelectedValue, String secondSelectedValue) {
        Map<String, String> map = new HashMap<>();
//        map.put("q", "USD_INR");
        map.put("q", firstSelectedValue + "_" + secondSelectedValue);
        map.put("compact", "ultra");
        map.put("apiKey", "9303f6235ce93fcc7574");
        Call<ResponseBody> call = currencyConverterApi.getConvertedData(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    Log.d(TAG, "onResponse123: "+response.body().string());
                    String resps = response.body().string();
                    Log.d(TAG, "onResponse:resps " + resps);
                    JSONObject jsonObject = new JSONObject(resps);
                    Log.d(TAG, "onResponseJson:USD_INR-->> " + jsonObject.optString(firstSelectedValue + "_" + secondSelectedValue));
                    String convertValue = jsonObject.optString(firstSelectedValue + "_" + secondSelectedValue);
                    Log.d(TAG, "onResponse: " + convertValue);
                    Log.d(TAG, "onResponse: final " + String.valueOf((Double.parseDouble(inputValue) * (Double.parseDouble(convertValue)))));
                    editTextOutput.setText(String.valueOf((Double.parseDouble(inputValue) * (Double.parseDouble(convertValue)))));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void createAlertDialogForUpperSelection() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        final String[] items_array = getResources().getStringArray(R.array.alert);
//        alertbox.setTitle("Pick one item")
        alertbox.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                //pos will give the selected item position
                firstSelectedValue = items_array[pos];
//                        Toast.makeText(CurrencyConverterActivity.this, firstSelectedValue, Toast.LENGTH_SHORT).show();
                editTextUpperSelection.setText(firstSelectedValue);
            }
        });
        alertbox.show();
    }

    public void createAlertDialogForLowerSelection() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        final String[] items_array = getResources().getStringArray(R.array.alert);
//        alertbox.setTitle("Pick one item")
        alertbox.setItems(R.array.alert, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                secondSelectedValue = items_array[pos];
//                        Toast.makeText(CurrencyConverterActivity.this, secondSelectedValue, Toast.LENGTH_SHORT).show();
                editTextLowerSelection.setText(secondSelectedValue);
                //pos will give the selected item position
            }
        });
        alertbox.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTextUpperSelection:
                createAlertDialogForUpperSelection();
                break;
            case R.id.editTextLowerSelection:
                createAlertDialogForLowerSelection();
                break;
            case R.id.imageViewUpperSelection:
                createAlertDialogForUpperSelection();
                break;
            case R.id.imageViewLowerSelection:
                createAlertDialogForLowerSelection();
                break;
            case R.id.editTextInput:
//                inputValue = editTextInput.getText().toString();
                break;
            case R.id.buttonConvert:
                getAllValueAndConvert();
                break;
        }
    }

    private void getAllValueAndConvert() {
        if (TextUtils.isEmpty(editTextUpperSelection.getText().toString())) {
            Toast.makeText(this, "Please select your currency type", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editTextLowerSelection.getText().toString())) {
            Toast.makeText(this, "Please select converted currency type", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editTextInput.getText().toString())) {
            Toast.makeText(this, "Please enter currency", Toast.LENGTH_SHORT).show();
        } else {
            if (firstSelectedValue.equals(secondSelectedValue)) {
                editTextOutput.setText(editTextInput.getText().toString());
            } else {
                inputValue = editTextInput.getText().toString();
                if (ConnectivityUtils.checkConnection(this)) {
                    getCurrencyData(firstSelectedValue, secondSelectedValue);
                } else {
                    Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}
