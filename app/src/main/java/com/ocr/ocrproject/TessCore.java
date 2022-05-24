package com.ocr.ocrproject;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.ocr.ocrproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class TessCore implements LanguageList {
    ActivityMainBinding binding;
    Context context;

    public TessCore(Context context, ActivityMainBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public List<String> detectText(Bitmap bitmap) {
        List<String> list = new ArrayList<>();
        TessDataManager tessDataManager = new TessDataManager(context, binding);
        tessDataManager.initTessTrainedData();
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = tessDataManager.getTesseractFolder();
        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, getLanguage());



        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "");
        return list;
    }

    private String filterLang() {
        int position = binding.languageSpinner.getSelectedItemPosition();
        switch (position) {
            case 1: return engLang;
            case 2: return jpnLang;
            default: return korLang;
        }
    }

    private String getLanguage() {
        int position = binding.languageSpinner.getSelectedItemPosition();
        switch (position) {
            case 1: return "eng";
            case 2: return "jpn";
            default: return "kor";
        }
    }
}
