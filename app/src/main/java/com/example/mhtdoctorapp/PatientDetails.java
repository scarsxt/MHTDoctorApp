package com.example.mhtdoctorapp;

import static android.content.ContentValues.TAG;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PatientDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button scheduleNow, ok, timeButton, confirm;
    TextView appointmentDate, uName, timeTextView;
    CardView chat;
    String appDt, pName, doc_Name, id;
    Spinner spinner;
    LinearLayout date, contact;
    List<String> categories;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Calendar calendar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Intent i = getIntent();
        id = i.getStringExtra("ID");
        String by = i.getStringExtra("By");

        scheduleNow = findViewById(R.id.ScheduleNow);
        appointmentDate = findViewById(R.id.AppointmentDate);
        chat = findViewById(R.id.chats);
        contact = findViewById(R.id.contact);
        spinner = (Spinner) findViewById(R.id.date);
        date = findViewById(R.id.dateLayout);
        confirm = findViewById(R.id.confirm);
        uName = findViewById(R.id.name);
        timeTextView = findViewById(R.id.timeTextView);
        timeButton = findViewById(R.id.timeButton);

        db.collection("User").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (task.isSuccessful()) {
                            pName = documentSnapshot.getString("Name");
                            uName.setText(pName);
                        }
                    }
                });

        db.collection("DoctorUser").document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (task.isSuccessful()) {
                            doc_Name = documentSnapshot.getString("Name");
                        }
                    }
                });

        dateSet();

        db.collection("DoctorUser").document(currentUser).collection("AppointmentList").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            appDt = documentSnapshot.getString("AppointmentDate");
                            Log.d("TAG", appDt);
                            appointmentDate.setText(appDt);
                            if (appDt.equals("NotScheduled")) {
                                scheduleNow.setText("Schedule now");
                            } else {
                                scheduleNow.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        scheduleNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNow.setVisibility(View.GONE);
                date.setVisibility(View.VISIBLE);
                //dateSet.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

        // Spinner Drop down elements
        categories = new ArrayList<String>();

        categories.add("--Select--");

        for (int d = 0; d < 7; d++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, d); // Adding days
            String output = sdf.format(c.getTime());
            categories.add(output);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientDetails.this, DoctorPatientchat.class);
                i.putExtra("Name", "name");
                i.putExtra("Email", "email");
                i.putExtra("ID", id);
                startActivity(i);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                Log.d("TAG", "On Click Listener Running");

                appDt = appointmentDate.getText().toString();

                Map<String, Object> requestData =new HashMap<>();
                requestData.put("Name(User)", pName);
                requestData.put("Name(Doctor)", doc_Name);
                requestData.put("Email", currentUser);
                requestData.put("PatientId", id);
                requestData.put("AppointmentDate", appDt);
                requestData.put("Payment", "false");
                requestData.put("Approval", "Approved");
                requestData.put("Time", dateText);

                db.collection("User").document(id).collection("Appointment").document(currentUser)
                        .set(requestData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "Request Data written");
                            }
                        });

                db.collection("DoctorUser").document(currentUser).collection("RequestList").document(id)
                        .set(requestData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                Log.d("TAG", "Request Data written");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.w("TAG", "Error writing document", e);
                            }
                        });
            }
        });

    }

    public void dateSet() {
        appDt = appointmentDate.getText().toString();
        if (appDt.equals("Not Scheduled")) {
            scheduleNow.setText("Schedule Now");
        } else if (appDt.equals("--Select--")) {
            scheduleNow.setText("Schedule Now");
        } else {
            scheduleNow.setText("Re-Schedule");
            appointmentDate.setTextColor(getResources().getColor(R.color.teal_700));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String dt = adapterView.getItemAtPosition(i).toString();
        appointmentDate.setText(dt);
        scheduleNow.setVisibility(View.VISIBLE);
        dateSet();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        scheduleNow.setText("Schedule Now");
        scheduleNow.setVisibility(View.VISIBLE);
        date.setVisibility(View.GONE);
    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i(TAG, "onTimeSet: " + hour + minute);
                calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeTextView.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);
        timePickerDialog.show();
    }
}