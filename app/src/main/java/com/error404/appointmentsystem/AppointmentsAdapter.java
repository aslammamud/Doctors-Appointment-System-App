package com.error404.appointmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private final Context context;
    private final List<AppointmentItem> items;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public AppointmentsAdapter(Context context, List<AppointmentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointments_adapter, parent, false);
        return new AppointmentsAdapter.ViewHolder(view);
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
                Toast.makeText(view.getContext(), "Completed!", Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyItemRemoved(position);

                Query patientQuery = ref.child("Appointment Completed!").child(item.date).child(item.time).orderByChild("name").equalTo(item.name);

                patientQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        holder.appointmentDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(view.getContext(), "Appointment Discarded!", Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyItemRemoved(position);

                Query patientQuery = ref.child("Appointments").child(item.date).child(item.time).orderByChild("name").equalTo(item.name);

                patientQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView appointmentSerialNo, appointmentSerialName, appointmentSerialTime;
        public ImageButton appointmentDone, appointmentDiscard;

        public ViewHolder(View itemView) {
            super(itemView);
            appointmentSerialNo = itemView.findViewById(R.id.appointmentSerialNo);
            appointmentSerialName = itemView.findViewById(R.id.appointmentSerialName);
            appointmentSerialTime = itemView.findViewById(R.id.appointmentSerialTime);
            appointmentDone = itemView.findViewById(R.id.appointmentDone);
            appointmentDiscard = itemView.findViewById(R.id.appointmentDiscard);
        }
    }

}
