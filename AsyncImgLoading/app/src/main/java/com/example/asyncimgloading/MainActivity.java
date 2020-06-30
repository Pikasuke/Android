package com.example.asyncimgloading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public Button btn;
    public ImageView imv;
    public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://images.pexels.com/photos/7974/pexels-photo.jpg?cs=srgb&dl=apple-bureau-bureau-a-domicile-espace-de-travail-7974.jpg&fm=jpg";
        btn = findViewById(R.id.btn);
        imv = findViewById(R.id.imv);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(v);
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


}
