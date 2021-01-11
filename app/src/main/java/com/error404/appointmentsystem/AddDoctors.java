package com.error404.appointmentsystem;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddDoctors extends AppCompatActivity implements View.OnClickListener {
    private static final int IMAGE_REQUEST = 1;
    String[] departmentNames;
    private EditText name, id, phone, email, speciality, degree;
    private Button addNewDoctor, goBack, chooseImage, removeImage;
    private ImageView imageHolder;
    private Spinner departmentSpinner;
    private Uri imageUri;
    private DatabaseReference myRef;
    private StorageReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoctors);

        myRef = FirebaseDatabase.getInstance().getReference("Departments");
        myRef2 = FirebaseStorage.getInstance().getReference("Departments");

        addNewDoctor = findViewById(R.id.addNewDoctor);
        goBack = findViewById(R.id.goBack);
        chooseImage = findViewById(R.id.chooseImage);
        removeImage = findViewById(R.id.removeImage);
        imageHolder = findViewById(R.id.imageHolder);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        speciality = findViewById(R.id.address);
        degree = findViewById(R.id.degree);

        chooseImage.setOnClickListener(this);

        departmentNames = getResources().getStringArray(R.array.department_names_array);
        departmentSpinner = findViewById(R.id.departmentSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, R.id.simpleSpinView, departmentNames);
        departmentSpinner.setAdapter(adapter);


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addNewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef2 = myRef2.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                myRef2.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Map<String, Object> Values = new HashMap<>();
                                Values.put("name", name.getText().toString());
                                Values.put("id", id.getText().toString());
                                Values.put("phone", phone.getText().toString());
                                Values.put("email", email.getText().toString());
                                Values.put("department", departmentSpinner.getSelectedItem().toString());
                                Values.put("degree", degree.getText().toString());
                                Values.put("speciality", speciality.getText().toString());
                                Values.put("imageName", taskSnapshot.getStorage().getDownloadUrl().toString());
                                myRef.child(departmentSpinner.getSelectedItem().toString()).child(id.getText().toString()).setValue(Values);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });

                finish();
                startActivity(getIntent());
                Toast.makeText(AddDoctors.this, "New Doctor Profile Created successfully!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        openFileChooser();
    }

    void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    //image extension pawar jonno eita lekhsi
    public String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageHolder);
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddDoctors.this.finish();
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
