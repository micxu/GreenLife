package com.buaa.greenlife.views.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ListView;

import com.buaa.greenlife.R;
import com.buaa.greenlife.views.custom.TimeLineMotion;
import com.buaa.greenlife.views.custom.TimelineAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QisenTang on 13-7-20.
 */
public class SnsFragment extends BaseFragment {

    private AsyncHttpClient httpClient = new AsyncHttpClient();
    private ListView mListView;
    private TimelineAdapter timelineAdapter;
    List<TimeLineMotion> motions = new ArrayList<TimeLineMotion>();

    private static final String FETCH_URL = "http://192.168.1.194:1234/notifications/";

    public SnsFragment(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected int getAsyncInitViewResId() {
        return R.layout.timeline;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    private void loadData(){
        httpClient.get(FETCH_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String timeline) {
                // Pull out the first event on the public timeline
                try {
                    JSONObject object = new JSONObject(timeline);
                    JSONArray array = object.getJSONArray("results");
                    motions.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject detail = array.getJSONObject(i);
                        motions.add(new TimeLineMotion(detail.getString("image_url"),
                                detail.getString("content"), detail.getInt("hot_degree"),
                                detail.getString("timestamp")));
                    }
                    timelineAdapter.setData(motions);
                    timelineAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onInflated() {
        mListView = (ListView)contentView.findViewById(R.id.tl_list);
        timelineAdapter = new TimelineAdapter(context);
        mListView.setAdapter(timelineAdapter);
        loadData();
    }
}
