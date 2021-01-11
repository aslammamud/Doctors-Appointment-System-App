package com.error404.appointmentsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Emergency extends AppCompatActivity {
    private ListView listView;
    private TextView ListType;
    private CommonListAdapter adapter;
    private String[] emergency_list_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonlist);

        listView = findViewById(R.id.listViewEmergency);
        ListType = findViewById(R.id.ListType);
        ListType.setText("Emergency");

        emergency_list_array = getResources().getStringArray(R.array.emergency_list_array);

        adapter = new CommonListAdapter(Emergency.this, emergency_list_array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.list_array[position];
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
