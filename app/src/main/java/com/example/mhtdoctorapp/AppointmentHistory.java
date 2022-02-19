package com.example.mhtdoctorapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AppointmentHistory extends AppCompatActivity {
    RecyclerView DoctorPatientList;
    ArrayList<ModelDoctorPatientList> modelArrayList;
    AdapterAppointmentList adapter;
    FirebaseFirestore firebaseFirestore;
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);

        DoctorPatientList = findViewById(R.id.appHist);
        DoctorPatientList.setHasFixedSize(true);
        DoctorPatientList.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<>();

        adapter = new AdapterAppointmentList(this, modelArrayList);
        DoctorPatientList.setAdapter(adapter);

        modelArrayList.clear();
        adapter.notifyDataSetChanged();
        firebaseFirestore.collection("DoctorUser").document(currentUser).collection("AppointmentList").orderBy("NameUser", Query.Direction.ASCENDING)    //.whereEqualTo("Status", "Approved").whereEqualTo("ShopVisibility", "Visible").orderBy("ShopName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                modelArrayList.add(dc.getDocument().toObject(ModelDoctorPatientList.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}