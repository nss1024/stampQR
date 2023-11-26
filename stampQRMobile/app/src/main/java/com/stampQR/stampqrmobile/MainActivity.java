package com.stampQR.stampqrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stampQR.stampqrmobile.services.UserClient;
import com.stampQR.stampqrmobile.wrappers.QRDataList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginBtn;

    String baseUrl = "http://18.133.86.241:8061";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils utils = new Utils();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Boolean inputValid = Boolean.TRUE;
                                            if (!utils.validateTextLength(username.getText().toString(), 3)) {
                                                Toast.makeText(view.getContext(), "User name less than 3 characters!", Toast.LENGTH_SHORT).show();
                                                inputValid = Boolean.FALSE;
                                            }
                                            if (!utils.validateTextLength(password.getText().toString(), 3)) {
                                                Toast.makeText(view.getContext(), "Password less than 3 characters!", Toast.LENGTH_SHORT).show();
                                                inputValid = Boolean.FALSE;
                                            }
                                            if (inputValid) {


                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

                                                UserClient userClient = retrofit.create(UserClient.class);
                                                String userName = username.getText().toString();
                                                String pwd = password.getText().toString();
                                                String base = userName.trim() + ":" + pwd.trim();
                                                String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

                                                userClient.getQrDataList(userName, authHeader).enqueue(
                                                //userClient.getQrDataList().enqueue(
                                                        new Callback<QRDataList>() {
                                                            @Override
                                                            public void onResponse(Call<QRDataList> call, Response<QRDataList> response) {
                                                                if(response.isSuccessful()) {
                                                                    Intent intent = new Intent(MainActivity.this, QRList.class);
                                                                    intent.putExtra("qrDataList", response.body());
                                                                    startActivity(intent);
                                                                }else{
                                                                    Toast.makeText(view.getContext(), "Could not retrieve data. Please check your credentials!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<QRDataList> call, Throwable t) {
                                                                System.out.println(t);
                                                                Toast.makeText(view.getContext(), "Could not retrieve data. Please check your credentials!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                );
                                            }
                                        }
                                    }
        );

    }
}