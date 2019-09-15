package com.informasi.bencana.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.app.MultiFormatActivity;
import com.informasi.bencana.model.UserGuideModel;
import com.informasi.bencana.other.FunctionHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class UserGuideAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<UserGuideModel> items;
    private UserGuideModel item;
    private FunctionHelper functionHelper;

    ViewHolder holder;

    public UserGuideAdapter(Activity context, List<UserGuideModel> items) {
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
            convertView         = inflater.inflate(R.layout.item_user_guide, null);
            holder              = new ViewHolder();
            holder.Title        = (TextView) convertView.findViewById(R.id.title);
            holder.Description  = (TextView) convertView.findViewById(R.id.description);
            holder.Images       = (ImageView) convertView.findViewById(R.id.image);
            holder.Download     = (Button) convertView.findViewById(R.id.download);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserGuideModel item = items.get(position);

        holder.Title.setText(item.getTitle());
        holder.Description.setText(item.getDescription());

        if (item.getType().equals("pdf"))
            holder.Images.setImageResource(R.drawable.file_pdf);
        else if (item.getType().matches("doc(.*)"))
            holder.Images.setImageResource(R.drawable.file_doc);
        else
            holder.Images.setImageResource(R.drawable.file);

        holder.Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("title", item.getTitle());
                hashMap.put("typeFile", item.getType());
                hashMap.put("content", item.getUrl());
                functionHelper.startIntent(MultiFormatActivity.class, false, false, hashMap);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView Title, Description;
        ImageView Images;
        Button Download;
    }

    public List<UserGuideModel> getData() {
        return items;
    }
}