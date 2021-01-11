package com.error404.appointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class GetAppointmentConfirmation extends AppCompatActivity {
    long maxid = 0;
    private TextView serialConfText, timeConfText, dateConfText, nameConfText, ageConfText, genderConfText, bloodgroupConfText, symptomsConfText, phoneConfText, addressConfText, doctornameConfText;
    private Button confirmAppointment, cancelAppointment;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getappointment_confirmation);

        final String Date = getIntent().getStringExtra("Date");
        final String Time = getIntent().getStringExtra("Time");
        final String PatientName = getIntent().getStringExtra("PatientName");
        final String Age = getIntent().getStringExtra("Age");
        final String Gender = getIntent().getStringExtra("Gender");
        final String Bloodgroup = getIntent().getStringExtra("BloodGroup");
        final String Symptoms = getIntent().getStringExtra("Symptoms");
        final String Phone = getIntent().getStringExtra("Phone");
        final String Address = getIntent().getStringExtra("Address");
        final String Doctorname = getIntent().getStringExtra("DoctorName");
        final String Doctorid = getIntent().getStringExtra("DoctorID");


        serialConfText = findViewById(R.id.serialConfText);
        timeConfText = findViewById(R.id.timeConfText);
        dateConfText = findViewById(R.id.dateConfText);
        nameConfText = findViewById(R.id.nameConfText);
        ageConfText = findViewById(R.id.ageConfText);
        genderConfText = findViewById(R.id.genderConfText);
        bloodgroupConfText = findViewById(R.id.bloodgroupConfText);
        symptomsConfText = findViewById(R.id.symptomsConfText);
        phoneConfText = findViewById(R.id.phoneConfText);
        addressConfText = findViewById(R.id.addressConfText);
        doctornameConfText = findViewById(R.id.doctornameConfText);

        timeConfText.setText(Time);
        dateConfText.setText(Date);
        nameConfText.setText(PatientName);
        ageConfText.setText(Age);
        genderConfText.setText(Gender);
        bloodgroupConfText.setText(Bloodgroup);
        symptomsConfText.setText(Symptoms);
        phoneConfText.setText(Phone);
        addressConfText.setText(Address);
        doctornameConfText.setText(Doctorname);


        confirmAppointment = findViewById(R.id.confirmAppointment);
        cancelAppointment = findViewById(R.id.cancelAppointment);

        myRef = FirebaseDatabase.getInstance().getReference("Appointments").child(Date).child(Time);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = (snapshot.getChildrenCount());
                }
                serialConfText.setText(String.valueOf(maxid + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        confirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppointmentItem DataItem = new AppointmentItem(PatientName, Age, Gender, Bloodgroup, Symptoms, Phone, Address, Date, Time, Doctorname, Doctorid);

                myRef.child(String.valueOf(maxid + 1)).setValue(DataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(GetAppointmentConfirmation.this, TrackSerial.class);
                        makeText(getApplicationContext(), "Your appointment placed successfully!", LENGTH_LONG).show();
                        finish();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeText(getApplicationContext(), "Error! Try again", LENGTH_LONG).show();
                    }
                });
            }
        });


        cancelAppointment.setOnClickListener(new View.OnClickListener() {
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
