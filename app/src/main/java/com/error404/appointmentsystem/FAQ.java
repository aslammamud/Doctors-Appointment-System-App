package com.error404.appointmentsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FAQ extends AppCompatActivity {
    private ImageButton goBackCommon;
    private ListView listView;
    private TextView ListType;
    private CommonListAdapter adapter;
    private String[] emergency_list_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonlist);

        goBackCommon = findViewById(R.id.goBackCommon);
        listView = findViewById(R.id.listViewEmergency);
        ListType = findViewById(R.id.ListType);
        ListType.setText("FAQ");

        emergency_list_array = getResources().getStringArray(R.array.faqs_array);

        adapter = new CommonListAdapter(FAQ.this, emergency_list_array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.list_array[position];
            }
        });

        goBackCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
