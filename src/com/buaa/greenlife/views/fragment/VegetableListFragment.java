package com.buaa.greenlife.views.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.buaa.configs.MyHealth;
import com.buaa.greenlife.FoodDetailActivity;
import com.buaa.greenlife.R;
import com.buaa.greenlife.bean.Comments;
import com.buaa.greenlife.network.ImageCache;
import com.buaa.greenlife.thread.GetVegeListThread;
import com.buaa.greenlife.thread.GetVegeListThread.GetVegeListHandler;
import com.buaa.greenlife.thread.GetVegeListThread.GetVegeListListener;
import com.buaa.greenlife.views.custom.VegeCustomListAdapter;


/**
 * Created by QisenTang on 13-7-20.
 */
public class VegetableListFragment extends BaseFragment implements GetVegeListListener {

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

    public void handleMessage(Message message) {
        switch(message.what){
            case MyHealth.Msg.IMG_LOADED_COMPLETED:
                if (adapter != null){
                    Log.d("May","notified");
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onInflated() {
        vegeList = (ListView) contentView.findViewById(R.id.vegeList);
        vegeDataList = new ArrayList<HashMap<String, String>>();

        //Trying to fetch data & assign it

        File file = new File(context.getCacheDir(), "VegeListJSON");
        if (!file.exists()) {
            GetVegeListThread.GetVegeListHandler handler = new GetVegeListHandler(this);
            GetVegeListThread vegeListThread = new GetVegeListThread(handler);
            vegeListThread.start();
        }

        // Click event for single list row
        vegeList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Intent to Laucnh Activity
                Intent intent = new Intent(context, FoodDetailActivity.class);

                //intent.putExtra("in_season_time", vegeDataList.get(position).get("in_season_time"));
                intent.putExtra("like_users", "30");
                intent.putExtra("in_season_time", vegeDataList.get(position).get("in_season_time"));
                intent.putExtra("logo", vegeDataList.get(position).get("logo").toString());
                intent.putExtra("sellers", vegeDataList.get(position).get("sellers").toString());
                intent.putExtra("id", vegeDataList.get(position).get("id").toString());
                intent.putExtra("title", vegeDataList.get(position).get("title").toString());
                intent.putExtra("baidu_info", vegeDataList.get(position).get("baidu_info").toString());
                intent.putExtra("like_users_count", vegeDataList.get(position).get("like_users_count").toString());

                startActivity(intent);
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
        try {
            JSONObject jsonObject = new JSONObject(json);
            int count = jsonObject.getInt("count");
            JSONArray jArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < count; i++) {
                HashMap<String, String> map = new HashMap<String, String>();

                JSONObject jObject = (JSONObject) jArray.get(i);

                map.put("in_season_time", jObject.getString("in_season_time"));
                map.put("logo", jObject.getString("logo"));
                map.put("sellers", jObject.getString("sellers_count"));
                map.put("id", jObject.getString("id"));
                map.put("title", jObject.getString("title"));
                map.put("baidu_info", jObject.getString("baidu_info"));
                map.put("like_users_count", jObject.getString("like_users_count"));

                vegeDataList.add(map);
            }

            adapter = new VegeCustomListAdapter(context, vegeDataList);
            vegeList.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVegeListFailed() {
        // TODO Auto-generated method stub

    }

}





