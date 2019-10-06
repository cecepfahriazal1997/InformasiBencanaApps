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
import com.informasi.bencana.app.FormCollabActivity;
import com.informasi.bencana.model.PatientCollabModel;
import com.informasi.bencana.other.ApiService;
import com.informasi.bencana.other.FunctionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class CollabAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<PatientCollabModel> items;
    private PatientCollabModel item;
    private FunctionHelper helper;
    private ApiService apiService;
    private String urlDelete;
    private Map<String, String> paramPatient;

    ViewHolder holder;

    public CollabAdapter(Activity context, List<PatientCollabModel> items, FunctionHelper helper,
                         ApiService apiService, String urlDelete, Map<String, String> paramPatient) {
        this.context = context;
        this.items = items;
        this.helper = helper;
        this.apiService = apiService;
        this.urlDelete = urlDelete;
        this.paramPatient = paramPatient;
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
            convertView         = inflater.inflate(R.layout.item_progress, null);
            holder              = new ViewHolder();
            holder.Year         = (TextView) convertView.findViewById(R.id.year);
            holder.Month        = (TextView) convertView.findViewById(R.id.month);
            holder.Edit         = (FancyButton) convertView.findViewById(R.id.btnEdit);
            holder.Delete       = (FancyButton) convertView.findViewById(R.id.btnDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientCollabModel item = items.get(position);

        holder.Year.setText(item.getDay() + " " + helper.intToMonth(Integer.parseInt(item.getMonth())) + " " + item.getYear());
        holder.Month.setText("Minggu ke : " + item.getWeek());

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> param = new HashMap<>();
                param.put("title", "Edit Collaboration Patient");
                param.put("type", "update");
                param.put("id", paramPatient.get("id"));
                param.put("name", paramPatient.get("name"));
                param.put("gender", paramPatient.get("gender"));
                param.put("age", paramPatient.get("age"));
                param.put("year", item.getYear());
                param.put("month", item.getMonth());
                param.put("week", item.getWeek());
                param.put("day", item.getDay());
                param.put("collabId", item.getCollabId());
                param.put("detailId", item.getDetailId());
                param.put("problem", item.getProblem());
                param.put("recommend", item.getRecommend());
                param.put("collaborativeC", item.getCollaborativeC());
                param.put("collaborativeD", item.getCollaborativeD());
                param.put("feedback", item.getFeedback());
                helper.startIntent(FormCollabActivity.class, false, false, param);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog   = new Dialog(context);
                String title    = "Are you sure ?";
                String message  = "You will delete data history " + item.getYear() + " " + helper.intToMonth(Integer.parseInt(item.getMonth())) + " !";
                View.OnClickListener positive = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        apiService.deleteCollab(urlDelete, item.getCollabId(), item.getDetailId(),
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
        TextView Year, Month;
        FancyButton Edit, Delete;
    }

    public List<PatientCollabModel> getData() {
        return items;
    }
}