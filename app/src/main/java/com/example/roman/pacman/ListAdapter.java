package com.example.roman.pacman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    ArrayList<String> arrayList = new ArrayList<>();
    Context context;
    private int layout;
    private class ViewHolder
    {
        TextView name;
        TextView score;

    }

    public ListAdapter(Context _context, int _layout, ArrayList<String> _arrayList)
    {
        context = _context;
        layout = _layout;
        arrayList = _arrayList;
    }
    @Override
    public int getCount() {
       return arrayList.size();
    }

    @Override
    public java.lang.Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            viewHolder.name = (TextView) row.findViewById(R.id.textView3);
            viewHolder.score = (TextView) row.findViewById(R.id.textView2);

            row.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) row.getTag();

        }
        String[] items = getItem(position).toString().split("-");
        viewHolder.name.setText(items[0]);
        viewHolder.score.setText("Skóre:" + items[1]);

        return row;

    }
}
