package com.informasi.bencana.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.app.FormHistoryActivity;
import com.informasi.bencana.app.FormPatientActivity;
import com.informasi.bencana.app.FormProgressActivity;
import com.informasi.bencana.model.PatientModel;
import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class PatientAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<PatientModel> items;
    private PatientModel item;
    private FunctionHelper helper;
    private ApiService apiService;
    private String urlDelete;

    ViewHolder holder;

    public PatientAdapter(Activity context, List<PatientModel> items, FunctionHelper helper,
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
            convertView         = inflater.inflate(R.layout.item_patient, null);
            holder              = new ViewHolder();
            holder.Id           = (TextView) convertView.findViewById(R.id.id);
            holder.Name         = (TextView) convertView.findViewById(R.id.name);
            holder.Doctor       = (TextView) convertView.findViewById(R.id.doctor);
            holder.Gender       = (TextView) convertView.findViewById(R.id.gender);
            holder.Age          = (TextView) convertView.findViewById(R.id.age);
            holder.Edit         = (FancyButton) convertView.findViewById(R.id.btnEdit);
            holder.Delete       = (FancyButton) convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientModel item = items.get(position);

        holder.Id.setText(item.getId());
        holder.Name.setText(item.getName());
        holder.Doctor.setText("Doctor : " + item.getDoctor());
        holder.Gender.setText("Gender : " + (item.getGender().equals("0") ? "Laki-laki" : "Perempuan"));
        holder.Age.setText("Age : " + item.getAge() + " tahun");

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionsDialog(item);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog   = new Dialog(context);
                String title    = "Are you sure ?";
                String message  = "You will delete data patient " + item.getName() + " !";
                View.OnClickListener positive = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apiService.deletePatient(urlDelete, item.getId(),
                                new ApiService.hashMapListener() {
                                    @Override
                                    public String getHashMap(Map<String, String> hashMap) {
                                        if (hashMap.get("success").equals("1")) {
                                            items.remove(position);
                                            notifyDataSetChanged();
                                        } else {
                                            helper.showToast(hashMap.get("message"), 0);
                                        }
                                        return null;
                                    }
                                });
                    }
                };

                View.OnClickListener negative = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                };

                helper.popupNotification(dialog, R.drawable.confirmation,
                        title, message, positive, negative, false);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView Id, Name, Doctor, Gender, Age;
        FancyButton Edit, Delete;
    }

    private void showActionsDialog(final PatientModel item) {
        CharSequence colors[] = new CharSequence[]{"Edit", "History", "Progress"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Map<String, String> param = new HashMap<>();
                    param.put("type", "update");
                    param.put("title", "Edit Patient");
                    param.put("id", item.getId());
                    param.put("name", item.getName());
                    param.put("gender", item.getGender());
                    param.put("age", item.getAge());
                    param.put("location", item.getLocation());
                    param.put("locationLabel", item.getLocationLabel());
                    param.put("date", item.getDate());
                    param.put("weaknessCondition", item.getWeaknessCondition());
                    param.put("threadCondition", item.getThreadCondition());
                    param.put("doctorName", item.getDoctor());
                    param.put("nurseName", item.getNurse());
                    param.put("supportName", item.getSupport());
                    param.put("remark", item.getRemark());
                    param.put("phoneDoctor", item.getPhoneDoctor());
                    param.put("emailDoctor", item.getEmailDoctor());
                    helper.startIntent(FormPatientActivity.class, false, false, param);
                } else if (which == 1) {
                    Map<String, String> param = new HashMap<>();
                    param.put("title", "History Patient");
                    param.put("id", item.getId());
                    param.put("name", item.getName());
                    param.put("gender", item.getGender());
                    param.put("age", item.getAge());
                    helper.startIntent(FormHistoryActivity.class, false, false, param);
                } else {
                    Map<String, String> param = new HashMap<>();
                    param.put("title", "Progress Patient");
                    param.put("type", "add");
                    param.put("id", item.getId());
                    param.put("name", item.getName());
                    param.put("gender", item.getGender());
                    param.put("age", item.getAge());
                    helper.startIntent(FormProgressActivity.class, false, false, param);
                }
            }
        });
        builder.show();
    }

    public List<PatientModel> getData() {
        return items;
    }
}