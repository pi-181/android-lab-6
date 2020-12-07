package com.demkom58.androidlab6;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MediaActivity extends AppCompatActivity
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    CheckBox chbLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        chbLoop = findViewById(R.id.chLoop);
        surfaceView = findViewById(R.id.surfaceMediaView);
        chbLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mediaPlayer != null)
                    mediaPlayer.setLooping(isChecked);
            }
        });

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myLogs", String.valueOf(v.getId()));
                onClick(v);
            }
        };
        findViewById(R.id.bPause).setOnClickListener(btnClick);
        findViewById(R.id.bResume).setOnClickListener(btnClick);
        findViewById(R.id.bStop).setOnClickListener(btnClick);
        findViewById(R.id.bPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickStart(surfaceView); }
        });
    }

    public void onClickStart(View view) {
        releaseMP();
        String dataSource =((EditText)findViewById(R.id.fieldMediaPath)).getText().toString();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(dataSource);
            mediaPlayer.setDisplay(((SurfaceView) findViewById(R.id.surfaceMediaView)).getHolder());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            showMessage("Ошибка воспроизведения");
        }

        if (mediaPlayer == null)
            return;

        mediaPlayer.setLooping(chbLoop.isChecked());
        mediaPlayer.setOnCompletionListener(this);
    }

    private void showMessage(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void releaseMP() {
        if (mediaPlayer == null)
            return;

            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void onClick(View view) {
        if (mediaPlayer == null)
            return;

        switch (view.getId()) {
            case R.id.bPause:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case R.id.bResume:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;
            case R.id.bStop:
                mediaPlayer.stop();
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

}
