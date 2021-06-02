package com.error404.appointmentsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class DoctorProfileActivity extends AppCompatActivity {
    private Button updateProfile, goBackProfile, saveUpdatedProfile, cancelUpdating;
    private TextView showProfileName, nameText, specialityText, degreeText, phoneText, emailText, passText;
    private EditText name, speciality, degree, phone, email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        Paper.init(this);

        updateProfile = findViewById(R.id.updateProfile);
        goBackProfile = findViewById(R.id.goBackProfile);
        saveUpdatedProfile = findViewById(R.id.saveUpdatedProfile);
        cancelUpdating = findViewById(R.id.cancelUpdating);

        showProfileName = findViewById(R.id.showProfileName);
        nameText = findViewById(R.id.nameText);
        specialityText = findViewById(R.id.specialityText);
        degreeText = findViewById(R.id.degreeText);
        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        passText = findViewById(R.id.passText);


        name = findViewById(R.id.name);
        speciality = findViewById(R.id.speciality);
        degree = findViewById(R.id.degree);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAllVisible();

            }
        });

        goBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelUpdating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAllGone();
            }
        });


        searchAcc();
        saveUpdatedProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = Paper.book().read(Prevalent.UserIdKey);
                String Pass = Paper.book().read(Prevalent.UserPasswordKey);
                String ParentDB = Paper.book().read(Prevalent.ParentDB);

                if (ID != "id" && Pass != "pass" && ParentDB != "user") {
                    if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(Pass) && !TextUtils.isEmpty(ParentDB)) {
                        final DatabaseReference myRef;
                        myRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(ID);

                        HashMap<String, Object> updatedValues = new HashMap<>();

                        updatedValues.put("name", name.getText().toString());
                        updatedValues.put("speciality", speciality.getText().toString());
                        updatedValues.put("degree", degree.getText().toString());
                        updatedValues.put("phone", phone.getText().toString());
                        updatedValues.put("email", email.getText().toString());
                        updatedValues.put("password", password.getText().toString());

                        myRef.updateChildren(updatedValues);
                        Toast.makeText(DoctorProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        makeAllGone();
                        finish();
                        startActivity(getIntent());
                    }
                }

            }
        });

        if (Prevalent.ParentDB.isEmpty() || Prevalent.UserIdKey.isEmpty() || Prevalent.UserPasswordKey.isEmpty()) {
            Paper.book().write(Prevalent.UserIdKey, "id");
            Paper.book().write(Prevalent.UserPasswordKey, "pass");
            Paper.book().write(Prevalent.ParentDB, "user");
        }
    }

    public void searchAcc() {
        String ID = Paper.book().read(Prevalent.UserIdKey);
        String Pass = Paper.book().read(Prevalent.UserPasswordKey);
        String ParentDB = Paper.book().read(Prevalent.ParentDB);

        if (ID != "id" && Pass != "pass" && ParentDB != "user") {
            if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(Pass) && !TextUtils.isEmpty(ParentDB)) {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(ID);
                RootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        nameText.setText(snapshot.child("name").getValue().toString());
                        specialityText.setText(snapshot.child("speciality").getValue().toString());
                        degreeText.setText(snapshot.child("degree").getValue().toString());
                        phoneText.setText(snapshot.child("phone").getValue().toString());
                        emailText.setText(snapshot.child("email").getValue().toString());
                        passText.setText(snapshot.child("password").getValue().toString());

                        showProfileName.setText(snapshot.child("name").getValue().toString());

                        name.setText(snapshot.child("name").getValue().toString());
                        speciality.setText(snapshot.child("speciality").getValue().toString());
                        degree.setText(snapshot.child("degree").getValue().toString());
                        phone.setText(snapshot.child("phone").getValue().toString());
                        email.setText(snapshot.child("email").getValue().toString());
                        password.setText(snapshot.child("password").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    public void makeAllVisible() {
        nameText.setVisibility(View.GONE);
        specialityText.setVisibility(View.GONE);
        degreeText.setVisibility(View.GONE);
        phoneText.setVisibility(View.GONE);
        emailText.setVisibility(View.GONE);
        passText.setVisibility(View.GONE);

        updateProfile.setVisibility(View.GONE);
        goBackProfile.setVisibility(View.GONE);


        name.setVisibility(View.VISIBLE);
        speciality.setVisibility(View.VISIBLE);
        degree.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);

        saveUpdatedProfile.setVisibility(View.VISIBLE);
        cancelUpdating.setVisibility(View.VISIBLE);
    }

    public void makeAllGone() {
        nameText.setVisibility(View.VISIBLE);
        specialityText.setVisibility(View.VISIBLE);
        degreeText.setVisibility(View.VISIBLE);
        phoneText.setVisibility(View.VISIBLE);
        emailText.setVisibility(View.VISIBLE);
        passText.setVisibility(View.VISIBLE);

        updateProfile.setVisibility(View.VISIBLE);
        goBackProfile.setVisibility(View.VISIBLE);

        name.setVisibility(View.GONE);
        speciality.setVisibility(View.GONE);
        degree.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);

        saveUpdatedProfile.setVisibility(View.GONE);
        cancelUpdating.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DoctorProfileActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
        finish();
    }
}
