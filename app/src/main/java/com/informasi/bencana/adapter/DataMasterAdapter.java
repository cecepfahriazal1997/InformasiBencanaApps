package com.informasi.bencana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.model.DataMasterModel;

import java.util.List;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class DataMasterAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<DataMasterModel> items;
    private DataMasterModel item;

    ViewHolder holder;

    public DataMasterAdapter(Context context, List<DataMasterModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView         = inflater.inflate(R.layout.item_data_master, null);
            holder              = new ViewHolder();
            holder.Title        = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DataMasterModel item = items.get(position);

        holder.Title.setText(item.getTitle());
        return convertView;
    }

    static class ViewHolder {
        TextView Title;
    }

    public List<DataMasterModel> getData() {
        return items;
    }
}