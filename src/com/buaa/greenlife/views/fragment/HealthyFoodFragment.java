package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import com.buaa.greenlife.R;
import com.buaa.greenlife.views.custom.HealthyFoodPageAdapter;

/**
 * Created by QisenTang on 13-7-20.
 */
public class HealthyFoodFragment extends BaseFragment {

    private ViewPager viewPager;
    private HealthyFoodPageAdapter adpater;

    public HealthyFoodFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.healthyfood;
    }

    @Override
    protected void onInflated() {
        viewPager = (ViewPager)contentView.findViewById(R.id.foodList);
        
    }
}
