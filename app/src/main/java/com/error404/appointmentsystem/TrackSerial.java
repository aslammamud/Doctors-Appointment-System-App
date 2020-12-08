package com.error404.appointmentsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TrackSerial extends AppCompatActivity {

    DatePickerDialog DatePicker;
    private Button trackSerialButton;
    private EditText selectDateTrack;
    private RadioGroup radioTimeTrackSlot;
    private RadioButton radioTimeButtonTrack;
    DatabaseReference reference, referencedoc;
    private ArrayList<String> departments, doctors;
    private ArrayAdapter<String> deptadapter, doctadapter;
    private Spinner departmentSpinnerTrack, doctorSpinnerTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_serial);

        reference = FirebaseDatabase.getInstance().getReference("Doctors");

        departmentSpinnerTrack = findViewById(R.id.departmentSpinnerTrack);
        departments = new ArrayList<>();
        deptadapter = new ArrayAdapter<String>(TrackSerial.this, R.layout.simple_spinner_dropdown_item, R.id.simpleSpinView, departments);
        getdepartmentslist();
        departmentSpinnerTrack.setAdapter(deptadapter);

        departmentSpinnerTrack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String deptname = departmentSpinnerTrack.getSelectedItem().toString();

                referencedoc = FirebaseDatabase.getInstance().getReference("Doctors").child(deptname);
                doctorSpinnerTrack = findViewById(R.id.doctorSpinnerTrack);
                doctors = new ArrayList<>();
                doctadapter = new ArrayAdapter<String>(TrackSerial.this, R.layout.simple_spinner_dropdown_item, R.id.simpleSpinView, doctors);
                getdoctorslist();
                doctorSpinnerTrack.setAdapter(doctadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        trackSerialButton = findViewById(R.id.trackSerialButton);

        selectDateTrack = findViewById(R.id.selectDateTrack);
        selectDateTrack.setInputType(InputType.TYPE_NULL);
        selectDateTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePicker = new DatePickerDialog(TrackSerial.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(Calendar.YEAR, year);
                                calendar1.set(Calendar.MONTH, monthOfYear);
                                calendar1.set(Calendar.DATE, dayOfMonth);

                                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", calendar1);
                                selectDateTrack.setText(dateCharSequence);

                            }
                        }, year, month, day);
                DatePicker.show();
            }
        });

        trackSerialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioTimeTrackSlot = findViewById(R.id.radioTimeTrackSlot);
                Integer radioTimeSlotid = radioTimeTrackSlot.getCheckedRadioButtonId();
                radioTimeButtonTrack = findViewById(radioTimeSlotid);

                String datetrack = selectDateTrack.getText().toString();
                String timetrack = radioTimeButtonTrack.getText().toString();
                String depttrack = departmentSpinnerTrack.getSelectedItem().toString();
                String docttrack = doctorSpinnerTrack.getSelectedItem().toString();

                Intent intent = new Intent(TrackSerial.this, TrackSerialList.class);
                intent.putExtra("DateTrack", datetrack);
                intent.putExtra("TimeTrack", timetrack);
                intent.putExtra("DeptTrack", depttrack);
                intent.putExtra("DoctTrack", docttrack);
                finish();
                startActivity(intent);
            }
        });


    }

    public void getdepartmentslist() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    departments.add(snapshot.getKey());
                }
                deptadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getdoctorslist() {
        referencedoc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    doctors.add(snapshot.getKey());
                }
                doctadapter.notifyDataSetChanged();
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
