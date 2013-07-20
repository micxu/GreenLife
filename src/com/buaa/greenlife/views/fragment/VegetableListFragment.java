package com.buaa.greenlife.views.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.buaa.greenlife.R;
import com.buaa.greenlife.views.custom.VegeCustomListAdapter;

/**
 * Created by QisenTang on 13-7-20.
 */
public class VegetableListFragment extends BaseFragment {
	
	ListView vegeList = null;
	VegeCustomListAdapter adapter = null;
	ArrayList<HashMap<String, String>> vegeDataList = null;
	
    public VegetableListFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.fragment_vegetablelist;
    }

    @Override
    protected void onInflated() {
    	vegeList = (ListView)contentView.findViewById(R.id.vegeList);
    	vegeDataList = new ArrayList<HashMap<String, String>>();
    	
    	//Trying to fetch data & assign it
    	
    	
    	adapter = new VegeCustomListAdapter(context, vegeDataList);
    	vegeList.setAdapter(adapter);
    	
    	// Click event for single list row
        vegeList.setOnItemClickListener(new OnItemClickListener() {

        @Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
							
			}
		});	
    	
    }
}
