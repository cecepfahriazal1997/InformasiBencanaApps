package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.model.DisasterModel;
import com.informasi.bencana.other.FunctionHelper;

import java.util.List;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class DisasterAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<DisasterModel> items;
    private DisasterModel item;
    private FunctionHelper functionHelper;

    ViewHolder holder;

    public DisasterAdapter(Activity context, List<DisasterModel> items) {
        this.context = context;
        this.items = items;
        this.functionHelper = new FunctionHelper(context);
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
            convertView         = inflater.inflate(R.layout.item_disaster, null);
            holder              = new ViewHolder();
            holder.Title        = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DisasterModel item = items.get(position);

        holder.Title.setText(item.getTitle());

        return convertView;
    }

    static class ViewHolder {
        TextView Title;
    }

    public List<DisasterModel> getData() {
        return items;
    }
}