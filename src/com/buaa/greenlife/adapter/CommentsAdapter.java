package com.buaa.greenlife.adapter;

import java.util.ArrayList;
import java.util.List;

import com.buaa.greenlife.bean.Comments;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommentsAdapter extends BaseAdapter{

    private Context mContext;
    private Handler mHandler;
    private List<Comments> mComments;
    
    public CommentsAdapter(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mComments = new ArrayList<Comments>();
    }
    
    public void setData(List<Comments> mComments){
        this.mComments = mComments;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        if (mComments != null){
            return mComments.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        if (mComments != null){
            return mComments.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        CommentsItem item;
        if ( convertView == null || convertView.getTag() == null){
            item = new CommentsItem(mContext);
        } else{
            item = (CommentsItem)convertView.getTag();
        }
        
        final Comments comments = mComments.get(position);
        item.setName(comments.getmName());
        item.setDescription(comments.getmDescription());
        item.setTime(comments.getmTime());
        item.setPoints(comments.getmPoints());
        
        return item.getView();
    }

}
