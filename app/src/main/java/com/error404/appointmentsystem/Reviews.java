package com.error404.appointmentsystem;

import android.os.Bundle;

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

public class Reviews extends AppCompatActivity {
    private RecyclerView recylerReview;
    private ReviewsAdapter adapter;
    private List<FeedbackItem> items;
    private DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        recylerReview = findViewById(R.id.recylerReview);
        recylerReview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recylerReview.setLayoutManager(layoutManager);
        items = new ArrayList<FeedbackItem>();


        reference = FirebaseDatabase.getInstance().getReference("Feedbacks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedbackItem DataItem = snapshot.getValue(FeedbackItem.class);
                    items.add(DataItem);
                }
                adapter = new ReviewsAdapter(getApplicationContext(), items);
                recylerReview.setAdapter(adapter);
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
