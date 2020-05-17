package com.example.catchit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
    private ImageView missile, piece, champi, ground;
    private TextView scoreLabel,upLabel, timeLabel, titleLabel, startScoreLabel, highScoreLabelTime, highScoreLabelEndu;
    private ImageView mario[] = new ImageView[2];
    private Button timeAttackMode, enduranceMode;
    private boolean isTimeAttack=false, isEndurance=false;
    private ImageView up1, up2, up3;
    //Size
    private int frameHeight, frmaeWidth, marioSize;
    //Position
    private float marioY, missileX, missileY, pieceX, pieceY, champiX, champiY;
    //Speed
    private int marioSpeed;
    private int SPEED_MARIO_JUMP, SPEED_MARION_DOWN, SPEED_PIECE, SPEED_CHAMPI, SPEED_MISSILE;
    //Score
    private int score, highScoreEndu, highScoreTime;
    private int lifeCounter;
    private SharedPreferences sharedPreferences;
    //Timer
    private Timer timer;
    private Handler handler = new Handler();
    //Time Count
    private final static long GAME_TIME = 15; //15 secondes
    private long startTime;
    //Sound
    private SoundPlayer soundPlayer ;

    //Status
    private boolean start_flg = false;
    private boolean action_flg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);

        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        mario[0] = findViewById(R.id.mario);
        mario[1] = findViewById(R.id.mario2);
        missile = findViewById(R.id.missile);
        piece = findViewById(R.id.piece);
        champi = findViewById(R.id.champi);
        ground = findViewById(R.id.ground);
        scoreLabel = findViewById(R.id.scoreLabel);
        upLabel = findViewById(R.id.upLabel);
        up1 = findViewById(R.id.up1);
        up2 = findViewById(R.id.up2);
        up3 = findViewById(R.id.up3);
        timeLabel = findViewById(R.id.timeLabel);
        titleLabel = findViewById(R.id.titleLabel);
        startScoreLabel = findViewById(R.id.startScoreLabel);
        highScoreLabelTime = findViewById(R.id.highScoreLabelTime);
        highScoreLabelEndu = findViewById(R.id.highScoreLabelEndurance);
        timeAttackMode = findViewById(R.id.btnTimeAttack);
        enduranceMode = findViewById(R.id.btnEndurance);

        //Set Visibility

        mario[0].setVisibility(View.INVISIBLE);
        mario[1].setVisibility(View.INVISIBLE);
        missile.setVisibility(View.INVISIBLE);
        piece.setVisibility(View.INVISIBLE);
        champi.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        upLabel.setVisibility(View.INVISIBLE);
        up1.setVisibility(View.INVISIBLE);
        up2.setVisibility(View.INVISIBLE);
        up3.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        startScoreLabel.setVisibility(View.GONE);

        //High Score
        sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScoreEndu = sharedPreferences.getInt("ENDURANCE HIGH SCORE", 0);
        highScoreTime = sharedPreferences.getInt("TIME HIGH SCORE", 0);
        highScoreLabelEndu.setText(getString(R.string.high_score, highScoreEndu));
        highScoreLabelTime.setText(getString(R.string.high_score, highScoreTime));

        SPEED_MARIO_JUMP = getResources().getInteger((R.integer.speed_mario_jump));
        SPEED_MARION_DOWN = getResources().getInteger((R.integer.speed_mario_down));
        SPEED_PIECE = getResources().getInteger((R.integer.speed_piece));
        SPEED_CHAMPI = getResources().getInteger((R.integer.speed_champi));
        SPEED_MISSILE = getResources().getInteger((R.integer.speed_missile));

        //Select Mode
        timeAttackMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTimeAttack = true;
                startGame();
            }
        });
        enduranceMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEndurance = true;
                startGame();
            }
        });
    }

    public void changePos(){
        // TIME
        long remainedTime = GAME_TIME- (System.currentTimeMillis()-startTime)/1000;
        timeLabel.setText(getString(R.string.time, remainedTime));

        //Game over Time Attack
        if (isTimeAttack) {
            if (remainedTime<0){
                gameOver();
                return;
            }
        }
        //Game over Endurance Mode
        if(isEndurance) {
            if (lifeCounter<0){
                gameOver();
                return;
            }
        }

        /////////// Play Sound  ///////////
       if(isTimeAttack) {
           if (remainedTime < 7 && !soundPlayer.isPlaying()) {
               soundPlayer.playHurryUp();
           }
       }

        /////////// Piece  ///////////
        // Deplacement de la piece vers mario
        pieceX -=SPEED_PIECE;
        // calcul de la borne haute et basse n Y et droite et gauche en X
        float pieceRight = pieceX + piece.getWidth();
        float pieceDown = pieceY + piece.getHeight();

        if (ifCollision(pieceX, pieceY, pieceDown)){
            pieceX = -100.0f;
            score+=10;
            soundPlayer.playCoinSound();
        }

        if (pieceX <0) {
            pieceX = (float)Math.floor(frmaeWidth*1.1);
            pieceY = (float)Math.floor(Math.random() * (frameHeight - piece.getHeight()));
        }
        piece.setX(pieceX);
        piece.setY(pieceY);

        /////////// Champi ///////////
        // Deplacement du champi vers mario
        champiX -=SPEED_CHAMPI;
        // calcul de la borne haute et basse n Y et droite et gauche en X
        float champiRight = champiX + champi.getWidth();
        float champiDown = champiY + champi.getHeight();

       if (ifCollision(champiX, champiY, champiDown)){
            champiX = -100.0f;
            score+=30;
           soundPlayer.playChampSound();
       }

        if (champiX <0) {
            champiX = frmaeWidth*4;
            champiY = (float)Math.floor(Math.random() * (frameHeight - champi.getHeight()));
        }
        champi.setX(champiX);
        champi.setY(champiY);

        /////////// Missile ///////////
        // Deplacement du missile vers mario
        missileX -=SPEED_MISSILE;
        // calcul de la borne haute et basse n Y et droite et gauche en X
        float missileRight = missileX + missile.getWidth();
        float missileDown = missileY + missile.getHeight();

        if (ifCollision(missileX, missileY, missileDown)){
            missileX = -100.0f;
            soundPlayer.playHitMissileSound();
            if(isTimeAttack) score-=30;
            if(isEndurance) lifeCounter-=1;
        }

        if (missileX <0) {
            missileX = (float)Math.floor(frmaeWidth*1.1);
            missileY = (float)Math.floor(Math.random() * (frameHeight - missile.getHeight()));
        }
        missile.setX(missileX);
        missile.setY(missileY);


        ////////////  Mario  ///////////
        marioY += marioSpeed;
        if (marioY<0) marioY=0;
        if (marioY+marioSize >=frameHeight) marioY=frameHeight-marioSize;
        mario[0].setY(marioY);
        mario[1].setY(marioY);

        marioSpeed+=SPEED_MARION_DOWN;
        if (action_flg){
            mario[0].setVisibility(View.INVISIBLE);
            mario[1].setVisibility(View.VISIBLE);
            action_flg=false;
            }else {
            mario[0].setVisibility(View.VISIBLE);
            mario[1].setVisibility(View.INVISIBLE);
            }

        //Update Score
        if (score<0) score = 0;
        scoreLabel.setText(getString(R.string.score, score));

        //Update life
       if (lifeCounter==2) up3.setVisibility(View.INVISIBLE);
       if (lifeCounter==1) up2.setVisibility(View.INVISIBLE);
       if (lifeCounter==0) up1.setVisibility(View.INVISIBLE);
    }

    public void gameOver() {
        //Stop Timer
        if (timer != null) {
            timer.cancel();
            timer = null;
            start_flg = false;
            soundPlayer.pauseHurryUp();
        }
        titleLabel.setText(getString(R.string.time_over));
        titleLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
        soundPlayer.playEndGameSound();

        //Set visibility
        startLayout.setVisibility(View.VISIBLE);
        startScoreLabel.setVisibility(View.VISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        upLabel.setVisibility(View.INVISIBLE);
        mario[0].setVisibility(View.INVISIBLE);
        mario[1].setVisibility(View.INVISIBLE);
        piece.setVisibility(View.INVISIBLE);
        champi.setVisibility(View.INVISIBLE);
        missile.setVisibility(View.INVISIBLE);

        //update scoreLabeel in layout
        startScoreLabel.setText(getString(R.string.score,score));

        //Update High Score
       if (isTimeAttack) {
           if (score > highScoreTime) {
               highScoreTime = score;
               //highScoreLabel.setText("High Score : "+highScore);
               highScoreLabelTime.setText(getString(R.string.high_score, highScoreTime));

               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putInt("TIME HIGH SCORE", highScoreTime);
               editor.apply();
           }
       }
       if(isEndurance) {
           if (score > highScoreEndu) {
               highScoreEndu = score;
               highScoreLabelEndu.setText(getString(R.string.high_score, highScoreEndu));

               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putInt("ENDURANCE HIGH SCORE", highScoreEndu);
               editor.apply();

           }
       }
        isTimeAttack = false;
        isEndurance = false;
    }
    public boolean ifCollision (float x, float y, float down) {
        // Gestion de la collision par box mario et la piece etant des box il faut gérer
        // la collision en X avec la borne Y inferieure et Y suppérieure
        // La gestion avec un centre de gravité representant la piece
        // dans les bornes X et Y est moins précise mais plus facile
        if ((down < marioY+marioSize && marioY <down && mario[0].getWidth()>x)
                ||(y < marioY+marioSize && marioY <y && mario[0].getWidth()>x)) {
            return true;
        }
        return false;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flg) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg=true;
                marioSpeed = SPEED_MARIO_JUMP;
                soundPlayer.playJumpSound();
                System.out.println(marioY + "= Mario Y");
                System.out.println(marioSize + "= Mario Size");
            }
        }
        return super.onTouchEvent(event);
    }



    public void startGame(){
        start_flg= true;
        soundPlayer.playStartGameSound();

        //get frameheight and frame width
        if (frameHeight == 0){
            frameHeight = gameFrame.getHeight()-ground.getHeight();
            frmaeWidth = gameFrame.getWidth();
            marioSize = mario[0].getHeight();
        }

        //Set Initial position
        marioY = frameHeight - marioSize;
        pieceX = -100.0f;
        champiX = -100.0f;
        missileX = -100.0f;
        mario[0].setY(marioY);
        mario[1].setY(marioY);
        piece.setX(pieceX);
        champi.setX(champiX);
        missile.setX(missileX);

        //Initialize time
        startTime = System.currentTimeMillis();
        timeLabel.setText(getString(R.string.time, GAME_TIME));

        //Initialize Score
        score = 0;
        scoreLabel.setText(getString(R.string.score));

        //set Visibility
        startLayout.setVisibility(View.INVISIBLE);
        mario[0].setVisibility(View.VISIBLE);
        mario[1].setVisibility(View.INVISIBLE);
        missile.setVisibility(View.VISIBLE);
        piece.setVisibility(View.VISIBLE);
        champi.setVisibility(View.VISIBLE);
        scoreLabel.setVisibility(View.VISIBLE);
        if (isTimeAttack) {
            timeLabel.setVisibility(View.VISIBLE);
            upLabel.setVisibility(View.INVISIBLE);
            up1.setVisibility(View.INVISIBLE);
            up2.setVisibility(View.INVISIBLE);
            up3.setVisibility(View.INVISIBLE);
        }
        if (isEndurance) {
            lifeCounter = 3;
            upLabel.setVisibility(View.VISIBLE);
            up1.setVisibility(View.VISIBLE);
            up2.setVisibility(View.VISIBLE);
            up3.setVisibility(View.VISIBLE);
            timeLabel.setVisibility(View.INVISIBLE);
        }

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
        },0,40);
    }
}

