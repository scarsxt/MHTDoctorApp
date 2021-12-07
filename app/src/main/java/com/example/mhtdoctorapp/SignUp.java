package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private EditText email, pw1, pw2;
    private Button reg;
    private String mail, pass1, pass2;
    private TextView sgnin;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser muser;

    //Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Prevent User from Taking screenshots or recording screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        sgnin = findViewById(R.id.sign_in);
        sgnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SignUp.this, SignIn.class);
                startActivity(a);
                finish();
            }
        });

        String str = "Already have account?<b> Sign In</b>";
        sgnin.setText(Html.fromHtml(str));

        reg = (Button)findViewById(R.id.button2);
        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
                pw1 = (EditText)findViewById(R.id.editTextTextPassword);
                pw2 = (EditText)findViewById(R.id.editTextTextPassword2);

                mail = email.getText().toString();
                pass1 = pw1.getText().toString();
                pass2 = pw2.getText().toString();

                int ver = verify(mail,pass1);

                mAuth = FirebaseAuth.getInstance();

                if (ver==1)
                {
                    add(mail,pass1);
                }

            }
        });

    }

    private int verify(String mail,String passw)
    {
        if (TextUtils.isEmpty(mail))
        {
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
            return 0;
        }
        else if (TextUtils.isEmpty(pass1))
        {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
            return 0;
        }
        else if (pass1.length()<6)
        {
            Toast.makeText(getApplicationContext(), "Password should contain minimum 6 Characters", Toast.LENGTH_LONG).show();
            return 0;
        }
        else if (TextUtils.isEmpty(pass2))
        {
            Toast.makeText(getApplicationContext(), "Please confirm password", Toast.LENGTH_LONG).show();
            return 0;
        }
        else if (!TextUtils.equals(pass1,pass2))
        {
            Toast.makeText(getApplicationContext(), "Password Mis-match", Toast.LENGTH_LONG).show();
            return 0;
        }
        else
        {
            return 1;
        }
    }

    private void add(String email,String pw)
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Sign-up Successful", Toast.LENGTH_LONG).show();
                            Intent a = new Intent(SignUp.this, DoctorDetails.class);
                            startActivity(a);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Couldn't Sign-up", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}