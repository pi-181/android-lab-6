package com.demkom58.androidlab6;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends AppCompatActivity {
    private int currentImage = 0;
    private List<String> images;
    private ImageView imageView;
    private TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    public void onResume() {
        super.onResume();

        currentImage = 0;

        Log.d("myLogs", "onResume cI=" + currentImage);

        nameView = findViewById(R.id.imageName);
        images = new ArrayList<>();

        imageView = findViewById(R.id.imageView);
        File imagesDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            images = searchImage(imagesDirectory);
            updatePhoto(Uri.parse(images.get(currentImage)));
        } catch (Exception e) {
            nameView.setText("Ошибка: Папка '" + imagesDirectory.getPath() + "' не найдена");
            Log.d("myLogs", "Ошибка");
        }

        findViewById(R.id.bPrevImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onPrevious(imageView); }
        });
        findViewById(R.id.bNextImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onNext(imageView); }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        images.clear();
        Log.d("myLogs", "onPause cI=" + currentImage);
    }

    private List<String> searchImage(File dir) {
        List<String> imagesFound = new ArrayList<>();
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                continue;

            String fileExt = getFileExt(f.getAbsolutePath());
            if (fileExt.equals("png") || fileExt.equals("jpg") || fileExt.equals("jpeg")) {
                Log.d("myLogs", "Файл найден " + f.getAbsolutePath());
                imagesFound.add(f.getAbsolutePath());
            }
        }
        return imagesFound;
    }

    public static String getFileExt(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void updatePhoto(Uri uri) {
        try {
            nameView.setText((currentImage + 1) + "/" + images.size());
            imageView.setImageURI(uri);
        } catch (Exception e) {
            nameView.setText("Ошибка загрузки файла");
        }
    }

    public void onNext(View v) {
        if (currentImage + 1 < images.size() && images.size() > 0) {
            currentImage++;
            updatePhoto(Uri.parse(images.get(currentImage)));
        }
    }

    public void onPrevious(View v) {
        if (currentImage > 0 && images.size() > 0) {
            currentImage--;
            updatePhoto(Uri.parse(images.get(currentImage)));
        }
    }


}
