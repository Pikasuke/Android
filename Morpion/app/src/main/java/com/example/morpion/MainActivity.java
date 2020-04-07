package com.example.morpion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Tableau 2 dimmension [colonne} [ligne}
    //0 : case vide, 1 : X, 2:O
    private int plateau[][] = new int[3][3];

    // 1: joueur X, 2 joueur O
    private int joueurEnCours = 1;

    private TextView player;
    ArrayList<Button> all_button = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = findViewById(R.id.player);
        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);
        Button b4 = (Button) findViewById(R.id.b4);
        Button b5 = (Button) findViewById(R.id.b5);
        Button b6 = (Button) findViewById(R.id.b6);
        Button b7 = (Button) findViewById(R.id.b7);
        Button b8 = (Button) findViewById(R.id.b8);
        Button b9 = (Button) findViewById(R.id.b9);

        all_button.add(b1);
        all_button.add(b2);
        all_button.add(b3);
        all_button.add(b4);
        all_button.add(b5);
        all_button.add(b6);
        all_button.add(b7);
        all_button.add(b8);
        all_button.add(b9);

        for (Button bt : all_button) {
            bt.setBackground(null);
            bt.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getBackground() != null) {
            return;
        }
        switch (view.getId()) {  //if (view.id()==R.id.b1
            case R.id.b1:
                plateau[0][0] = joueurEnCours;
                break;
            case R.id.b2:
                plateau[1][0] = joueurEnCours;
                break;
            case R.id.b3:
                plateau[2][0] = joueurEnCours;
                break;
            case R.id.b4:
                plateau[0][1] = joueurEnCours;
                break;
            case R.id.b5:
                plateau[1][1] = joueurEnCours;
                break;
            case R.id.b6:
                plateau[2][1] = joueurEnCours;
                break;
            case R.id.b7:
                plateau[0][2] = joueurEnCours;
                break;
            case R.id.b8:
                plateau[1][2] = joueurEnCours;
                break;
            case R.id.b9:
                plateau[2][2] = joueurEnCours;
                break;
            default:
                return;
        }

        Drawable drawebleJoueur;
        if (joueurEnCours == 1)
            drawebleJoueur = ContextCompat.getDrawable(this, R.drawable.x);
        else
            drawebleJoueur = ContextCompat.getDrawable(this, R.drawable.o);
        view.setBackground(drawebleJoueur);

        //changement de joueur
        if (joueurEnCours == 1) {
            joueurEnCours = 2;
            player.setText("O ");
        } else {
            joueurEnCours = 1;
            player.setText("X");
        }
        int resultat = checkWinner();
        finDePartie(resultat);

    }

    //: 0 non fini, 1:X, 2:O, 3 egalité
    private int checkWinner() {
        // verif colonne
        for (int col = 0; col <= 2; col++) {
            if (plateau[col][0] != 0 && plateau[col][0] == plateau[col][1] && plateau[col][0] == plateau[col][2])
                return plateau[col][0];
        }
        //verif ligne
        for (int lign = 0; lign <= 2; lign++) {
            if (plateau[0][lign] != 0 && plateau[0][lign] == plateau[1][lign] && plateau[0][lign] == plateau[2][lign])
                return plateau[0][lign];
        }
        //verif diag
        if (plateau[0][0] != 0 && plateau[0][0] == plateau[1][1] && plateau[0][0] == plateau[2][2]) {
            return plateau[0][0];
        }
        if (plateau[0][2] != 0 && plateau[0][2] == plateau[1][1] && plateau[0][2] == plateau[2][0]) {
            return plateau[0][2];
        }

        //verif plateu plein
        boolean plateauFull = true;
        for (int col = 0; col <= 2; col++) {
            for (int lign = 0; lign <= 2; lign++)
                if (plateau[col][lign] == 0) {
                    plateauFull = false;
                    break;
                }
            if (!plateauFull)
                break;
        }
        if (plateauFull)
            // execo
            return 3;
        //partie non fini
        return 0;
    }

    private void finDePartie(int result) {
        if (result == 0) // partie nono terminé
            return;
        String message = "";
        if (result == 1) {
            message = "Les X ont gagné";
        }
        if (result == 1) {
            message = "Les 0 ont gagné";
        }
        if (result == 1) {
            message = "Egalité";
        }
        AlertDialog.Builder alertDiag = new AlertDialog.Builder(this);
        alertDiag.setTitle("fin de partie");
        alertDiag.setMessage(message);
        alertDiag.setNeutralButton("On y retourne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reset();
            }
        });
        alertDiag.setCancelable(false);
        alertDiag.show();
    }

    private void reset() {
        for (int col = 0; col <= 2; col++) {
            for (int lign = 0; lign <= 2; lign++) {
                plateau[col][lign] = 0;
            }
        }
        for (Button bt : all_button) {
            bt.setBackground(null);
        }
    }
}
