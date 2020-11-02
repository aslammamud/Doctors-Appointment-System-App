package com.error404.appointmentsystem;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class DoctorsList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DoctorsListAdapter adapter;
    private List<AddDoctorsItem> items;
    private DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorslist);
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<AddDoctorsItem>();

        String value = getIntent().getStringExtra("deptposition");
        Toast.makeText(DoctorsList.this, value, Toast.LENGTH_SHORT).show();


        reference = FirebaseDatabase.getInstance().getReference("Doctors").child(value);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AddDoctorsItem DataItem = snapshot.getValue(AddDoctorsItem.class);
                    items.add(DataItem);
                }
                adapter = new DoctorsListAdapter(getApplicationContext(), items);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
