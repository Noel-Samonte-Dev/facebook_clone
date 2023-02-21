package com.example.facebook_clone;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {
    private ProgressBar pgsBar;
    private int i = 0;
    private TextView txtView;
    private Handler hdlr = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pgsBar = findViewById(R.id.pBar);
        txtView = findViewById(R.id.tView);

        i = pgsBar.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 1;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            pgsBar.setProgress(i);
                            txtView.setText(i+"/"+pgsBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (i == 100) {
                    Intent intent = new Intent(splash_screen.this, login_page.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();
    }
}
