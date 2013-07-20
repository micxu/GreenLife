package com.buaa.greenlife.views.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
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

    protected LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    private InflateListener inflateListener = new InflateListener() {
        @Override
        public void onInflatedView(View view) {
            contentView = view;
            containerView.addView(view, defaultLayoutParams);
            doInflated();
        }
    };

    private void doInflated() {
        onInflated();
    }

    protected abstract void onInflated();

    public BaseFragment(Context context, Handler handler) {
        if (context == null || handler == null){
            throw new NullPointerException("context and handler can't be null");
        }
        this.handler = handler;
        this.context = context;
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

}
