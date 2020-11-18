package com.error404.appointmentsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class GetAppointment extends AppCompatActivity {

    DatePickerDialog DatePicker;
    private EditText selectDate, nameOfPatient, ageOfPatient, bloodGroupOfPaitent, symptompsOfPatient, phoneOfPatient, addressOfPatient;
    private RadioGroup radioTimeSlot, genderOfPaitent;
    private RadioButton radioTimeButton, radioSexButton;
    private Button bookAppointment, cancelAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getappointment);

        radioTimeSlot = findViewById(R.id.radioTimeSlot);
        selectDate = findViewById(R.id.selectDate);
        nameOfPatient = findViewById(R.id.nameOfPatient);
        ageOfPatient = findViewById(R.id.ageOfPaitent);
        genderOfPaitent = findViewById(R.id.genderOfPaitent);
        bloodGroupOfPaitent = findViewById(R.id.bloodGroupOfPaitent);
        symptompsOfPatient = findViewById(R.id.symptompsOfPatient);
        phoneOfPatient = findViewById(R.id.phoneOfPatient);
        addressOfPatient = findViewById(R.id.addressOfPatient);

        final String DoctorName = getIntent().getStringExtra("DoctorName");
        final String DoctorID = getIntent().getStringExtra("DoctorID");

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


        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer timeSlotID = radioTimeSlot.getCheckedRadioButtonId();
                radioTimeButton = findViewById(timeSlotID);
                Integer genderID = genderOfPaitent.getCheckedRadioButtonId();
                radioSexButton = findViewById(genderID);

                String date = selectDate.getText().toString();
                String time = radioTimeButton.getText().toString();
                String name = nameOfPatient.getText().toString();
                String age = ageOfPatient.getText().toString();
                String gender = radioSexButton.getText().toString();
                String bloodgroup = bloodGroupOfPaitent.getText().toString();
                String symptoms = symptompsOfPatient.getText().toString();
                String phone = phoneOfPatient.getText().toString();
                String address = addressOfPatient.getText().toString();

                Intent intent = new Intent(GetAppointment.this, GetAppointmentConfirmation.class);
                intent.putExtra("Date", date);
                intent.putExtra("Time", time);
                intent.putExtra("PatientName", name);
                intent.putExtra("Age", age);
                intent.putExtra("Gender", gender);
                intent.putExtra("BloodGroup", bloodgroup);
                intent.putExtra("Symptoms", symptoms);
                intent.putExtra("Phone", phone);
                intent.putExtra("Address", address);
                intent.putExtra("DoctorName", DoctorName);
                intent.putExtra("DoctorID", DoctorID);
                finish();
                startActivity(intent);

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
