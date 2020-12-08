package com.error404.appointmentsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorHomeActivity extends AppCompatActivity {

    private Button appointmentsButton, deptButton, trackButton, tipsButton, emergencyButton, faqButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        deptButton = findViewById(R.id.deptButton);
        tipsButton = findViewById(R.id.tipsButton);
        emergencyButton = findViewById(R.id.emergencyButton);
        faqButton = findViewById(R.id.faqButton);
        appointmentsButton = findViewById(R.id.appointmentsButton);
        trackButton = findViewById(R.id.trackButton);

        deptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, Departments.class);
                startActivity(intent);
            }
        });
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHomeActivity.this, TrackSerial.class);
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
                        startActivity(new Intent(DoctorHomeActivity.this, HomeActivity.class));
                        finish();
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
