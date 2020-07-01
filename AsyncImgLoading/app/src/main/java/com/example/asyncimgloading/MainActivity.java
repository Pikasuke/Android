package com.example.asyncimgloading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public Button btn, btn2;
    public ImageView imv, imv2;
    public EditText et;
    public String url, url2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://images.pexels.com/photos/7974/pexels-photo.jpg?cs=srgb&dl=apple-bureau-bureau-a-domicile-espace-de-travail-7974.jpg&fm=jpg";
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        imv = findViewById(R.id.imv);
        imv2 = findViewById(R.id.imv2);
        et = findViewById(R.id.et);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(v);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage2();
            }
        });

    }


    private void loadImage(View view) {
        DownloadImage downloadImage = new DownloadImage();

        try {
            Bitmap bitmap = downloadImage.execute(url).get();
            imv.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadImage2() {

        DownloadImage downloadImage = new DownloadImage();
        url2 = et.getText().toString();

        try {
            Bitmap  bitmap = downloadImage.execute(url2).get();
            imv2.setImageBitmap(bitmap);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
