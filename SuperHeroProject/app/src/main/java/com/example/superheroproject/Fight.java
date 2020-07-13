package com.example.superheroproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.WidgetContainer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Fight extends AppCompatActivity {
    public Button btnGo1, btnGo2, btnReset1, btnReset2, btnFight, btnRestart;
    public TextView tvName, tvSpeed, tvHP, tvAttack, tvRace, tvName2, tvSpeed2, tvHP2, tvAttack2, tvRace2;
    public AutoCompleteTextView edSearch1, edSearch2;
    public ImageView imv1, imv2, imvVs, imvWin;
    public String url, spurl, spurl2, urlImg1, urlImg2, heroCherche, heroCherche2, name1, name2, race1, race2, speedinit, speedinit2;
    public Integer speed1, hp1, attack1, speed2, hp2, attack2, preums;
    public Integer[] tableauCombat;
    public Boolean premierQuiTape;
    public LinearLayout lName, lCard, lCard2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        btnGo1 = findViewById(R.id.btnGo1);
        btnGo2 = findViewById(R.id.btnGo2);
        btnFight = findViewById(R.id.btnFight);
        btnFight.setVisibility(View.INVISIBLE);
        btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setVisibility(View.INVISIBLE);
        tvName = findViewById(R.id.tvName);
        tvSpeed = findViewById(R.id.tvSpeed);
        tvHP = findViewById(R.id.tvHP);
        tvAttack = findViewById(R.id.tvAttak);
        tvRace = findViewById(R.id.tvRace);
        tvName2 = findViewById(R.id.tvName2);
        tvSpeed2 = findViewById(R.id.tvSpeed2);
        tvHP2 = findViewById(R.id.tvHP2);
        tvAttack2 = findViewById(R.id.tvAttak2);
        tvRace2 = findViewById(R.id.tvRace2);
        edSearch1 = findViewById(R.id.edSearch1);
        edSearch2 = findViewById(R.id.edSearch2);
        imv1= findViewById(R.id.imv1);
        imv2 = findViewById(R.id.imv2);
        imvVs = findViewById(R.id.imvVs);
        imvWin = findViewById(R.id.imvWin);
        imvWin.setVisibility(View.INVISIBLE);
        lCard = findViewById(R.id.lCard);
        lCard2 = findViewById(R.id.lCard2);
        lName = findViewById(R.id.lName);

        btnGo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHero(edSearch1, name1,tvName, speed1, tvSpeed, hp1, tvHP, attack1, tvAttack,  race1, tvRace, imv1);
                speedinit = tvSpeed.getText().toString();
                speedinit2 = tvSpeed2.getText().toString();
                System.out.println("speede init 1:"+ speedinit);
                System.out.println("speede 2:"+ speedinit2);
                System.out.println("oyoyoyo"+verifHeroCharge());
                if (verifHeroCharge()) btnFight.setVisibility(View.VISIBLE);
            }
        });

        btnGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHero(edSearch2, name2, tvName2, speed2, tvSpeed2, hp2, tvHP2, attack2, tvAttack2, race2, tvRace2, imv2);
                System.out.println("tututut"+verifHeroCharge());
                if (verifHeroCharge()) btnFight.setVisibility(View.VISIBLE);

            }
        });

        btnFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Verif que les 2 heros st ddl
                if (verifHeroCharge()) {
                    lName.setVisibility(View.INVISIBLE);
                    invisible(lCard);
                    invisible(lCard2);
                    invisible(btnFight);
                    //affectation des valeurs pour le calcul du combat
                    speed1 = Integer.parseInt(tvSpeed.getText().toString());
                    speed2 = Integer.parseInt(tvSpeed2.getText().toString());
                    attack1 = Integer.parseInt(tvAttack.getText().toString());
                    attack2 = Integer.parseInt(tvAttack2.getText().toString());
                    hp1 = Integer.parseInt(tvHP.getText().toString());
                    hp2 = Integer.parseInt(tvHP2.getText().toString());

                 //   imvVs.animate().alpha(0).setDuration(500);
                   // imv1.animate().translationX(200).setDuration(1000);
                    //imv2.animate().translationX(-200).setDuration(1000);
                    fight(speed1, hp1, attack1, speed2, hp2, attack2);
                }
                else {
                    Toast.makeText(Fight.this, "Veuillez selectionner 2 Heroes", Toast.LENGTH_LONG).show();
                }

                //Intent intent = new Intent(Fight.this, Result.class);
                //speed1 = intent.getStringExtra("speed1");
                //startActivityForResult(intent, 001);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lName.setVisibility(View.VISIBLE);
                lCard.setVisibility(View.VISIBLE);
                lCard2.setVisibility(View.VISIBLE);
                btnFight.setVisibility(View.VISIBLE);
                imv2.setVisibility(View.VISIBLE);
                imv1.setVisibility(View.VISIBLE);
                imvVs.setVisibility(View.VISIBLE);
                imvVs.animate().alpha(100);
                imv1.animate().alpha(100);
                imv2.animate().alpha(100);
                invisible(btnRestart);
                invisible(imvWin);

            }
        });

    }

    private void fight(Integer speed1,  Integer hp1, Integer attack1, Integer speed2, Integer hp2,  Integer attack2) {
        //Decide du 1er à taper
        if  (speed1>speed2) {
             tableauCombat= new Integer[]{hp1, attack1, hp2, attack2};
             premierQuiTape = true;
             preums = 1;
            System.out.println("premierQuiTape : "+premierQuiTape);
            System.out.println("preums : "+preums);
            System.out.println("tab 0 : sup hp "+tableauCombat[0]);
            System.out.println("tab 1 : sup attak "+tableauCombat[1]);
            System.out.println("tab 2 : thanos hp "+tableauCombat[2]);
            System.out.println("tab 3 : thanos attak "+tableauCombat[3]);
        } else {
             tableauCombat= new Integer[]{hp2, attack2, hp1, attack1};
             premierQuiTape = false;
             preums = 0;
        }
        //moulinette de combat pour tuer un des deux
        while (tableauCombat[0] > 0 && tableauCombat[2] >0) {
            tableauCombat[2] = tableauCombat[2] - tableauCombat[1];
            System.out.println("thanos hp : "+tableauCombat[2]);
            System.out.println("toto tab 0 : "+tableauCombat[0]);
            System.out.println("toto tab 1 : "+tableauCombat[1]);
            System.out.println("toto tab 2 : "+tableauCombat[2]);
            System.out.println("toto tab 3 : "+tableauCombat[3]);
            if (tableauCombat[2] <= 0) break;
            tableauCombat[0] = tableauCombat[0] - tableauCombat[3];
            System.out.println("Superman hp : "+tableauCombat[0]);
            System.out.println("titi tab 0 : "+tableauCombat[0]);
            System.out.println("titi tab 1 : "+tableauCombat[1]);
            System.out.println("titi tab 2 : "+tableauCombat[2]);
            System.out.println("titi tab 3 : "+tableauCombat[3]);
        }
        System.out.println("preums "+preums);
        System.out.println("1erqui tape "+premierQuiTape);
        System.out.println("hp superman tab 0 : "+tableauCombat[0]);

        //Affiche le vainqueur , ou efface le perdant
        if (premierQuiTape && tableauCombat[0]>0){
          //  imv2.setVisibility(View.INVISIBLE);
            System.out.println("Premier");
            imv2.animate().rotationYBy(360f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imv2.animate().rotation(360f);
                }
            });
        } else if (premierQuiTape && tableauCombat[0]<=0) {
           // imv1.setVisibility(View.INVISIBLE);
            System.out.println("Deuxieme");


        } else if (!premierQuiTape && tableauCombat[0]>0) {
          //  imv1.setVisibility(View.INVISIBLE);
            //System.out.println("Troisieme");
            Animation eject = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotadeleft);
            imv1.startAnimation(eject);


        } else if(!premierQuiTape && tableauCombat[0]<=0){
            imv2.setVisibility(View.INVISIBLE);
            System.out.println("Quatrieme");

        }
        //invisible(imvVs);
      //  imvWin.setVisibility(View.VISIBLE);
        btnRestart.setVisibility(View.VISIBLE);
    }
    public void clockwise(View view){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.myanim);
        view.startAnimation(animation);
    }

    private void invisible(View w) {
        w.setVisibility(View.INVISIBLE);
    }


    private void loadHero(EditText edCherch, String name, TextView tvnam, Integer speed, TextView tvSpee, Integer hp, TextView tvhp, Integer attack, TextView tvAttak,  String race, TextView tvRac, ImageView imv) {
        Jsondl jsondl = new Jsondl();
        heroCherche = edCherch.getText().toString();
        spurl = "https://www.superheroapi.com/api.php/10158349667762497/search/" + heroCherche;

        try {
            System.out.println(spurl);
            String result = jsondl.execute(spurl).get();
            //Recup name
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            name = jsonArray.getJSONObject(0).getString("name");
            tvnam.setText(name);
            //Recup Powerstats
            String stat = jsonArray.getJSONObject(0).getString("powerstats");
            JSONObject jsonObjectStat = new JSONObject(stat);
            speed =  jsonObjectStat.getInt("speed");

            tvSpee.setText(speed.toString());

            Integer intelligence =  jsonObjectStat.getInt("intelligence");
            Integer strength =  jsonObjectStat.getInt("strength");
            Integer durability =  jsonObjectStat.getInt("durability");
            Integer power =  jsonObjectStat.getInt("power");
            Integer combat =  jsonObjectStat.getInt("combat");


            hp = (power*durability)/10 ;
            tvhp.setText(hp.toString());

            attack =  (intelligence*strength)/100;
            tvAttak.setText(attack.toString());

            //Recup apparence
            String apparence = jsonArray.getJSONObject(0).getString("appearance");
            JSONObject jsonObjectApp = new JSONObject(apparence);
            race = jsonObjectApp.getString("race");
            tvRac.setText(race);

            //Recup Image
            String image = jsonArray.getJSONObject(0).getString("image");
            JSONObject jsonObject1 = new JSONObject(image);
            urlImg1 = jsonObject1.getString("url");
            DownloadImage downloadImage = new DownloadImage();

            Bitmap bitmap = downloadImage.execute(urlImg1).get();
            imv.setImageBitmap(bitmap);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean verifHeroCharge() {
        speedinit = tvSpeed.getText().toString();
        speedinit2 = tvSpeed2.getText().toString();
        if (speedinit!="" && speedinit2!="") {
            return true;
        } else
            return false;
    }
}
