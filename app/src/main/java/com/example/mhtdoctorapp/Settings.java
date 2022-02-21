package com.example.mhtdoctorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Settings extends AppCompatActivity {
    TextView ah, ca, logout, ph;
    CardView profile;
    String n;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ph = findViewById(R.id.ph);
        //ah = findViewById(R.id.appointmentHistory);
        profile = findViewById(R.id.profile);
        ca = findViewById(R.id.mailUs);

        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, PaymentHistory.class));
            }
        });

        /*
        ah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, AppointmentHistory.class));
            }
        });

         */

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(Settings.this, DoctorDetails.class);
                i.putExtra("EditMode", "false");
                startActivity(i);
            }
        });

        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "example@example.com"));
                startActivity(intent);
            }
        });


    }
}