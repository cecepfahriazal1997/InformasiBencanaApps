package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.model.GridViewModel;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<GridViewModel> {
    private Context context;
    private ArrayList<GridViewModel> data = new ArrayList();
    private int layoutResourceId;

    static class RecordHolder {
        ImageView icon;
        TextView title;

        RecordHolder() {}
    }

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<GridViewModel> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId   = layoutResourceId;
        this.context            = context;
        this.data               = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder;
        View row = convertView;
        if (row == null) {
            row                 = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);
            holder              = new RecordHolder();
            holder.icon         = (ImageView) row.findViewById(R.id.image);
            holder.title        = (TextView) row.findViewById(R.id.title);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        GridViewModel item = (GridViewModel) this.data.get(position);
        holder.icon.setImageResource(item.getImage());
        holder.title.setText(item.getTitle());
        return row;
    }
}
