package com.error404.appointmentsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Feedback extends AppCompatActivity {

    float rateValue = 0;
    private Button sendFeedback, discardFeedback;
    private EditText nameFeedback, emailFeedback, messageFeedback;
    private RatingBar ratingFeedback;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        myRef = FirebaseDatabase.getInstance().getReference("Feedbacks");

        sendFeedback = findViewById(R.id.sendFeedback);
        discardFeedback = findViewById(R.id.discardFeedback);
        nameFeedback = findViewById(R.id.nameFeedback);
        emailFeedback = findViewById(R.id.emailFeedback);
        messageFeedback = findViewById(R.id.messageFeedback);
        ratingFeedback = findViewById(R.id.ratingFeedback);

        final String ratingdoctorid = getIntent().getStringExtra("DoctorID");
        final String ratingdoctorname = getIntent().getStringExtra("DoctorName");

        ratingFeedback.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();
                Toast.makeText(Feedback.this, String.valueOf(rateValue), Toast.LENGTH_SHORT).show();
            }
        });

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namefeedback = nameFeedback.getText().toString();
                String emailfeedback = emailFeedback.getText().toString();
                String ratingfeedback = String.valueOf(rateValue);
                String messagefeedback = messageFeedback.getText().toString();
                FeedbackItem DataItem = new FeedbackItem(namefeedback, emailfeedback, ratingfeedback, ratingdoctorid, ratingdoctorname, messagefeedback);

                myRef.child(namefeedback).setValue(DataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        makeText(getApplicationContext(), "Thanks for your feedback!", LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeText(getApplicationContext(), "Error! Try again", LENGTH_LONG).show();
                    }
                });
            }
        });

        discardFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
