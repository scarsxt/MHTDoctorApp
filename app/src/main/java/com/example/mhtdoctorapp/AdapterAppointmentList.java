package com.example.mhtdoctorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAppointmentList extends RecyclerView.Adapter<AdapterAppointmentList.MyViewHolder> {
    Context context;
    ArrayList<ModelDoctorPatientList> modelArrayList;

    public AdapterAppointmentList(Context context, ArrayList<ModelDoctorPatientList> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appointment_list_layout, parent, false);
        return new AdapterAppointmentList.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelDoctorPatientList model = modelArrayList.get(position);

        String name = model.NameUser;
        String time = model.AppointmentDate;

        holder.ModelPatientListName.setText(name);
        holder.ModelPatientListTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ModelPatientListName, ModelPatientListTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelPatientListName = itemView.findViewById(R.id.name);
            ModelPatientListTime = itemView.findViewById(R.id.appointmentDate);
        }
    }
}
