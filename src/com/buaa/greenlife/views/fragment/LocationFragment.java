package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.buaa.greenlife.R;

/**
 * Created by QisenTang on 13-7-20.
 */
public class LocationFragment extends BaseFragment{
    public LocationFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.test;
    }

    @Override
    protected void onInflated() {
        Log.d("May", "on Inflated");
    }
}
