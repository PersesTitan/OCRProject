package com.ocr.ocrproject;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.ocr.ocrproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TessCore tessCore;
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

        tessCore = new TessCore(this, binding);

        setSpinner();
        binding.textView.setText(null);
        binding.camara.setOnClickListener(this::startCamera);

    }

    private void startCamera(View view) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            tessCore.detectText(bitmap);
            binding.imageView.setImageBitmap(bitmap);
        }
    }

    private void setSpinner() {
        final String shared = "filed";
        final String save = "SAVE";
        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String[] list = {"?????????", "??????", "?????????"};
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout, list);
        binding.languageSpinner.setAdapter(adapter);
        binding.languageSpinner.setSelection(sharedPreferences.getInt(save, 0));
        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt(save, i);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    /**
     * @return spinner ?????? ?????? ?????? ?????? ?????????
     */
    private String getLanguage() {
        int position = binding.languageSpinner.getSelectedItemPosition();
        switch (position) {
            case 1: return "eng";
            case 2: return "jpn";
            default: return "kor";
        }
    }

    private String extractText(Bitmap bitmap) {
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        tessBaseAPI.init(dataPath, getLanguage());
        tessBaseAPI.setImage(bitmap);
        String extractedText = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        return extractedText;
    }
}