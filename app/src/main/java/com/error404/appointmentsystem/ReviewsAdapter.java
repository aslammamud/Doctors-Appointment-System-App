package com.error404.appointmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private final Context context;
    private final List<FeedbackItem> items;

    public ReviewsAdapter(Context context, List<FeedbackItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reviews_adapter, parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        final FeedbackItem item = items.get(position);


        holder.reveiwerName.setText("by_" + item.namefeedback);
        holder.reviewMessage.setText(item.messagefeedback);
        holder.reveiwDoctorName.setText(item.ratingdoctorname);
        holder.reveiwDoctorRating.setText("Rating : " + item.ratingfeedback);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reveiwDoctorName, reviewMessage, reveiwDoctorRating, reveiwerName;

        public ViewHolder(View itemView) {
            super(itemView);

            reveiwDoctorName = itemView.findViewById(R.id.reveiwDoctorName);
            reviewMessage = itemView.findViewById(R.id.reviewMessage);
            reveiwDoctorRating = itemView.findViewById(R.id.reveiwDoctorRating);
            reveiwerName = itemView.findViewById(R.id.reveiwerName);


        }
    }
}
