package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email, psw;
    private Button sgnin;
    private TextView fgp, sgnup, skip;
    private FirebaseAuth mAuth;
    private String mail, pass;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //Prevent User from Taking screenshots or recording screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        dialog = new Dialog(SignIn.this);
        //dialog.setContentView(R.layout.skip_popup);
        //dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.delete_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        //TextView cancle1 = dialog.findViewById(R.id.cancle_popup1);
        //TextView delete11 = dialog.findViewById(R.id.delete_popup1);

        /*
        cancle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        delete11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(SignIn.this, MainActivity.class);
                c.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(c);
                finish();
            }
        });

         */


        sgnin = (Button)findViewById(R.id.button2);
        sgnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
                psw = (EditText)findViewById(R.id.editTextTextPassword);

                mail = email.getText().toString();
                pass = psw.getText().toString();

                mAuth = FirebaseAuth.getInstance();

                if(verify(mail, pass))
                {
                    sign_in(mail,pass);
                }
            }
        });

        sgnup = findViewById(R.id.sign_up);
        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SignIn.this, SignUp.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });

        String str = "New here?<b> Sign Up</b>";
        sgnup.setText(Html.fromHtml(str));


        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        /*
        fgp = findViewById(R.id.textView3);
        fgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(SignIn.this, ForegetPassword.class);
                d.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(d);
            }
        });

         */

    }

    private boolean verify(String mail, String pw)
    {
        if (TextUtils.isEmpty(mail))
        {
            Toast.makeText(getApplicationContext(), "Enter email-id", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (TextUtils.isEmpty(pw))
        {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (pw.length()<6)
        {
            Toast.makeText(getApplicationContext(), "Password should contain minimum 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void sign_in(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Signed in successfully", Toast.LENGTH_LONG).show();
                            Intent b = new Intent(SignIn.this, MainActivity.class);
                            b.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(b);
                            finish();
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "Incorrect email-id or password", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}