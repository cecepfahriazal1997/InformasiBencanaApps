package com.informasi.bencana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.informasi.bencana.R;
import com.informasi.bencana.model.NewsModel;

import java.util.List;

/**
 * Created by Cecep Rokani on 3/18/2019.
 */
public class NewsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsModel> items;
    private NewsModel item;

    ViewHolder holder;

    public NewsAdapter(Context context, List<NewsModel> items) {
        this.context = context;
        this.items = items;
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
            convertView         = inflater.inflate(R.layout.item_news, null);
            holder              = new ViewHolder();
            holder.Title        = (TextView) convertView.findViewById(R.id.title);
            holder.Description  = (TextView) convertView.findViewById(R.id.description);
            holder.Dates        = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsModel item = items.get(position);

        holder.Title.setText(item.getTitle());
        holder.Dates.setText(item.getDate());
        holder.Description.setText(item.getDescription());

        return convertView;
    }

    static class ViewHolder {
        TextView Title, Description, Dates;
    }

    public List<NewsModel> getData() {
        return items;
    }
}