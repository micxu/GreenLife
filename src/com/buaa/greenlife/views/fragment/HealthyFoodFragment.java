package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Handler;

import com.buaa.greenlife.R;

/**
 * Created by QisenTang on 13-7-20.
 */
public class HealthyFoodFragment extends BaseFragment {

    public HealthyFoodFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.null_frame;
    }

    @Override
    protected void onInflated() {

    }
}
