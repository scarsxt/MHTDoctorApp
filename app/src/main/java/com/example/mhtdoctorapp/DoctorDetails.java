package com.example.mhtdoctorapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorDetails extends AppCompatActivity {

    EditText DoctorDetailsName, DoctorDetailsEmail;
    Button DoctorDetailsSubmitBtn;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirebaseUser muser;

    String editMode, uName, uDOB, uGender, eGender, uNumber;
    TextView name, age, edit, dob, eDob, gender;
    EditText eName, eAge;
    CalendarView calendarView;
    RadioGroup radioGroup;
    RadioButton male, female, other;
    Button save;

    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        editMode = "false";

        Intent i = getIntent();
        editMode = i.getStringExtra("EditMode");
        //uNumber = i.getStringExtra("Number");

        //TextView
        name  = (TextView)findViewById(R.id.name);
        edit = findViewById(R.id.edit);
        dob = findViewById(R.id.DoB);
        eDob = findViewById(R.id.EDoB);
        gender = findViewById(R.id.gender);

        //EditText
        eName = findViewById(R.id.editName);

        //Calendar
        calendarView = findViewById(R.id.calendarView);

        //RadioGroup
        radioGroup = findViewById(R.id.groupradio);

        //Radio Button
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);

        //Button
        save = findViewById(R.id.save);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorDetails.this, DoctorDetails.class);
                i.putExtra("editMode" , "true");
                startActivity(i);
            }
        });

        //Applying settings based on edit mode
        if (editMode.equals("true")) {
            name.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            dob.setVisibility(View.GONE);
            gender.setVisibility(View.GONE);


            eName.setVisibility(View.VISIBLE);
            eDob.setVisibility(View.VISIBLE);
            male.setVisibility(View.VISIBLE);
            female.setVisibility(View.VISIBLE);
            other.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
        }

        if (editMode.equals("false")) {

            name.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            dob.setVisibility(View.VISIBLE);
            gender.setVisibility(View.VISIBLE);


            eName.setVisibility(View.GONE);
            eDob.setVisibility(View.GONE);
            male.setVisibility(View.GONE);
            female.setVisibility(View.GONE);
            other.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);




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
                                    String db = document.getString("DateOfBirth");
                                    String g = document.getString("Gender");
                                    name.setText(n);
                                    dob.setText(db);
                                    gender.setText(g);
                                }
                            }
                        }
                    });


        }

        //Date of Birth
        eDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
            }
        });

        calendarView.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override

                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    public void onSelectedDayChange(
                            @NonNull CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth)
                    {

                        // Store the value of date with
                        // format in String type Variable
                        // Add 1 in month because month
                        // index is start with 0
                        String Date
                                = dayOfMonth + "-"
                                + (month + 1) + "-" + year;

                        // set this date in TextView for Display
                        eDob.setText(Date);
                    }
                });

        //Gender
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                    }
                });

        //Saving data to database
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(DoctorDetails.this,
                            "No answer has been selected",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    RadioButton radioButton
                            = (RadioButton)radioGroup
                            .findViewById(selectedId);

                    // Now display the value of selected item
                    // by the Toast message

                    eGender = radioButton.getText().toString();

                    //Toast.makeText(CreateAccount.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }










                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();;

                uName = eName.getText().toString();
                uDOB = eDob.getText().toString();
                uGender = eGender.toString();

                Map<String, Object> accountData = new HashMap<>();
                accountData.put("Name", uName);
                accountData.put("DateOfBirth", uDOB);
                accountData.put("Gender", uGender);
                accountData.put("UID",currentUser);

                db.collection("DoctorUser").document(currentUser)
                        .set(accountData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                startActivity(new Intent(DoctorDetails.this, MainActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });
                String  currentDateTimeString = DateFormat.getDateTimeInstance()
                        .format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 0); // Adding 5 days
                String output = sdf.format(c.getTime());

                Map<String, Object> addToUser = new HashMap<>();
                addToUser.put("UId", currentUser);
                addToUser.put("Name(Doctor)", "Sample");
                addToUser.put("Time", currentDateTimeString);
                addToUser.put("Validity", output);
                addToUser.put("PatientId", currentUser);

                db.collection("DoctorUser").document(currentUser).collection("Appointment")
                        .add(addToUser)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                            }
                        });
            }
        });

        /*
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();
        String UID = muser.getUid();

        DoctorDetailsName = findViewById(R.id.DoctorDetailsName);
        DoctorDetailsEmail = findViewById(R.id.DoctorDetailsEmail);

        DoctorDetailsSubmitBtn = findViewById(R.id.save);
        DoctorDetailsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = DoctorDetailsName.getText().toString();
                String Email = DoctorDetailsEmail.getText().toString();

                Map<String, Object> DoctorDetails = new HashMap<>();
                DoctorDetails.put("Name", Name);
                DoctorDetails.put("Email", Email);
                DoctorDetails.put("UID", UID);

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

         */
    }
}