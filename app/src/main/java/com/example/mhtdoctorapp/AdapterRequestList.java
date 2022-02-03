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

public class AdapterRequestList extends RecyclerView.Adapter<AdapterRequestList.MyViewHolder> {

    Context context;
    ArrayList<ModelRequestList> requestLists;

    public AdapterRequestList(Context context, ArrayList<ModelRequestList> requestLists) {
        this.context = context;
        this.requestLists = requestLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_list_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelRequestList model = requestLists.get(position);

        String name = model.NameUser;
        //String time = model.Time;
        String id = model.PatientId;

        holder.ModelRequestListName.setText(name);
        //holder.ModelPatientListTime.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context, PatientDetails.class);
                a.putExtra("Name", name);
                //a.putExtra("Time",time);
                a.putExtra("ID", id);
                a.putExtra("By", "request");
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ModelRequestListName, ModelPatientListEmail, ModelPatientListTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ModelRequestListName = itemView.findViewById(R.id.requestName);
            //ModelPatientListEmail = itemView.findViewById(R.id.ModelPatientListEmail);
            //ModelPatientListTime = itemView.findViewById(R.id.ModelPatientListTime);
        }
    }
}
