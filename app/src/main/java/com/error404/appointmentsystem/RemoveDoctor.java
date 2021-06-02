package com.error404.appointmentsystem;

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

public class RemoveDoctor extends AppCompatActivity {
    private RecyclerView docSerialRecylcerview;
    private RemoveDoctorAdapter adapter;
    private List<DoctorsItem> items;
    private Button goBackButton;
    private DatabaseReference myref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_doctor);

        goBackButton = findViewById(R.id.goBackButton);

        docSerialRecylcerview = findViewById(R.id.docSerialRecylcerview);
        docSerialRecylcerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        docSerialRecylcerview.setLayoutManager(layoutManager);
        items = new ArrayList<DoctorsItem>();


        myref = FirebaseDatabase.getInstance().getReference("Doctors");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoctorsItem DataItem = snapshot.getValue(DoctorsItem.class);
                    items.add(DataItem);
                }
                adapter = new RemoveDoctorAdapter(getApplicationContext(), items);
                docSerialRecylcerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
