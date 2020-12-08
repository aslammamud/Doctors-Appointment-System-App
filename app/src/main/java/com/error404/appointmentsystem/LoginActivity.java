package com.error404.appointmentsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail, userPass;
    private Switch rememberMeSwitch, adminSwitch;
    private Button login, loginCancel;

    private ProgressDialog loadingBar;
    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.userEmail);
        userPass = findViewById(R.id.userPass);
        rememberMeSwitch = findViewById(R.id.rememberMeSwitch);
        adminSwitch = findViewById(R.id.adminSwitch);
        login = findViewById(R.id.login);
        loadingBar = new ProgressDialog(this);
        loginCancel = findViewById(R.id.loginCancel);

        Paper.init(this);
        reference = FirebaseDatabase.getInstance().getReference("Doctors").child("Cardiology").child("Dr Hossain");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(LoginActivity.this, DoctorHomeActivity.class);
                startActivity(intent);*/
                LoginAllow();
            }
        });
        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setText(null);
                userPass.setText(null);
            }
        });

        //Navigation_Starts_here

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navAdminPage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navDashboardPage:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navAdminPage:
                        return true;
                }
                return false;
            }
        });

        //Navigation_Ends_here
    }

    public void LoginAllow() {
        final String UserEmail = userEmail.getText().toString();
        final String UserPass = userPass.getText().toString();

        if (TextUtils.isEmpty(UserEmail)) {
            Toast.makeText(LoginActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(UserPass)) {
            Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccount(UserEmail, UserPass);
        }
    }

    public void AllowAccount(final String UserEmail, final String UserPass) {
        if (rememberMeSwitch.isChecked()) {
            Paper.book().write(Prevalent.UserIdKey, UserEmail);
            Paper.book().write(Prevalent.UserPasswordKey, UserPass);
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eid = snapshot.child("email").getValue().toString();
                String epass = snapshot.child("password").getValue().toString();

                if (UserEmail.equals(eid)) {
                    if (UserPass.equals(epass)) {
                        Intent intent = new Intent(LoginActivity.this, DoctorHomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Employee id incorrect", Toast.LENGTH_SHORT).show();
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
                        LoginActivity.this.finish();
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
