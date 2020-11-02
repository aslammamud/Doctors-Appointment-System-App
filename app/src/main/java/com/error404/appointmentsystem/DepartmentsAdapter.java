package com.error404.appointmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentsAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    String[] department_names_array;
    List<String> backup;
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filtereddata = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filtereddata.addAll(backup);
            } else {
                for (String obj : backup) {
                    if (obj.contains(charSequence.toString())) {
                        filtereddata.add(obj);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtereddata;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<String> mainArray = new ArrayList<String>();
            Collections.addAll(mainArray, department_names_array);
            department_names_array = new String[department_names_array.length];
            mainArray.clear();
            mainArray.addAll((ArrayList<String>) results.values);
            department_names_array = mainArray.toArray(new String[mainArray.size()]);
            notifyDataSetChanged();
        }
    };
    private LayoutInflater inflater;

    DepartmentsAdapter(Context context, String[] department_names_array) {
        this.context = context;
        this.department_names_array = department_names_array;
        backup = new ArrayList<String>();
        Collections.addAll(backup, department_names_array);
    }

    @Override
    public int getCount() {
        return department_names_array.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_departments_adapter, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.deptName);

        textView.setText(department_names_array[position]);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
