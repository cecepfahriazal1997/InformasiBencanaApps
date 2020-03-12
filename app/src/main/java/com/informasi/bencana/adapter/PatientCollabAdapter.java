package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.app.CollabActivity;
import com.informasi.bencana.model.PatientCollabModel;
import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class PatientCollabAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<PatientCollabModel> items;
    private PatientCollabModel item;
    private FunctionHelper helper;
    private ApiService apiService;
    private String urlDelete;

    ViewHolder holder;

    public PatientCollabAdapter(Activity context, List<PatientCollabModel> items, FunctionHelper helper,
                                ApiService apiService, String urlDelete) {
        this.context = context;
        this.items = items;
        this.helper = helper;
        this.apiService = apiService;
        this.urlDelete = urlDelete;
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
            convertView         = inflater.inflate(R.layout.item_patient_monitoring, null);
            holder              = new ViewHolder();
            holder.Id           = (TextView) convertView.findViewById(R.id.id);
            holder.Name         = (TextView) convertView.findViewById(R.id.name);
            holder.Doctor       = (TextView) convertView.findViewById(R.id.doctor);
            holder.Nurse        = (TextView) convertView.findViewById(R.id.nurse);
            holder.Gender       = (TextView) convertView.findViewById(R.id.gender);
            holder.Age          = (TextView) convertView.findViewById(R.id.age);
            holder.Detail       = (FancyButton) convertView.findViewById(R.id.btnDetail);
            holder.Whatsapp     = (ImageView) convertView.findViewById(R.id.whatsapp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientCollabModel item = items.get(position);

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
                param.put("title", "List Collaboration Patient");
                param.put("id", item.getId());
                param.put("name", item.getName());
                param.put("gender", item.getGender());
                param.put("age", item.getAge());
                param.put("nurse", item.getNurse());
                helper.startIntent(CollabActivity.class, false, false, param);
            }
        });

        if (!item.getPhoneDoctor().isEmpty()) {
            holder.Whatsapp.setVisibility(View.VISIBLE);
            holder.Whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (helper.isPackageInstalled(context,"com.whatsapp")) {
                        PackageManager packageManager = context.getPackageManager();
                        Intent i = new Intent(Intent.ACTION_VIEW);

                        try {
                            String url = "https://api.whatsapp.com/send?phone="+ item.getPhoneDoctor()
                                    +"&text=" + URLEncoder.encode("Hallo, Dokter " + item.getDoctor(), "UTF-8");
                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(packageManager) != null) {
                                context.startActivity(i);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        helper.popupDialog("Opps", "Silahkan download aplikasi Whatsapp terlebih dahulu !", false);
                    }
                }
            });
        } else {
            holder.Whatsapp.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView Id, Name, Doctor, Nurse, Gender, Age;
        FancyButton Detail;
        ImageView Whatsapp;
    }

    public List<PatientCollabModel> getData() {
        return items;
    }
}