package com.buaa.greenlife;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.adapter.CommentsAdapter;
import com.buaa.greenlife.bean.Comments;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MarketDetailActivity extends Activity {
	
	
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	private	CommentsAdapter CommentslistItemAdapter;

	private ImageView marketdetailImage;
	private Button buttonlike;
	private TextView marketTitle;
	private Button marketCommet;
	private ListView marketCommentsListView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market_detail);
		
		
		 marketCommentsListView = (ListView)findViewById(R.id.marketdetail_comment_listview);
		 marketCommentsListView.setDivider(null);
		 CommentslistItemAdapter = new CommentsAdapter(this, null);
		 ArrayList<Comments> mComments = new ArrayList<Comments>();
		
		 
		 mComments = new ArrayList<Comments>();
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 
		 CommentslistItemAdapter.setData(mComments);

		 marketCommentsListView.setAdapter(CommentslistItemAdapter);
	}
	
	
}
