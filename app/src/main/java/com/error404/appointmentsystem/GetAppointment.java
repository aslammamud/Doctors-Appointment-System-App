package com.error404.appointmentsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class GetAppointment extends AppCompatActivity {

    DatePickerDialog DatePicker;
    TimePickerDialog TimePicker;
    private EditText selectDate, selectTimeSlot, nameOfPatient, ageOfPatient, genderOfPaitent, bloodGroupOfPaitent, symptompsOfPatient, phoneOfPatient, addressOfPatient;
    private Button bookAppointment, cancelAppointment;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getappointment);

        myRef = FirebaseDatabase.getInstance().getReference("Appointments");

        selectDate = findViewById(R.id.selectDate);
        selectTimeSlot = findViewById(R.id.selectTimeSlot);
        nameOfPatient = findViewById(R.id.nameOfPatient);
        ageOfPatient = findViewById(R.id.ageOfPaitent);
        genderOfPaitent = findViewById(R.id.genderOfPaitent);
        bloodGroupOfPaitent = findViewById(R.id.bloodGroupOfPaitent);
        symptompsOfPatient = findViewById(R.id.symptompsOfPatient);
        phoneOfPatient = findViewById(R.id.phoneOfPatient);
        addressOfPatient = findViewById(R.id.addressOfPatient);

        final String DoctorName = getIntent().getStringExtra("DoctorName");
        final String DoctorID = getIntent().getStringExtra("DoctorID");
        //Toast.makeText(GetAppointment.this,DoctorName,Toast.LENGTH_SHORT).show();
        //Toast.makeText(GetAppointment.this,DoctorID,Toast.LENGTH_SHORT).show();

        bookAppointment = findViewById(R.id.bookAppointment);
        cancelAppointment = findViewById(R.id.cancelAppointment);

        selectDate.setInputType(InputType.TYPE_NULL);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePicker = new DatePickerDialog(GetAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(Calendar.YEAR, year);
                                calendar1.set(Calendar.MONTH, monthOfYear);
                                calendar1.set(Calendar.DATE, dayOfMonth);

                                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                                selectDate.setText(dateCharSequence);

                            }
                        }, year, month, day);
                DatePicker.show();
            }
        });

        selectTimeSlot.setInputType(InputType.TYPE_NULL);
        selectTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                boolean is24HourFormat = DateFormat.is24HourFormat(GetAppointment.this);

                TimePicker = new TimePickerDialog(GetAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        //selectTimeSlot.setText(hourOfDay + ":" + minute);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR, hour);
                        calendar1.set(Calendar.MINUTE, minute);

                        CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
                        selectTimeSlot.setText(charSequence);
                    }
                }, hour, minute, is24HourFormat);

                TimePicker.show();
            }
        });

        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = selectDate.getText().toString();
                String time = selectTimeSlot.getText().toString();
                String name = nameOfPatient.getText().toString();
                String age = ageOfPatient.getText().toString();
                String gender = genderOfPaitent.getText().toString();
                String bloodgroup = bloodGroupOfPaitent.getText().toString();
                String symptoms = symptompsOfPatient.getText().toString();
                String phone = phoneOfPatient.getText().toString();
                String address = addressOfPatient.getText().toString();

                GetAppointmentItem DataItem = new GetAppointmentItem(name, age, gender, bloodgroup, symptoms, phone, address, date, time, DoctorName, DoctorID);

                myRef.child(date).child(name).setValue(DataItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        makeText(getApplicationContext(), "Your appointment placed successfully!", LENGTH_LONG).show();
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
