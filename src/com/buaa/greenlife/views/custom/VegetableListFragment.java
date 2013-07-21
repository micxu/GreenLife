package com.buaa.greenlife.views.custom;

import com.buaa.greenlife.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.buaa.greenlife.views.fragment.*;

public class VegetableListFragment extends BaseFragment {
	
	private View contentView = super.contentView;
	
	public VegetableListFragment(Context context, Handler handler) {
		super(context, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getAsyncInitViewResId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_vegetablelist;
	}

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
	protected void onInflated() {
		// TODO draw content view with swipe tabs
	}

}
