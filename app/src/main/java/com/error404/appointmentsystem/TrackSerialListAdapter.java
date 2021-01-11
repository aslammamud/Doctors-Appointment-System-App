package com.error404.appointmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackSerialListAdapter extends RecyclerView.Adapter<TrackSerialListAdapter.ViewHolder> {
    private final Context context;
    private final List<AppointmentItem> items;

    public TrackSerialListAdapter(Context context, List<AppointmentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public TrackSerialListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_track_serial_adapter, parent, false);
        return new TrackSerialListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TrackSerialListAdapter.ViewHolder holder, int position) {
        final AppointmentItem item = items.get(position);

        holder.trackSerialNo.setText(String.valueOf(position + 1));
        holder.trackSerialName.setText(item.name);
        holder.trackSerialTime.setText(item.time);
        holder.trackSerialDoctor.setText(item.doctorname);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trackSerialNo, trackSerialName, trackSerialTime, trackSerialDoctor;

        public ViewHolder(View itemView) {
            super(itemView);
            trackSerialNo = itemView.findViewById(R.id.trackSerialNo);
            trackSerialName = itemView.findViewById(R.id.trackSerialName);
            trackSerialTime = itemView.findViewById(R.id.trackSerialTime);
            trackSerialDoctor = itemView.findViewById(R.id.trackSerialDoctor);

        }
    }
}
