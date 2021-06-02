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

public class RemoveDoctorAdapter extends RecyclerView.Adapter<RemoveDoctorAdapter.ViewHolder> {
    private final Context context;
    private final List<DoctorsItem> items;

    public RemoveDoctorAdapter(Context context, List<DoctorsItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RemoveDoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_remove_doctor_adapter, parent, false);
        return new RemoveDoctorAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RemoveDoctorAdapter.ViewHolder holder, final int position) {
        final DoctorsItem item = items.get(position);

        holder.dcSerialNo.setText(String.valueOf(position + 1));
        holder.dcSerialName.setText(item.name);
        holder.dcAddDate.setText(item.id);
        holder.dcRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Remove Doctor")
                        .setMessage("Are you sure you want to remove?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Removed " + item.name + "From Doctor's List", Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());
                            }
                        }).setNegativeButton("No", null).show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dcSerialNo, dcSerialName, dcAddDate;
        public ImageButton dcRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            dcSerialNo = itemView.findViewById(R.id.dcSerialNo);
            dcSerialName = itemView.findViewById(R.id.dcSerialName);
            dcAddDate = itemView.findViewById(R.id.dcAddDate);
            dcRemove = itemView.findViewById(R.id.dcRemove);

        }
    }
}
