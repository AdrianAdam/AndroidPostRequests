package com.example.adrianadam.aplicatieab4systems.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.adrianadam.aplicatieab4systems.Model.MainPageSpot;
import com.example.adrianadam.aplicatieab4systems.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<MainPageSpot> implements View.OnClickListener {

    private ArrayList<MainPageSpot> dataSet;
    Context context;

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        MainPageSpot spotModel = (MainPageSpot) object;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtCountry;
        Button btnFavorite;
    }

    public ListAdapter(ArrayList<MainPageSpot> data, Context context) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainPageSpot dataSpot = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.text_city);
            viewHolder.txtCountry = (TextView) convertView.findViewById(R.id.text_country);
            viewHolder.btnFavorite = (Button) convertView.findViewById(R.id.btnMainFavorite);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(dataSpot.getName());
        viewHolder.txtCountry.setText(dataSpot.getCountry());
        viewHolder.btnFavorite.setOnClickListener(this);
        viewHolder.btnFavorite.setTag(position);

        return convertView;
    }
}
