package com.borden.seru.drwangschristmastree;


import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import static android.os.SystemClock.sleep;


public class MainActivity extends AppCompatActivity {

    boolean lightsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageview1 = (ImageView) findViewById(R.id.Light1);
        ImageView imageview2 = (ImageView) findViewById(R.id.Light2);
        ImageView imageview3 = (ImageView) findViewById(R.id.Light3);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageview1.setColorFilter(filter);
        imageview2.setColorFilter(filter);
        imageview3.setColorFilter(filter);
    }

    public void caroling(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd("carol.mp3");
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        player.prepare();
        player.start();
        view.setVisibility(View.GONE);
    }

    public void flashLight(View view) {
        new Thread(new Runnable() {
            public void run() {
                while(true){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ImageView imageview1 = (ImageView) findViewById(R.id.Light1);
                            ImageView imageview2 = (ImageView) findViewById(R.id.Light2);
                            ImageView imageview3 = (ImageView) findViewById(R.id.Light3);
                            ColorMatrix matrix = new ColorMatrix();
                            if (lightsOn) {
                                lightsOn = false;
                                matrix.setSaturation(0);

                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                imageview1.setColorFilter(filter);
                                imageview2.setColorFilter(filter);
                                imageview3.setColorFilter(filter);
                            } else {
                                lightsOn = true;
                                matrix.setSaturation(200);
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                imageview1.setColorFilter(filter);
                                imageview2.setColorFilter(filter);
                                imageview3.setColorFilter(filter);
                            }
                        }
                    });
                    sleep(1000);
                }
            }
        }).start();
    }
}
