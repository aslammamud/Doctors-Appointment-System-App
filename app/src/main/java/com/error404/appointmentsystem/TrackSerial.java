package com.error404.appointmentsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TrackSerial extends AppCompatActivity {

    DatePickerDialog DatePicker;
    private Button trackSerialButton;
    private EditText selectDateTrack;
    private RadioGroup radioTimeTrackSlot;
    private RadioButton radioTimeButtonTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_serial);

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

                Intent intent = new Intent(TrackSerial.this, TrackSerialList.class);
                intent.putExtra("DateTrack", datetrack);
                intent.putExtra("TimeTrack", timetrack);
                finish();
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
