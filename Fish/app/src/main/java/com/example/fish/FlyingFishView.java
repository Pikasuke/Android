package com.example.fish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private  int fishX = 10;
    private  int fishY;
    private  int fishSpeed;
    private int score, lifeCounter;

    private int canvasWidth, canvasHeight;

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    private boolean touch = false;

    private Bitmap backImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backImage, 0, 0, null);
        //hauteur max du poisson (sous score = la taille du poisson)
        int minFishY = fish[0].getHeight();
        //basseur min du poisson = taille de l'ecran moins 3 x la taille du poisson
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;
        //empechecher le poisson de dépasser les bordure haute
        if (fishY < minFishY)
        {
            fishY = minFishY;
        }
        // et basse
        if (fishY > maxFishY)
        {
            fishY = maxFishY;
        }
        fishSpeed= fishSpeed + 2;

        //si touch le poisson chane et vole
        if (touch) {
            canvas.drawBitmap(fish[1], fishX,fishY,null);
            touch = false;
        }else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }
        //YELOW BALL
        //la boule avance de façon reguliere
            yellowX = yellowX - yellowSpeed;
            //si le poisson touche la boulle disparait et le score augmente
            if (hitBallCheck(yellowX,yellowY)){
                score=score +10;
                //la boulle perd 100 de X disparait
                yellowX = -100;
            }
            //si la boulle disparait elle reaparait de l'auttre coté de hauteur random et 21 x (taille de 16)
            if (yellowX<0) {
                yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        //GREEN BALL
        //la boule avance de façon reguliere
        greenX = greenX - greenSpeed;
        //si le poisson touche la boulle disparait et le score augmente
        if (hitBallCheck(greenX,greenY)){
            score=score +50;
            //la boulle perd 100 de X et de l'ecran disparait
            greenX = -100;
        }
        //si la boulle disparait elle reaparait de l'auttre coté de hauteur random et 21 x (taille de 16)
        if (greenX<0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        //RED BALL
        //la boule avance de façon reguliere
        redX = redX - redSpeed;
        //si le poisson touche la boulle disparait et le score augmente
        if (hitBallCheck(redX,redY)){

            //la boulle perd 100 de X et de l'ecran disparait
            redX = -100;
            lifeCounter--;
            if (lifeCounter ==0){
                Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
               gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               gameOverIntent.putExtra("point", score);
                getContext().startActivity(gameOverIntent);
            }
        }
        //si la boulle disparait elle reaparait de l'auttre coté de hauteur random et 21 x (taille de 16)
        if (redX<0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }



        //DESSINS DE ELEMENTS
        canvas.drawCircle(yellowX, yellowY, 30, yellowPaint);
        canvas.drawCircle(greenX, greenY, 15, greenPaint);
        canvas.drawCircle(redX, redY, 45, redPaint);

        canvas.drawText("Score : " + score, 20, 60, scorePaint);
        //Dessin dynamique des vies
        for (int i = 0;i<3;i++){
            int x = (int) (580 + life[0].getWidth() * 1.5*i);
            int y = 10;
            if (i < lifeCounter){
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

   /*     dessin fixe de la vie
        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 680, 10, null);
        canvas.drawBitmap(life[0], 780, 10, null);*/
    }

    public boolean hitBallCheck(int x, int y) {
        //si fishx(x0 du poisson) est inf à la boule et que la boule est inferieur à taille du poisson
        // pareil en Y on a touché la boule
        if (fishX<x && x < (fishX + fish[0].getWidth()) && fishY<y && y<(fishY+fish[0].getHeight())) {
            return true;
        }
        return false;
    }
        //si touche down -22
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
