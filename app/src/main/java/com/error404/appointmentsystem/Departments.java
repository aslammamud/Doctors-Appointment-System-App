package com.error404.appointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


public class Departments extends AppCompatActivity {
    private ListView listView;
    private DepartmentsAdapter adapter;
    private String[] department_names_array;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        listView = findViewById(R.id.listViewDept);

        department_names_array = getResources().getStringArray(R.array.department_names_array);

        adapter = new DepartmentsAdapter(this, department_names_array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.department_names_array[position];
                //Toast.makeText(Departments.this,value,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Departments.this, DoctorsList.class);
                intent.putExtra("deptposition", value);
                startActivity(intent);
            }
        });
        searchView = findViewById(R.id.searchViewID);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
