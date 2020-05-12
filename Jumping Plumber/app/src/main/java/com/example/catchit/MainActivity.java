package com.example.catchit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Game Layout
    private FrameLayout gameFrame;
    private LinearLayout startLayout;
    //Elements
    private ImageView mario, missile, piece, champi, ground;
    private TextView scoreLabel, timeLabel, titleLabel, startScoreLabel, highScoreLabel;
    //Size
    private int frameHeight, frmaeWidth, marioSize;
    //Position
    private float marioY, missileX, missileY, pieceX, pieceY, champiX, champiY;
    //Speed
    private int marioSpeed;
    //Score

    //Timer
    private Timer timer;
    private Handler handler = new Handler();
    //Time Count

    //Sound

    //Status
    private boolean start_flg = false;
    private boolean action_flg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        mario = findViewById(R.id.mario);
        missile = findViewById(R.id.missile);
        piece = findViewById(R.id.piece);
        champi = findViewById(R.id.champi);
        ground = findViewById(R.id.ground);
        scoreLabel = findViewById(R.id.scoreLabel);
        timeLabel = findViewById(R.id.timeLabel);
        titleLabel = findViewById(R.id.timeLabel);
        startScoreLabel = findViewById(R.id.startScoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        //Set Visibility
        mario.setVisibility(View.INVISIBLE);
        missile.setVisibility(View.INVISIBLE);
        piece.setVisibility(View.INVISIBLE);
        champi.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        startScoreLabel.setVisibility(View.GONE);
    }

    public void changePos(){

        // Piece
        // Deplacement de la piece vers mario
        pieceX -=12;
        // calcul de la borne haute et basse n Y et droite et gauche en X
        float pieceRight = pieceX + piece.getWidth();
        float pieceDown = pieceY + piece.getHeight();
        // Gestion de la collision par box mario et la piece etant des box il faut gérer
        // la collision en X avec la borne Y inferieure et Y suppérieure
        // La gestion avec un centre de gravité representant la piece
        // dans les bornes X et Y est moins précise mais plus facile
        if ((pieceDown < marioY+marioSize && marioY <pieceDown && pieceX < 0 && marioSize>pieceX)
                ||(pieceY < marioY+marioSize && marioY <pieceY && pieceX < 0 && marioSize>pieceX)){
            pieceX = -100.0f;
        }

        if (pieceX <0) {
            pieceX = (float)Math.floor(frmaeWidth*1.1);
            pieceY = (float)Math.floor(Math.random() * (frameHeight - piece.getHeight()));
        }
        piece.setX(pieceX);
        piece.setY(pieceY);


        //mario
        marioY += marioSpeed;
        if (marioY<0) marioY=0;
        if (marioY+marioSize >=frameHeight) marioY=frameHeight-marioSize;
        mario.setY(marioY);

        marioSpeed+=2;
        if (action_flg)action_flg=false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flg) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg=true;
                marioSpeed = -20;
            }
        }
        return super.onTouchEvent(event);
    }

    public void startGame(View view){
        start_flg= true;

        //get frameheight and frame width
        if (frameHeight == 0){
            frameHeight = gameFrame.getHeight()-ground.getHeight();
            frmaeWidth = gameFrame.getWidth();
            marioSize = mario.getHeight();
        }

        //Set Initial position
        marioY = frameHeight - marioSize;
        pieceX = -100.0f;
        champiX = -100.0f;
        missileX = -100.0f;
        mario.setY(marioY);
        piece.setX(pieceX);
        champi.setX(champiX);
        missile.setX(missileX);

        //Initialize time

        //Initialize Score

        //set Visibility
        startLayout.setVisibility(View.INVISIBLE);
        mario.setVisibility(View.VISIBLE);
        missile.setVisibility(View.VISIBLE);
        piece.setVisibility(View.VISIBLE);
        champi.setVisibility(View.VISIBLE);
        scoreLabel.setVisibility(View.VISIBLE);
        timeLabel.setVisibility(View.VISIBLE);

        //Start Timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flg){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        },0,20);
    }
}
