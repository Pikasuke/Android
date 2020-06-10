package com.example.passwordvalidator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;
    private TextView tvPassEror, tvEmailEror, tvNameEror;
    private CardView cardOne, cardTwo, cardThree, cardFour, btnRegistrer;
    private boolean isAtLeast8 = false, hasUppercase=false, hasNumber=false, hasSymbol=false, isRegistraionClickable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvNameEror = findViewById(R.id.tvNameEror);
        tvEmailEror = findViewById(R.id.tvEmailEror);
        tvPassEror = findViewById(R.id.tvPassEror);
        cardOne = findViewById(R.id.cardOne);
        cardTwo = findViewById(R.id.cardTwo);
        cardThree = findViewById(R.id.cardThree);
        cardFour = findViewById(R.id.cardFour);
        btnRegistrer = findViewById(R.id.btnRegister);

        btnRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if (name.length() > 0 && email.length() > 0 && pass.length()>0) {
                    if (isRegistraionClickable) {
                        Toast.makeText(MainActivity.this, "Registration successfull", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (name.length()==0){
                        tvNameEror.setVisibility(View.VISIBLE);
                    }
                    if (email.length()==0){
                        tvEmailEror.setVisibility(View.VISIBLE);
                    }
                    if (pass.length()==0){
                        tvPassEror.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        inputChange();

    }

    private  void checkEmptyField(String name, String email, String  pass) {
        if (name.length()>0 && tvNameEror.getVisibility() == View.VISIBLE) {
            tvNameEror.setVisibility(View.GONE);
        }
         if (email.length()>0 && tvEmailEror.getVisibility() == View.VISIBLE) {
            tvEmailEror.setVisibility(View.GONE);
        }
         if (pass.length()>0 && tvPassEror.getVisibility() == View.VISIBLE) {
            tvPassEror.setVisibility(View.GONE);
        }
    }
    @SuppressLint("RessourceType")
    private void passCheck() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();

        checkEmptyField(name, email, pass);

        // check 8 character
        if(pass.length()>=8) {
            isAtLeast8=true;
            cardOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            isAtLeast8=false;
            cardOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }
        // for uppercase
        if(pass.matches("(.*[A-Z].*)")) {
            hasUppercase=true;
            cardTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasUppercase=false;
            cardTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }
        // for number
        if(pass.matches("(.*[0-9].*)")) {
            hasNumber=true;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasNumber=false;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }

        // for number
        if(pass.matches("(.*[0-9].*)")) {
            hasNumber=true;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasNumber=false;
            cardThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }

        // for symbol
        if(pass.matches("^(?=.*[_.()]).*$")) {
            hasSymbol=true;
            cardFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasSymbol=false;
            cardFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }

        checkAllData(email);
    }



    //if all fiels are filled properly the btn color will change
    @SuppressLint("RessourceType")
    private void checkAllData(String email) {
        if (isAtLeast8 && hasUppercase && hasNumber && hasSymbol && email.length()>0) {
            isRegistraionClickable = true;
            btnRegistrer.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            isRegistraionClickable = false;
            btnRegistrer.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefaul)));
        }
    }

    private void  inputChange() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            passCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            passCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
