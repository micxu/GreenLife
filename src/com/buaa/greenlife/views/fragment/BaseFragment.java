package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.buaa.greenlife.R;
import com.buaa.greenlife.util.AsyncInflater;

import java.lang.ref.WeakReference;

/**
 * Created by QisenTang on 13-7-20.
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;

    protected View contentView;

    private FrameLayout containerView;

    private LayoutInflater inflater;

    protected Handler handler;

    protected abstract int getAsyncInitViewResId();

    public abstract void handleMessage(Message msg);

    protected LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

    private InflateListener inflateListener = new InflateListener() {
        @Override
        public void onInflatedView(View view) {
            contentView = view;
            containerView.addView(contentView, defaultLayoutParams);
            doInflated();
        }
    };

    private void doInflated() {
        onInflated();
        handler.sendEmptyMessage(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(getAsyncInitViewResId(),container,false);
        onInflated();
        return contentView;
    }

    protected abstract void onInflated();

    public BaseFragment(Context context, Handler handler) {
        if (context == null || handler == null){
            throw new NullPointerException("context and handler can't be null");
        }
        this.handler = handler;
        this.context = context;
        initView();
    }

    private void initView(){
        inflater = LayoutInflater.from(context);
        containerView = (FrameLayout)inflater.inflate(R.layout.null_frame, null);
        int layoutId = getAsyncInitViewResId();
        if (layoutId > 0) {
            asyncInitView(layoutId);
        }
    }

    private void asyncInitView(int layoutId) {
        AsyncInflater.getInstance().asyncInflate(LayoutInflater.from(context),
                layoutId, new WeakReference<Handler>(handler),
                new WeakReference<InflateListener>(inflateListener));
    }

    public View getContentView(){
        return contentView;
    }

}
