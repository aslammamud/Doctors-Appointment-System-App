package com.error404.appointmentsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private final Context context;
    private final List<AppointmentItem> items;
    private final ClickInterface mclickInterface;

    String parent;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public AppointmentsAdapter(Context context, List<AppointmentItem> items, ClickInterface clickInterface) {
        this.context = context;
        this.items = items;
        this.mclickInterface = clickInterface;
    }


    @Override
    public AppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointments_adapter, parent, false);
        return new ViewHolder(view, mclickInterface);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsAdapter.ViewHolder holder, final int position) {
        final AppointmentItem item = items.get(position);

        holder.appointmentSerialNo.setText(String.valueOf(position + 1));
        holder.appointmentSerialName.setText(item.name);
        holder.appointmentSerialTime.setText(item.time);

        holder.appointmentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Completed Appointment")
                        .setMessage("Are you sure appointment is done?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Appointment completed for this person!", Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());
                            }
                        }).setNegativeButton("No", null).show();

                //Query patientQuery = ref.child("Appointments").child(item.date).child(item.time).orderByChild("phone").equalTo(item.phone);
                //removeTheNode(patientQuery);
            }

        });

        holder.appointmentDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Discard Appointment")
                        .setMessage("Are you sure to discard patient's appointment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Appointment Discarded for this person!", Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());
                            }
                        }).setNegativeButton("No", null).show();
            }
        });

    }

/*    private void removeTheNode(Query patientQuery) {
        patientQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context, "Appointment completed for this person!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    parent = snapshot.getKey();
                    *//*Toast.makeText(view.getContext(), "Position : "+parent, Toast.LENGTH_SHORT).show();*//*
                    Log.d("parent node: ",String.valueOf(snapshot.getKey()));
                    Log.d("check: ",String.valueOf(snapshot.getRef()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        notifyDataSetChanged();
    }*/

    public interface ClickInterface {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView appointmentSerialNo, appointmentSerialName, appointmentSerialTime;
        public ImageButton appointmentDone, appointmentDiscard;
        ClickInterface clickInterface;

        public ViewHolder(View itemView, ClickInterface clickInterface) {
            super(itemView);
            appointmentSerialNo = itemView.findViewById(R.id.appointmentSerialNo);
            appointmentSerialName = itemView.findViewById(R.id.appointmentSerialName);
            appointmentSerialTime = itemView.findViewById(R.id.appointmentSerialTime);
            appointmentDone = itemView.findViewById(R.id.appointmentDone);
            appointmentDiscard = itemView.findViewById(R.id.appointmentDiscard);
            //this.clickInterface = clickInterface;
            //appointmentDone.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickInterface.onItemClick(getAdapterPosition());
        }
    }


}
