package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.app.ReportDetailActivity;
import com.informasi.bencana.model.ReportModel;
import com.informasi.bencana.other.FunctionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class ReportAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
        private List<ReportModel> items;
    private ReportModel item;
    private FunctionHelper helper;

    ViewHolder holder;

    public ReportAdapter(Activity context, List<ReportModel> items, FunctionHelper helper) {
        this.context = context;
        this.items = items;
        this.helper = helper;
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
            convertView         = inflater.inflate(R.layout.item_patient_progress, null);
            holder              = new ViewHolder();
            holder.Id           = (TextView) convertView.findViewById(R.id.id);
            holder.Name         = (TextView) convertView.findViewById(R.id.name);
            holder.Doctor       = (TextView) convertView.findViewById(R.id.doctor);
            holder.Nurse        = (TextView) convertView.findViewById(R.id.nurse);
            holder.Gender       = (TextView) convertView.findViewById(R.id.gender);
            holder.Age          = (TextView) convertView.findViewById(R.id.age);
            holder.Detail       = (FancyButton) convertView.findViewById(R.id.btnDetail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReportModel item = items.get(position);

        holder.Id.setText(item.getId());
        holder.Name.setText(item.getName());
        holder.Doctor.setText("Doctor : " + item.getDoctor());
        holder.Nurse.setText("Perawat : " + item.getNurse());
        holder.Gender.setText("Gender : " + (item.getGender().equals("0") ? "Laki-laki" : "Perempuan"));
        holder.Age.setText("Age : " + item.getAge() + " tahun");

        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("id", item.getId());
                param.put("name", item.getName());
                param.put("gender", item.getGender());
                param.put("age", item.getAge());
                param.put("nurse", item.getNurse());
                param.put("listProgress", item.getListProgress());
                helper.startIntent(ReportDetailActivity.class, false, false, param);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView Id, Name, Doctor, Nurse, Gender, Age;
        FancyButton Detail;
    }

    public List<ReportModel> getData() {
        return items;
    }
}