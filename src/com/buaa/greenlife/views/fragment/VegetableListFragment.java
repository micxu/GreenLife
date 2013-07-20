package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;

import com.buaa.greenlife.R;

/**
 * Created by QisenTang on 13-7-20.
 */
public class VegetableListFragment extends BaseFragment {
	
	ListView vegeList = null;
	
    public VegetableListFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.fragment_vegetablelistrow;
    }

    @Override
    protected void onInflated() {
    	vegeList = (ListView) contentView.findViewById(R.id.vegeList);
    	
    }
}
