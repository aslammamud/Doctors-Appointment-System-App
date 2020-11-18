package com.error404.appointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrackSerialList extends AppCompatActivity {
    private RecyclerView trackSerialRecylcerview;
    private TrackSerialListAdapter adapter;
    private List<GetAppointmentItem> items;
    private DatabaseReference reference;
    private Button goBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_serial_list);

        goBackButton = findViewById(R.id.goBackButton);

        trackSerialRecylcerview = findViewById(R.id.trackSerialRecylcerview);
        trackSerialRecylcerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        trackSerialRecylcerview.setLayoutManager(layoutManager);
        items = new ArrayList<GetAppointmentItem>();

        final String Date = getIntent().getStringExtra("DateTrack");
        final String Time = getIntent().getStringExtra("TimeTrack");

        reference = FirebaseDatabase.getInstance().getReference("Appointments").child(Date).child(Time);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GetAppointmentItem DataItem = snapshot.getValue(GetAppointmentItem.class);
                    items.add(DataItem);
                }
                adapter = new TrackSerialListAdapter(getApplicationContext(), items);
                trackSerialRecylcerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackSerialList.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
