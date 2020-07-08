package com.example.superheroproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.WidgetContainer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Fight extends AppCompatActivity {
    public Button btnGo1, btnGo2, btnReset1, btnReset2, btnFight, btnRestart;
    public TextView tvName, tvSpeed, tvHP, tvAttack, tvRace, tvName2, tvSpeed2, tvHP2, tvAttack2, tvRace2;
    public AutoCompleteTextView edSearch1, edSearch2;
    public ImageView imv1, imv2, imvVs, imvWin;
    public String url, spurl, spurl2, urlImg1, urlImg2, heroCherche, heroCherche2, name1, name2, race1, race2;
    public Integer speed1, hp1, attack1, speed2, hp2, attack2;
    public Integer[] tableauCombat;
    public Boolean premierQuiTape;
    public LinearLayout lName, lCard, lCard2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        btnGo1 = findViewById(R.id.btnGo1);
        btnGo2 = findViewById(R.id.btnGo2);
        btnReset1 = findViewById(R.id.btnReset1);
        btnReset2 = findViewById(R.id.btnReset2);
        btnFight = findViewById(R.id.btnFight);
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
            }
        });

        btnGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHero(edSearch2, name2, tvName2, speed2, tvSpeed2, hp2, tvHP2, attack2, tvAttack2, race2, tvRace2, imv2);
            }
        });

        btnFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                  fight(speed1, hp1, attack1, speed2, hp2, attack2);

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
        } else {
             tableauCombat= new Integer[]{hp2, attack2, hp1, attack1};
             premierQuiTape = false;
        }
        //moulinette de combat pour tuer un des deux
        while (tableauCombat[0] > 0 && tableauCombat[2] >0) {
            tableauCombat[2] = tableauCombat[2] - tableauCombat[1];
            System.out.println("Attakfirst hp : "+tableauCombat[2]);
            if (tableauCombat[2] <= 0) break;
            tableauCombat[0] = tableauCombat[0] - tableauCombat[3];
            System.out.println("AttackDeuz hp : "+tableauCombat[0]);
        }

        //Affiche le vainqueur , ou efface le perdant
        if (premierQuiTape && tableauCombat[0]>0){
            imv2.setVisibility(View.INVISIBLE);
        } else if (premierQuiTape && tableauCombat[0]<0) {
            imv1.setVisibility(View.INVISIBLE);
        } else if (!premierQuiTape && tableauCombat[0]>0) {
            imv1.setVisibility(View.INVISIBLE);
        } else {
            imv2.setVisibility(View.INVISIBLE);
        }
        invisible(imvVs);
        imvWin.setVisibility(View.VISIBLE);
        btnRestart.setVisibility(View.VISIBLE);
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
}