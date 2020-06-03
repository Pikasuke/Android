package com.example.vibrationeffect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button viberbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viberbtn = findViewById(R.id.vibrate_btn);

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        final long[] pattern = {2000, 1000}; //sleep for 2sec vibre for 1 sec

        viberbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viberbtn.getText().toString().equals("Start")){
                    vibrator.vibrate(pattern, 0); // 0 = repat forever, -1 no repeat
                    Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                    viberbtn.setText("STOP");
                } else {
                    vibrator.cancel(); // cancel vibration
                    viberbtn.setText("Start");
                    Toast.makeText(MainActivity.this, "stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
