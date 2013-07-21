package com.buaa.greenlife.views.custom;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.R;
import com.buaa.greenlife.network.ImageCache;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VegeCustomListAdapter extends BaseAdapter {

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
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private ArrayList<HashMap<String, String>> data;
    Context context;

    public VegeCustomListAdapter(Context _context, ArrayList<HashMap<String, String>> d) {
        data = d;
        context = _context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int pos, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View vi = arg1;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if (null == arg1) {
            vi = inflater.inflate(R.layout.fragment_vegetablelistrow, null);
        }

        HashMap<String, String> vege = new HashMap<String, String>();
        vege = data.get(pos);

        TextView title = (TextView) vi.findViewById(R.id.title); // title
        TextView time_left = (TextView) vi.findViewById(R.id.TextView01); //time_left
        TextView hot_rate = (TextView) vi.findViewById(R.id.TextView02); // hot_rate
        final ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb image

        final String iconUrl = vege.get("logo");

        if (mMemoryCache.get(iconUrl) != null) {
            thumb_image.setImageBitmap(mMemoryCache.get(iconUrl));
        } else {
            try {
                String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
                httpClient.get(vege.get("logo"), new BinaryHttpResponseHandler(allowedContentTypes) {
                    @Override
                    public void onSuccess(byte[] fileData) {
                        // Do something with the file
                        try {
                            AsyncTask<byte[], Integer, Bitmap> task = new AsyncTask<byte[], Integer, Bitmap>() {

                                @Override
                                protected void onPreExecute() {
                                }

                                @Override
                                protected void onPostExecute(Bitmap bitmap) {
                                    if (bitmap != null) {
                                        thumb_image.setImageBitmap(bitmap);
                                    }
                                }

                                @Override
                                protected Bitmap doInBackground(byte[]... bytes) {
                                    if (null == bytes[0]) return null;
                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = BitmapFactory.decodeByteArray(bytes[0], 0, bytes[0].length);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    addBitmapToMemoryCache(iconUrl, bitmap);
                                    return bitmap;
                                }
                            };
                            task.execute(fileData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // Setting all values in listview
        title.setText(vege.get("title"));
        time_left.setText(vege.get("in_season_time") + "周");
        hot_rate.setText(vege.get("like_users_count"));
        //Ready to get Image
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);

        return vi;
    }

}
