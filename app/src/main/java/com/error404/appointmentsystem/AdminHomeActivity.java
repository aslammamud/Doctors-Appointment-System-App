package com.error404.appointmentsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity {

    private ImageButton adddocButton, removedocButton, historyButton, reviewButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Paper.init(this);

        adddocButton = findViewById(R.id.adddocButton);
        removedocButton = findViewById(R.id.removedocButton);
        historyButton = findViewById(R.id.historyButton);
        reviewButton = findViewById(R.id.adminreviewButton);


        adddocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AddDoctors.class);
                startActivity(intent);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, Reviews.class);
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
                        startActivity(new Intent(AdminHomeActivity.this, HomeActivity.class));
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
