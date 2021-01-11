package com.error404.appointmentsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ImageButton deptButton, trackButton, reviewButton, tipsButton, emergencyButton, faqButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        deptButton = findViewById(R.id.deptButton);
        tipsButton = findViewById(R.id.tipsButton);
        reviewButton = findViewById(R.id.reviewButton);
        emergencyButton = findViewById(R.id.emergencyButton);
        faqButton = findViewById(R.id.faqButton);
        trackButton = findViewById(R.id.trackButton);

        deptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Departments.class);
                startActivity(intent);
            }
        });
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrackSerial.class);
                startActivity(intent);
            }
        });
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Reviews.class);
                startActivity(intent);
            }
        });
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Emergency.class);
                startActivity(intent);
            }
        });
        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Tips.class);
                startActivity(intent);
            }
        });
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FAQ.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navDashboardPage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navDashboardPage:
                        return true;
                    case R.id.navAdminPage:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        if (Prevalent.ParentDB.isEmpty() || Prevalent.UserIdKey.isEmpty() || Prevalent.UserPasswordKey.isEmpty()) {
            Paper.book().write(Prevalent.UserIdKey, "id");
            Paper.book().write(Prevalent.UserPasswordKey, "pass");
            Paper.book().write(Prevalent.ParentDB, "user");
        }

        searchAcc();


    }

    public void searchAcc() {
        String ID = Paper.book().read(Prevalent.UserIdKey);
        String Pass = Paper.book().read(Prevalent.UserPasswordKey);
        String ParentDB = Paper.book().read(Prevalent.ParentDB);

        if (ID != "id" && Pass != "pass" && ParentDB != "user") {
            if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(Pass) && !TextUtils.isEmpty(ParentDB)) {
                AllowAccount(ID, ParentDB);
            }
        }
    }

    public void AllowAccount(final String ID, final String ParentDB) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(ParentDB).child(ID).exists()) {

                    if (ParentDB.equals("Admin")) {
                        loadingBar.setMessage("Logging in as Admin...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        Intent intent = new Intent(HomeActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                    } else if (ParentDB.equals("Doctors")) {
                        loadingBar.setMessage("Logging in as Doctor...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        Intent intent = new Intent(HomeActivity.this, DoctorHomeActivity.class);
                        startActivity(intent);
                    }

                } else {

                    loadingBar.dismiss();
//                        Toast.makeText(HomeActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
