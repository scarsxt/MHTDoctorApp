package com.example.mhtdoctorapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDoctorPatientList extends RecyclerView.Adapter<AdapterDoctorPatientList.MyViewHolder> {

    Context context;
    ArrayList<ModelDoctorPatientList> modelArrayList;

    public AdapterDoctorPatientList(Context context, ArrayList<ModelDoctorPatientList> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.patientlistlayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelDoctorPatientList model = modelArrayList.get(position);

        String name = model.Name;
        String email = model.Email;
        String time = model.Time;
        String id = model.PatientId;

        holder.ModelPatientListName.setText(name);
        holder.ModelPatientListEmail.setText(email);
        holder.ModelPatientListTime.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context, PatientDetails.class);
                a.putExtra("Name", name);
                a.putExtra("Email", email);
                a.putExtra("ID", id);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ModelPatientListName, ModelPatientListEmail, ModelPatientListTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelPatientListName = itemView.findViewById(R.id.ModelPatientListName);
            ModelPatientListEmail = itemView.findViewById(R.id.ModelPatientListEmail);
            ModelPatientListTime = itemView.findViewById(R.id.ModelPatientListTime);
        }
    }
}
