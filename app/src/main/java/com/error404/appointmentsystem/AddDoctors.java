package com.error404.appointmentsystem;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class AddDoctors extends AppCompatActivity implements View.OnClickListener {
    String[] departmentNames;
    private EditText name, id, phone, email, speciality, degree, password;
    private Button addNewDoctor, goBack, chooseImage, removeImage;
    private ImageView imageHolder;
    private Spinner departmentSpinner;
    private Uri imageUri;
    private String extension;
    private DatabaseReference myRef, docIdentityref, historyref;
    private StorageReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoctors);

        myRef = FirebaseDatabase.getInstance().getReference("Departments");
        docIdentityref = FirebaseDatabase.getInstance().getReference("Doctors");
        historyref = FirebaseDatabase.getInstance().getReference("Admin-History");
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
        password = findViewById(R.id.password);
        degree = findViewById(R.id.degree);

        chooseImage.setOnClickListener(this);

        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgremover();
            }
        });

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

                uploadData();
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
        startActivityForResult(intent, 1);
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            //imageHolder.setVisibility(View.VISIBLE);
            imageHolder.setImageURI(imageUri);
            extension = getFileExtension(imageUri);
        }
    }

    public void imgremover() {
        imageUri = Uri.parse("android.resource://com.error404.appointmentsystem/" + R.drawable.doctordefaultimage);
        //String imgPath = imageUri.toString();
        imageHolder.setImageURI(imageUri);
        //imageHolder.setImageResource(R.drawable.doctordefaultimage);
    }

    public void uploadData() {

        final String randomKey = UUID.randomUUID().toString();
        final StorageReference mountainImagesRef = myRef2.child("Doctors/" + randomKey + "." + extension);

        String Name = name.getText().toString();
        final String ID = id.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String Department = departmentSpinner.getSelectedItem().toString();
        String Degree = degree.getText().toString();
        String Speciality = speciality.getText().toString();
        String Image = randomKey + "." + extension;
        String Password = password.getText().toString();

        if (Name.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Name is required!", Toast.LENGTH_SHORT).show();
        } else if (ID.isEmpty()) {
            Toast.makeText(AddDoctors.this, "ID is required!", Toast.LENGTH_SHORT).show();
        } else if (Phone.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Phone is required!", Toast.LENGTH_SHORT).show();
        } else if (Email.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Email is required!", Toast.LENGTH_SHORT).show();
        } else if (Department.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Department is required!", Toast.LENGTH_SHORT).show();
        } else if (Degree.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Degree number is required!", Toast.LENGTH_SHORT).show();
        } else if (Password.isEmpty()) {
            Toast.makeText(AddDoctors.this, "Password is required!", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog pd = new ProgressDialog(AddDoctors.this);
            pd.setTitle("Uploading Picture...");
            pd.show();

            final DoctorsItem DataItem = new DoctorsItem(Name, ID, Email, Phone, Degree, Speciality, Image, Password);
            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            final String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
            final HistoryItem historyItem = new HistoryItem(date, "Doctor Added", ID);

            myRef.child(Department).child(Name).setValue(DataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    docIdentityref.child(ID).setValue(DataItem);
                    historyref.child(date + " " + timeStamp).setValue(historyItem);
                    makeText(getApplicationContext(), "New doctor successfully added!", LENGTH_LONG).show();
                    mountainImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Doctors Picture Uploaded Successfully!", Snackbar.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed To Upload Doctors Picture", Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Progress: " + (int) progressPercent + "%");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    makeText(getApplicationContext(), "Error! Try again", LENGTH_LONG).show();
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
