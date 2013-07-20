package com.buaa.greenlife.views.custom;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VegeCustomListAdapter extends BaseAdapter{

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
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		if(null == arg1)
            vi = inflater.inflate(R.layout.fragment_vegetablelistrow, null);
		
		HashMap<String, String> vege = new HashMap<String, String>();
	    vege = data.get(pos);
	        
		TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView time_left = (TextView)vi.findViewById(R.id.TextView01); //time_left
        TextView hot_rate = (TextView)vi.findViewById(R.id.TextView02); // hot_rate
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
       
        // Setting all values in listview
        title.setText(vege.get("vege_name"));
        time_left.setText(vege.get("vege_time"));
        hot_rate.setText(vege.get("vege_rate"));
        //Ready to get Image
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        
        return vi;
	}

}
