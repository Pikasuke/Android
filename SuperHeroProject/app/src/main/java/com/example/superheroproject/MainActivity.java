package com.example.superheroproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superheroproject.DownloadImage;
import com.example.superheroproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public Button btn, btn2;
    public ImageView imv, imv2;
    public EditText et;
    public TextView tv;
    public AutoCompleteTextView autoTv;
    public String url, url2, spurl, heroCherche;
    public List<String>heroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://images.pexels.com/photos/7974/pexels-photo.jpg?cs=srgb&dl=apple-bureau-bureau-a-domicile-espace-de-travail-7974.jpg&fm=jpg";
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        autoTv = findViewById(R.id.autoTv);
        tv = findViewById(R.id.tv);
        imv2 = findViewById(R.id.imv2);
        et = findViewById(R.id.et);

        //Creation de l'autocomplete champs de recherche de hero
        heroList = new ArrayList<>();
        String[] array1 = getResources().getStringArray(R.array.herolist1);
        String[] array2 = getResources().getStringArray(R.array.herolist2);
        String[] array3 = getResources().getStringArray(R.array.herolist3);
       // String[] array4 = getResources().getStringArray(R.array.herolist4);
        heroList.addAll(Arrays.asList(array1));
        heroList.addAll(Arrays.asList(array2));
        heroList.addAll(Arrays.asList(array3));
     //   heroList.addAll(Arrays.asList(array4));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, heroList);
        autoTv.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadApi();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage2();
            }
        });


    }


    private void loadApi() {
        Jsondl jsondl = new Jsondl();
        heroCherche = autoTv.getText().toString();
        spurl = "https://www.superheroapi.com/api.php/10158349667762497/search/"+heroCherche;

        try {
            System.out.println(spurl);
            String result = jsondl.execute(spurl).get();
          //  tv.setText(result);


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            String name = jsonArray.getJSONObject(0).getString("name");
            tv.setText(name);

            String image = jsonArray.getJSONObject(0).getString("image");
            JSONObject jsonObject1 = new JSONObject(image);
            String url = jsonObject1.getString("url");
            et.setText(url);



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
