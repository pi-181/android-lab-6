package com.demkom58.androidlab6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListener btnClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myLogs", String.valueOf(v.getId()));
                Click(v.getId());
            }
        };
        findViewById(R.id.bMusic).setOnClickListener(btnClick);
        findViewById(R.id.bCamera).setOnClickListener(btnClick);
        findViewById(R.id.bGallery).setOnClickListener(btnClick);
    }

    protected void Click(int view) {
        Intent intent = null;
        Log.d("myLogs", view + "");

        switch (view) {
            case R.id.bMusic:
                intent = new Intent(this, MediaActivity.class);
                break;
            case R.id.bGallery:
                intent = new Intent(this, GalleryActivity.class);
                break;
            case R.id.bCamera:
                intent = new Intent(this, CameraActivity.class);
                break;
            default:
                break;
        }

        if (intent != null) {
            Log.d("myLogs", "Интент = " + intent);
            startActivity(intent);
        }
    }
}
