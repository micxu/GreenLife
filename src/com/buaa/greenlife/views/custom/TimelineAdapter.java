package com.buaa.greenlife.views.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.greenlife.R;
import com.buaa.greenlife.util.ImageUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QisenTang on 13-7-21.
 */
public class TimelineAdapter extends BaseAdapter {

    private AsyncHttpClient httpClient = new AsyncHttpClient();

    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;

    private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (key == null || bitmap == null){
            return ;
        }
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }


    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private Context context;
    private LayoutInflater inflater;

    private List<TimeLineMotion> motionList = new ArrayList<TimeLineMotion>();

    public TimelineAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TimeLineMotion> data) {
        this.motionList = data;
    }

    @Override
    public int getCount() {
        return motionList.size();
    }

    @Override
    public Object getItem(int i) {
        return motionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final TimelineItem item;
        if (convertView == null) {
            item = new TimelineItem();
        } else {
            item = (TimelineItem) convertView.getTag();
        }
        item.descView.setText(motionList.get(i).getDesc() + "\n" + motionList.get(i).getTimeStamp());
        final String imageUrl = motionList.get(i).getPhotoUrl();
        if (mMemoryCache.get(imageUrl) != null) {
            item.graphView.setImageBitmap(mMemoryCache.get(imageUrl));
        } else {
            try {
                AsyncTask<String,Integer,Bitmap> task = new AsyncTask<String, Integer, Bitmap>() {

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        addBitmapToMemoryCache(imageUrl, bitmap);
                        if (bitmap != null) {
                            item.graphView.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    protected Bitmap doInBackground(String... strings) {
                        return ImageUtil.getImageFromUrl(strings[0]);
                    }
                };
                task.execute(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return item.contentView;
    }

    class TimelineItem {
        View contentView;
        ImageView graphView;
        TextView descView;

        public TimelineItem() {
            inflater = LayoutInflater.from(context);
            contentView = inflater.inflate(R.layout.timelineitem, null);
            graphView = (ImageView) contentView.findViewById(R.id.tl_photo);
            descView = (TextView) contentView.findViewById(R.id.tl_desc);
            contentView.setTag(this);
        }
    }
}
