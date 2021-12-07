package com.example.mhtdoctorapp;

import android.content.Context;
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

        holder.ModelPatientListName.setText(model.Name);
        holder.ModelPatientListEmail.setText(model.Email);
        holder.ModelPatientListTime.setText(model.Time);
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
