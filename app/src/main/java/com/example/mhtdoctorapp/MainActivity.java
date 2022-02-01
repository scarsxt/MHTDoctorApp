package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView DoctorPatientList, RequestList;
    ArrayList<ModelDoctorPatientList> modelArrayList;
    ArrayList<ModelRequestList> requestLists;
    AdapterDoctorPatientList myAdapter, requestAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String name;
    Button confirm;


    TextView userName;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String Uid = mUser.getUid();

        DoctorPatientList = findViewById(R.id.DoctorPatientList);
        DoctorPatientList.setHasFixedSize(true);
        DoctorPatientList.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<>();

        myAdapter = new AdapterDoctorPatientList(this, modelArrayList);
        DoctorPatientList.setAdapter(myAdapter);

        modelArrayList.clear();
        myAdapter.notifyDataSetChanged();
        firebaseFirestore.collection("DoctorUser").document(Uid).collection("AppointmentList").orderBy("Name(User)", Query.Direction.ASCENDING)    //.whereEqualTo("Status", "Approved").whereEqualTo("ShopVisibility", "Visible").orderBy("ShopName", Query.Direction.ASCENDING)
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
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });




        RequestList = findViewById(R.id.requestList);
        RequestList.setHasFixedSize(true);
        RequestList.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        requestLists = new ArrayList<>();

        myAdapter = new AdapterDoctorPatientList(this, modelArrayList);
        RequestList.setAdapter(myAdapter);

        requestLists.clear();
        myAdapter.notifyDataSetChanged();
        firebaseFirestore.collection("DoctorUser").document(Uid).collection("RequestList").orderBy("Name(User)", Query.Direction.ASCENDING)    //.whereEqualTo("Status", "Approved").whereEqualTo("ShopVisibility", "Visible").orderBy("ShopName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                requestLists.add(dc.getDocument().toObject(ModelRequestList.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
























        //logout
        logout = findViewById(R.id.settings);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        userName = findViewById(R.id.userName);
        db.collection("DoctorUser").document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                String n = document.getString("Name");
                                userName.setText(n);
                            } else {

                            }
                        }
                    }
                });

        /*
        db.collection("").document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (task.isSuccessful())
                        {
                            name = documentSnapshot.getString("Name");
                        }
                    }
                });

        //userName
        userName = findViewById(R.id.userName);
        userName.setText(name);

         */

    }
}