package com.buaa.greenlife.views.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.greenlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QisenTang on 13-7-20.
 */
public class SlideDrawerAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context context;

    private List<SimpleItem> items = new ArrayList<SimpleItem>();

    public void setData(List<SimpleItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public SlideDrawerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final SlideItem item;
        if (convertView == null){
            item = new SlideItem();
        } else {
            item = (SlideItem)convertView.getTag();
        }
        item.textView.setText(items.get(i).getTitle());
        item.imageView.setImageResource(items.get(i).getDrawableId());
        return item.contentView;
    }

    class SlideItem {
        ImageView imageView;
        TextView textView;
        View contentView;

        SlideItem() {
            inflater = LayoutInflater.from(context);
            contentView = inflater.inflate(R.layout.drawer_list_item,null);
            imageView = (ImageView)contentView.findViewById(R.id.slide_icon);
            textView = (TextView)contentView.findViewById(R.id.slide_text);
            contentView.setTag(this);
        }
    }
}
