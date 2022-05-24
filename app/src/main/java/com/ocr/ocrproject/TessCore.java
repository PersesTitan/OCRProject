package com.ocr.ocrproject;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.ocr.ocrproject.databinding.ActivityMainBinding;

public class TessCore implements LanguageList {
    ActivityMainBinding binding;
    Context context;

    public TessCore(Context context, ActivityMainBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public String detectText(Bitmap bitmap) {
        int position = binding.languageSpinner.getSelectedItemPosition();
        TessDataManager tessDataManager = new TessDataManager(context, binding);
        tessDataManager.initTessTrainedData();
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = tessDataManager.getTesseractFolder();
        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, getLanguage(position));
        //화이트 리스트
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, filterLang(position));
        //블렉 리스트
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, blackLang(position));
        tessBaseAPI.setImage(bitmap);
        String inspection = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        return inspection;
    }

    private String blackLang(int position) {
        if (position == 2) return etcLang2;
        else return etcLang1;
    }

    private String filterLang(int position) {
        switch (position) {
            case 1: return engLang;
            case 2: return jpnLang;
            default: return korLang;
        }
    }

    private String getLanguage(int position) {
        switch (position) {
            case 1: return "eng";
            case 2: return "jpn";
            default: return "kor";
        }
    }
}
