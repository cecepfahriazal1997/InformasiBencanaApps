package com.informasi.bencana.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.app.FormNeedAssesmentActivity;
import com.informasi.bencana.model.ModelNeedAssesment;
import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class NeedAssesmentAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<ModelNeedAssesment> items;
    private ModelNeedAssesment item;
    private FunctionHelper helper;
    private ApiService apiService;
    private String urlDelete;

    ViewHolder holder;

    public NeedAssesmentAdapter(Activity context, List<ModelNeedAssesment> items, FunctionHelper helper,
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
            convertView         = inflater.inflate(R.layout.item_need_assesment, null);
            holder              = new ViewHolder();
            holder.Need         = (TextView) convertView.findViewById(R.id.need);
            holder.Date         = (TextView) convertView.findViewById(R.id.date);
            holder.Place        = (TextView) convertView.findViewById(R.id.place);
            holder.MedicalEquipment = (TextView) convertView.findViewById(R.id.medical_equipment);
            holder.Edit         = (FancyButton) convertView.findViewById(R.id.btnEdit);
            holder.Delete       = (FancyButton) convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModelNeedAssesment item = items.get(position);

        holder.Need.setText(item.getService());
        holder.Date.setText(item.getDate());
        holder.Place.setText("Place : " + item.getPlace());
        holder.MedicalEquipment.setText("Medical Equipment : " + item.getMedicalE());

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("title", "Update Data");
                param.put("type", "update");
                param.put("id", item.getId());
                param.put("location", item.getLocation());
                param.put("date", item.getDate());
                param.put("cases", item.getCases());
                param.put("diseases", item.getDiseases());
                param.put("service", item.getService());
                param.put("place", item.getPlace());
                param.put("medicalE", item.getMedicalE());
                param.put("supportingE", item.getSupportingE());
                param.put("drug", item.getDrug());
                param.put("medicalS", item.getMedicalS());
                param.put("nmedicalS", item.getNmedicalS());
                param.put("general", item.getGeneral());
                param.put("specialistD", item.getSpecialistD());
                param.put("nurse", item.getNurse());
                param.put("nmedical", item.getNmedical());
                param.put("ambulance", item.getAmbulance());
                param.put("relateDtr", item.getRelateDtr());
                param.put("com", item.getCom());
                param.put("nameLocation", item.getLocationLabel());
                helper.startIntent(FormNeedAssesmentActivity.class, false, false, param);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog   = new Dialog(context);
                String title    = "Are you sure ?";
                String message  = "You will delete data patient " + item.getService() + " !";
                View.OnClickListener positive = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apiService.deleteAssesmen(urlDelete, item.getId(),
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
        TextView Need, Date, Place, MedicalEquipment;
        FancyButton Edit, Delete;
    }

    public List<ModelNeedAssesment> getData() {
        return items;
    }
}