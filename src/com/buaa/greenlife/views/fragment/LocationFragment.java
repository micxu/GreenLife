package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Handler;

/**
 * Created by QisenTang on 13-7-20.
 */
public class LocationFragment extends BaseFragment{
    public LocationFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return 0;
    }

    @Override
    protected void onInflated() {

    }
}
