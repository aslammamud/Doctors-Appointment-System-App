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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHistory extends AppCompatActivity {
    private RecyclerView adminHistSerialRecylcerview;
    private AdminHistoryAdapter adapter;
    private List<HistoryItem> items;
    private Button goBackButton;
    private Query query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        goBackButton = findViewById(R.id.goBackButton);

        adminHistSerialRecylcerview = findViewById(R.id.adminHistSerialRecylcerview);
        adminHistSerialRecylcerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adminHistSerialRecylcerview.setLayoutManager(layoutManager);
        items = new ArrayList<HistoryItem>();

        query = FirebaseDatabase.getInstance().getReference("Admin-History").orderByChild("date");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HistoryItem DataItem = snapshot.getValue(HistoryItem.class);
                    items.add(DataItem);
                }
                adapter = new AdminHistoryAdapter(getApplicationContext(), items);
                adminHistSerialRecylcerview.setAdapter(adapter);
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
