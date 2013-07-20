package com.buaa.greenlife.views.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.buaa.greenlife.R;
import com.buaa.greenlife.views.custom.VegeCustomListAdapter;
import com.google.gson.Gson;


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
    	
    	//json dump with GSON
    	String jsonString = null;
    	
    	Gson gson = new Gson();

    	Veges[] veges = gson.fromJson(jsonString,  Veges[].class);
    	
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
    
    static class Veges{
        String logo;
        String id;
        String title;
        String baidu_info;
        String in_season_time;
        String like_users;
        String sellers;
        
        String getLogo() { return logo; };
        String getID() { return id; };
        String getTitle() { return title; };
        String getBaidu_info() { return baidu_info; };
        String getIn_season_time() { return in_season_time; };
        String getLike_users() { return like_users; };
        String getSellers() { return sellers; };
        
    }
    
}





