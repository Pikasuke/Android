package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);


        //Methode Thread run qui ne fonctionne pas
        /*Thread thread = new Thread();
        {
            @Override
            public void rub() {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){

                }
                finally {
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
