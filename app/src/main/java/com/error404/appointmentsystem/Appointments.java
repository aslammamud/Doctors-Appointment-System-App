package com.error404.appointmentsystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;

public class Appointments extends AppCompatActivity {
    DatePickerDialog DatePicker;
    private RecyclerView AppointmentRecylcerview;
    private AppointmentsAdapter adapter;
    private DoctorsItem user;
    private List<AppointmentItem> items;
    private Button goBackButton;
    private ImageButton searchButton;
    private EditText selectAppointmentDate;
    private RadioGroup radioAppointmentTTSlot;
    private RadioButton radio;
    private String Date, Time;
    private DatabaseReference myRef;
    private Query query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        Paper.init(this);

        final String Docid = Paper.book().read(Prevalent.UserIdKey);

        radioAppointmentTTSlot = findViewById(R.id.radioAppointmentTTSlot);

        searchButton = findViewById(R.id.searchButton);
        goBackButton = findViewById(R.id.goBackButton);
        AppointmentRecylcerview = findViewById(R.id.appointmentSerialRecylcerview);
        AppointmentRecylcerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        AppointmentRecylcerview.setLayoutManager(layoutManager);
        items = new ArrayList<AppointmentItem>();


        selectAppointmentDate = findViewById(R.id.selectAppointmentDate);
        selectAppointmentDate.setInputType(InputType.TYPE_NULL);
        selectAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePicker = new DatePickerDialog(Appointments.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(Calendar.YEAR, year);
                                calendar1.set(Calendar.MONTH, monthOfYear);
                                calendar1.set(Calendar.DATE, dayOfMonth);

                                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                                selectAppointmentDate.setText(dateCharSequence);

                            }
                        }, year, month, day);
                DatePicker.show();
            }
        });

        selectAppointmentDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Date = selectAppointmentDate.getText().toString();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rb = radioAppointmentTTSlot.getCheckedRadioButtonId();
                radio = findViewById(rb);
                Time = radio.getText().toString();
                Toast.makeText(Appointments.this, "Time: " + Time + "   Date: " + Date, Toast.LENGTH_SHORT).show();

                query = FirebaseDatabase.getInstance().getReference("Appointments")
                        .child(Date).child(Time).orderByChild("doctorid").equalTo(Docid);

                getmyQueryResults();

            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getmyQueryResults() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentItem DataItem = snapshot.getValue(AppointmentItem.class);
                    items.add(DataItem);
                }
                adapter = new AppointmentsAdapter(getApplicationContext(), items);
                AppointmentRecylcerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
