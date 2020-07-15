package com.example.slat_2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;

public class SelectActivity extends Activity {

    private Spinner spinnergrade, spinnerclass;
    private Button selectBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        spinnergrade = (Spinner)findViewById(R.id.spinnergrade);
        spinnerclass = (Spinner)findViewById(R.id.spinnerclass);
        selectBtn = (Button)findViewById(R.id.selectBtn);
        
    }
}
