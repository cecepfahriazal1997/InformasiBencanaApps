package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.informasi.bencana.R;
import com.informasi.bencana.other.ApiService;

import java.util.ArrayList;

public class KlasifikasiDisasterAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> data = new ArrayList();
    private int layoutResourceId;
    private ApiService apiService;

    static class RecordHolder {
        ImageView imageView;
        RecordHolder() {}
    }

    public KlasifikasiDisasterAdapter(Context context, int layoutResourceId, ArrayList<String> data,
                                ApiService apiService) {
        super(context, layoutResourceId, data);
        this.layoutResourceId   = layoutResourceId;
        this.context            = context;
        this.data               = data;
        this.apiService         = apiService;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder;
        View row = convertView;
        if (row == null) {
            row                 = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);
            holder              = new RecordHolder();
            holder.imageView    = row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        if (!this.data.get(position).isEmpty()) {
            apiService.getImageOnlineImageView(this.data.get(position), holder.imageView);
        }
        holder.imageView.getLayoutParams().height = 500;
        holder.imageView.requestLayout();
        return row;
    }

}