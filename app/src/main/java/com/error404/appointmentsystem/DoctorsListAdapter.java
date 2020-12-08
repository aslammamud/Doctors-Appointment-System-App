package com.error404.appointmentsystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.ViewHolder> {
    private final Context context;
    private final List<DoctorsItem> items;
    private Uri imageUri;

    public DoctorsListAdapter(Context context, List<DoctorsItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public DoctorsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doctorslist_adapter, parent, false);
        return new DoctorsListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsListAdapter.ViewHolder holder, int position) {
        final DoctorsItem item = items.get(position);

        //Picasso.get().load(item.imageUri).fit().centerCrop().into(holder.showPicture);
        holder.showName.setText(item.name);
        holder.showSpeciality.setText(item.speciality);
        holder.showDegree.setText(item.degree);
        holder.showPhone.setText(item.phone);
        holder.showEmail.setText(item.email);
        holder.getAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = item.name;
                String sId = item.id;
                Intent intent = new Intent(v.getContext(), GetAppointment.class);
                intent.putExtra("DoctorName", sName);
                intent.putExtra("DoctorID", sId);
                v.getContext().startActivity(intent);
            }
        });

        holder.Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sId = item.id;
                Intent intent = new Intent(v.getContext(), Feedback.class);
                intent.putExtra("DoctorID", sId);
                v.getContext().startActivity(intent);
            }
        });

        boolean isExpended = items.get(position).isExpanded();
        holder.expandableView.setVisibility(isExpended ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showName, showPhone, showEmail, showSpeciality, showDegree;
        public ImageView showPicture;
        public Button getAppointment, Feedback;

        LinearLayout expandableView;
        Button arrowBtn;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);
            expandableView = itemView.findViewById(R.id.expandableView);

            showPicture = itemView.findViewById(R.id.showPicture);
            showName = itemView.findViewById(R.id.showName);
            showSpeciality = itemView.findViewById(R.id.showSpeciality);
            showDegree = itemView.findViewById(R.id.showDegree);
            showPhone = itemView.findViewById(R.id.showPhone);
            showEmail = itemView.findViewById(R.id.showEmail);

            getAppointment = itemView.findViewById(R.id.getAppointment);
            Feedback = itemView.findViewById(R.id.Feedback);

            arrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility() == View.GONE) {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expandableView.setVisibility(View.GONE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                }
            });
/*
            getAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),GetAppointment.class);
                    v.getContext().startActivity(intent);
                }
            });

            Feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Feedback.class);
                    v.getContext().startActivity(intent);
                }
            });

 */
        }
    }
}
