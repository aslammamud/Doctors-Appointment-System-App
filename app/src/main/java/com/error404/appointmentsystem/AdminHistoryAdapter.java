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

import java.util.List;

public class AdminHistoryAdapter extends RecyclerView.Adapter<AdminHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<HistoryItem> items;

    public AdminHistoryAdapter(Context context, List<HistoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AdminHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_history_adapter, parent, false);
        return new AdminHistoryAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminHistoryAdapter.ViewHolder holder, final int position) {
        final HistoryItem item = items.get(position);

        holder.adminHistDate.setText(item.date);
        holder.adminHistMSG.setText(item.message);
        holder.adminHistDocId.setText(item.doctorid);
        holder.adminHistRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()) // problem over here
                        .setTitle("Clear History")
                        .setMessage("Are you sure you want to remove?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "History Cleared For ID : " + item.doctorid, Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());
                            }
                        }).setNegativeButton("No", null).show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView adminHistDate, adminHistMSG, adminHistDocId;
        public ImageButton adminHistRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            adminHistDate = itemView.findViewById(R.id.adminHistDate);
            adminHistMSG = itemView.findViewById(R.id.adminHistMSG);
            adminHistDocId = itemView.findViewById(R.id.adminHistDocId);
            adminHistRemove = itemView.findViewById(R.id.adminHistRemove);

        }
    }
}
