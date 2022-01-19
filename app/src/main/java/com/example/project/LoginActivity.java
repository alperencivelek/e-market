package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //kullanici bilgilerini iceren sahalar
    TextInputLayout userEmailWrapper,userPassword_Wrapper;
    EditText User_email, User_password;
    //giris butonu
    Button btnLogin;
    //kontrol edilecek veritabani
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //butonlarin tanimlanmasi
        userEmailWrapper=findViewById(R.id.EmailWrapper);
        userPassword_Wrapper=findViewById(R.id.user_passwordwrapper);

        User_email=findViewById(R.id.user_email);
        User_password=findViewById(R.id.user_password);

        btnLogin=(Button) findViewById(R.id.btn_Login);

        mAuth=FirebaseAuth.getInstance();
        //giris tiklanildiginda yapilacaklar
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=User_email.getText().toString().trim();
                String password=User_password.getText().toString().trim();
                //hatali bilgi girisi kontrolu
                if(email.isEmpty()){
                    userEmailWrapper.setError("Enter E-mail");
                    userEmailWrapper.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    userPassword_Wrapper.setError("Enter Password");
                    userPassword_Wrapper.requestFocus();
                    return;
                }
                //uygulamaya giris
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(LoginActivity.this,ShoppingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,"Welcome to Alpek Market!",Toast.LENGTH_LONG).show();
                            //bilgiler dogruysa hos geldiniz mesaji
                        }
                        else {
                            // herhangi bir bilgi yanlissa verilen hata mesajı
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}