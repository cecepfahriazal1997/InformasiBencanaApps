package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.model.TotalPatientModel;

import java.util.ArrayList;

public class TotalPatientAdapter extends ArrayAdapter<TotalPatientModel> {
    private Context context;
    private ArrayList<TotalPatientModel> data = new ArrayList();
    private int layoutResourceId;

    static class RecordHolder {
        TextView country, total;

        RecordHolder() {}
    }

    public TotalPatientAdapter(Context context, int layoutResourceId, ArrayList<TotalPatientModel> data) {
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
            holder.country      = (TextView) row.findViewById(R.id.country);
            holder.total        = (TextView) row.findViewById(R.id.total);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        TotalPatientModel item = (TotalPatientModel) this.data.get(position);
        holder.country.setText(item.getCountry());
        holder.total.setText(item.getTotal() + " Orang");
        return row;
    }
}
