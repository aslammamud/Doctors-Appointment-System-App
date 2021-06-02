package com.error404.appointmentsystem;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CommonListAdapter extends BaseAdapter {
    private final Context context;
    String[] list_array;
    List<String> backup;

    private LayoutInflater inflater;

    CommonListAdapter(Context context, String[] emergency_list_array) {
        this.context = context;
        this.list_array = emergency_list_array;
    }

    @Override
    public int getCount() {
        return list_array.length;
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
            convertView = inflater.inflate(R.layout.activity_commonlist_adapter, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.emergencyContact);

        textView.setText(Html.fromHtml(list_array[position]));

        return convertView;
    }

}
