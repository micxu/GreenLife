package com.buaa.greenlife.views.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.buaa.greenlife.R;
import com.buaa.greenlife.thread.GetVegeListThread;
import com.buaa.greenlife.thread.GetVegeListThread.GetVegeListHandler;
import com.buaa.greenlife.thread.GetVegeListThread.GetVegeListListener;
import com.buaa.greenlife.views.custom.VegeCustomListAdapter;


/**
 * Created by QisenTang on 13-7-20.
 */
public class VegetableListFragment extends BaseFragment implements GetVegeListListener{
	
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
    	
    	File file = new File(context.getCacheDir(), "VegeListJSON");
    	if(!file.exists())
    	{
    		GetVegeListThread.GetVegeListHandler handler = new GetVegeListHandler(this);
    		GetVegeListThread vegeListThread = new GetVegeListThread(handler);
    		vegeListThread.start();
    	}
    	
    	// Click event for single list row
        vegeList.setOnItemClickListener(new OnItemClickListener() {

        @Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
							
			}
		});	
    	
    }
  
	@Override
	public void getVegeListSuccessed(String json) {
		// TODO Auto-generated method stub
		//Save
		String filename = "VegeListJSON";

		try {
	        @SuppressWarnings("unused")
			File file = File.createTempFile(filename, null, context.getCacheDir());
		} catch (Exception e) {
		  e.printStackTrace();
		}
		//JSON DUMP
		try{
			JSONObject jsonObject = new JSONObject(json);
			Iterator keys = jsonObject.keys();
			HashMap<String, String> map = new HashMap<String, String>();
			
			int count = jsonObject.getInt("count");
			JSONArray jArray = jsonObject.getJSONArray("results");
			
			
			
			while(keys.hasNext()){
				String key = (String) keys.next();
				map.put(key, jsonObject.getString(key));
			}
			
		    vegeDataList.add(map);
		    		    
		    adapter = new VegeCustomListAdapter(context, vegeDataList);
		    vegeList.setAdapter(adapter);
		    
		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	@Override
	public void getVegeListFailed() {
		// TODO Auto-generated method stub
		
	}
    
}





