package com.ocr.ocrproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ocr.ocrproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String dataPath = "";
    String imagePath = "";

    Bitmap image = null;
    String language = "";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSpinner();
        binding.textView.setText(null);
        binding.camara.setOnClickListener(v -> {

        });

    }

    private void setSpinner() {
        List<String> list = new ArrayList<>();
        list.add("한국어");
        list.add("영어");
        list.add("일본어");
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout, list);
        binding.languageSpinner.setAdapter(adapter);
    }
}