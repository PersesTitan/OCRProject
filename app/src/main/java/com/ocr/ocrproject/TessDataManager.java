package com.ocr.ocrproject;

import android.content.Context;
import android.widget.Toast;

import com.ocr.ocrproject.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Pattern;

public class TessDataManager {
    private final String tessDir ="tesseract";
    private final String subDir = "tessData";

    private String trainedDataPath;
    private String tesseractFolder;

    private boolean initiated;

    private String fileName = "";
    ActivityMainBinding binding;
    Context context;

    public TessDataManager(Context context, ActivityMainBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public String getTesseractFolder() {
        return tesseractFolder;
    }

    public String getTrainedDataPath() {
        return initiated ? trainedDataPath : null;
    }

    public void initTessTrainedData() {
        if (initiated) return;
        File appFolder = context.getFilesDir();
        File folder = new File(appFolder, tessDir);
        if (!folder.exists()) {
            if (!folder.mkdir()) toast();
        }
        tesseractFolder = folder.getAbsolutePath();

        File subfolder = new File(folder, subDir);
        if (!subfolder.exists()) {
            if(!subfolder.mkdir()) toast();
        }

        File file = new File(subfolder, fileName);
        trainedDataPath = file.getAbsolutePath();

        if (!file.exists()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] bytes = readRaw();
                if (bytes == null) return;
                fileOutputStream.write(bytes);
                initiated = true;
            } catch (Exception ignored) { }
        } else initiated = true;
    }

    private byte[] readRaw() {
        try (InputStream fileInput = context.getResources()
                .openRawResource(getLanguageFile())){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] by = new byte[1024];

            int bytesRead;
            while ((bytesRead=fileInput.read(by))!=-1) bos.write(by, 0, bytesRead);
            return bos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private int getLanguageFile() {
        int position = binding.languageSpinner.getSelectedItemPosition();
        switch (position) {
            case 1: return R.raw.eng;
            case 2: return R.raw.jpn;
            default: return R.raw.kor;
        }
    }

    private void toast() {
        Toast.makeText(context, "문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
    }
}
