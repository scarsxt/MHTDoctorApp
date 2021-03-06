package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class DoctorPatientchat extends AppCompatActivity {

    ImageView DoctorPatientChatBackBtn;
    TextView DoctorPatientChatNameTxt;
    EditText DoctorPatientChatTxt;
    ImageView DoctorPatientChatSendBtn;
    RecyclerView DoctorPatientChatRecyclerView;
    ArrayList<Messages> messagesArrayList;
    MessagesAdapter messagesAdapter;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patientchat);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        DoctorPatientChatBackBtn = findViewById(R.id.DoctorPatientChatBackBtn);
        DoctorPatientChatBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String Name = bundle.getString("Name");
        String Email = bundle.getString("Email");
        String ReceiverId = bundle.getString("ID");
        String SenderId = mUser.getUid();

        String SenderRoom = SenderId + " " + ReceiverId;
        String ReceiverRoom = ReceiverId + " " + SenderId;

        DoctorPatientChatNameTxt = findViewById(R.id.DoctorPatientChatNameTxt);
        DoctorPatientChatNameTxt.setText(Name);

        messagesArrayList = new ArrayList<>();

        DoctorPatientChatRecyclerView = findViewById(R.id.DoctorPatientChatRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        DoctorPatientChatRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(DoctorPatientchat.this, messagesArrayList);
        DoctorPatientChatRecyclerView.setAdapter(messagesAdapter);

        //Load/get Data
        firebaseFirestore.collection("Chats")
                .document(SenderRoom)
                .collection("messages").orderBy("timeStamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            messagesArrayList.clear();
                            messagesAdapter.notifyDataSetChanged();
                            for (DocumentSnapshot document : task.getResult()){
                                //????????///////////////////////////////////////////////////////////////////
                                //String msg = String.valueOf(Messages.class);
                                Messages messages = document.toObject(Messages.class);
                                //Messages messages = (Messages) document.get(msg);
                                messagesArrayList.add(messages);
                                messagesAdapter.notifyDataSetChanged();
                                /////////////////////////////////////////////////////////////////////////////
                            }
                            messagesAdapter.notifyDataSetChanged();

                        }
                    }
                });


        DoctorPatientChatTxt = findViewById(R.id.DoctorPatientChatTxt);
        DoctorPatientChatSendBtn = findViewById(R.id.DoctorPatientChatSendBtn);
        DoctorPatientChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = DoctorPatientChatTxt.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                DoctorPatientChatTxt.setText("");

                Date date = new Date();

                Messages messages = new Messages(message,mUser.getUid(),date.getTime());

                firebaseFirestore.collection("Chats")
                        .document(SenderRoom)
                        .collection("messages")
                        .document()
                        .set(messages)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseFirestore.collection("Chats")
                                        .document(ReceiverRoom)
                                        .collection("messages")
                                        .document()
                                        .set(messages)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                finish();
                                                overridePendingTransition( 0, 0);
                                                startActivity(getIntent());
                                                overridePendingTransition( 0, 0);
                                            }
                                        });

                            }
                        });
            }
        });







    }
}