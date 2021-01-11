package com.error404.appointmentsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class DoctorHomeActivity extends AppCompatActivity {

    private ImageButton docappointmentsButton, docdeptButton, doctrackButton, doctipsButton, docemergencyButton, docfaqButton;
    private TextView doctorNameShow;
    private DatabaseReference myRef;
    private String DocNameShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Paper.init(this);
        final String Docid = Paper.book().read(Prevalent.UserIdKey);

        myRef = FirebaseDatabase.getInstance().getReference("Doctors").child(Docid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DocNameShow = snapshot.child("name").getValue().toString();
                doctorNameShow.setText(DocNameShow);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctorNameShow = findViewById(R.id.doctorNameShow);
        docappointmentsButton = findViewById(R.id.docappointmentsButton);
        docdeptButton = findViewById(R.id.docdeptButton);
        doctrackButton = findViewById(R.id.doctrackButton);
        docemergencyButton = findViewById(R.id.docemergencyButton);
        doctipsButton = findViewById(R.id.doctipsButton);
        docfaqButton = findViewById(R.id.docfaqButton);


        docappointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Appointments.class);
                startActivity(intent);
            }
        });

        docdeptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Departments.class);
                startActivity(intent);
            }
        });
        doctrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, TrackSerial.class);
                startActivity(intent);
            }
        });
        docemergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Emergency.class);
                startActivity(intent);
            }
        });
        doctipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Tips.class);
                startActivity(intent);
            }
        });
        docfaqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, FAQ.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Paper.book().write(Prevalent.UserIdKey, "id");
                        Paper.book().write(Prevalent.UserPasswordKey, "pass");
                        Paper.book().write(Prevalent.ParentDB, "user");
                        finish();
                        startActivity(new Intent(DoctorHomeActivity.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


        //this.moveTaskToBack(true);
    }
}
