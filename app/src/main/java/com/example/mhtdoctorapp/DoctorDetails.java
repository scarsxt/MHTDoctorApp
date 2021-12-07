package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctorDetails extends AppCompatActivity {

    EditText DoctorDetailsName, DoctorDetailsEmail;
    Button DoctorDetailsSubmitBtn;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();
        String UID = muser.getUid();

        DoctorDetailsName = findViewById(R.id.DoctorDetailsName);
        DoctorDetailsEmail = findViewById(R.id.DoctorDetailsEmail);

        DoctorDetailsSubmitBtn = findViewById(R.id.DoctorDetailsSubmitBtn);
        DoctorDetailsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = DoctorDetailsName.getText().toString();
                String Email = DoctorDetailsEmail.getText().toString();

                Map<String, Object> DoctorDetails = new HashMap<>();
                DoctorDetails.put("Name", Name);
                DoctorDetails.put("Email", Email);

                firebaseFirestore.collection("DoctorUser").document(UID)
                        .set(DoctorDetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Information Submitted Successfully", Toast.LENGTH_LONG).show();
                                Intent a = new Intent(DoctorDetails.this, MainActivity.class);
                                startActivity(a);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });



    }
}