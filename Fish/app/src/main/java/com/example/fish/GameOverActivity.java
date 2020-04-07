package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startGame;
    private TextView tPoint;
    private  String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        startGame = (Button) findViewById(R.id.bPlayAgain);
        tPoint = (TextView) findViewById(R.id.tPoint);

        score = getIntent().getExtras().get("point").toString();
        tPoint.setText("Vous avez fait : "+score);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();

            }
        });


    }
}
