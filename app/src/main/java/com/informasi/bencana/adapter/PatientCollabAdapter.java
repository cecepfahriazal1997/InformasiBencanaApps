package com.informasi.bencana.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            holder.Communicate  = convertView.findViewById(R.id.btnCommunicate);

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

        holder.Communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionsDialog(item);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView Id, Name, Doctor, Nurse, Gender, Age;
        FancyButton Detail, Communicate;
//        ImageView Whatsapp, Skype, Zoom;
    }

    public List<PatientCollabModel> getData() {
        return items;
    }


    private void showActionsDialog(final PatientCollabModel item) {
        CharSequence options[] = new CharSequence[]{"Zoom","Skype","Whatsapp"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hubungi Dokter Melalui :");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = context.getPackageManager()
                            .getLaunchIntentForPackage("us.zoom.videomeetings");
                    if (intent == null) {
                        helper.showToast("Silahkan download Aplikasi Zoom untuk memulai video conference dengan Dokter " + item.getDoctor(), 1);
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("market://details?id=" + "us.zoom.videomeetings"));
                        context.startActivity(intent);
                    } else {
                        PackageManager packageManager = context.getPackageManager();

                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("zoomus://"));
                            if (i.resolveActivity(packageManager) != null) {
                                context.startActivity(i);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else if (which == 1) {
                    if (!item.getEmailDoctor().isEmpty() && !item.getEmailDoctor().equals("null")) {
                        try {
                            Intent intent = context.getPackageManager()
                                    .getLaunchIntentForPackage("com.skype.raider");
                            if (intent == null) {
                                helper.showToast("Silahkan download Aplikasi Skype untuk memulai video conference dengan Dokter " + item.getDoctor(), 1);
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("market://details?id=" + "com.skype.raider"));
                                context.startActivity(intent);
                            } else {
                                Intent sky = new Intent("android.intent.action.VIEW");
                                sky.setData(Uri.parse("skype:" + item.getEmailDoctor()));
                                context.startActivity(sky);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        helper.showToast("Email dokter kosong !", 0);
                    }
                } else {
                    if (!item.getPhoneDoctor().isEmpty() && !item.getPhoneDoctor().equals("null")) {
                        Intent intent = context.getPackageManager()
                                .getLaunchIntentForPackage("com.whatsapp");
                        if (intent == null) {
                            helper.showToast("Silahkan download Aplikasi Whatsapp untuk menghubungi Dokter " + item.getDoctor(), 1);
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + "com.whatsapp"));
                            context.startActivity(intent);
                        } else {
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
                        }
                    } else {
                        helper.showToast("Nomor telepon dokter kosong !", 0);
                    }
                }
            }
        });
        builder.show();
    }
}