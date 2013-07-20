package com.buaa.greenlife;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.adapter.CommentsAdapter;
import com.buaa.greenlife.bean.Comments;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FoodDetailActivity extends Activity {
	
	private ListView userCommentsListView;
	private Button likeButton;
	private Button shareButton;
	private Button sellerButton;

	
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	private	CommentsAdapter CommentslistItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	protected void initViews() {
		// TODO Auto-generated method stub
		 setContentView(R.layout.activity_food_detail);
		 likeButton = (Button)findViewById(R.id.ButtonLike);
		 shareButton = (Button)findViewById(R.id.Buttonshare);
		 sellerButton = (Button)findViewById(R.id.Buttonseller);
		 
		 Intent intent=getIntent();
		 String drugid =  intent.getStringExtra("detail");
		//System.out.println("hashmap"+drugid);
		  	
		 
		 userCommentsListView = (ListView)findViewById(R.id.drugdetail_comment_listview);
		 userCommentsListView.setDivider(null);
		 CommentslistItemAdapter = new CommentsAdapter(this, null);
		 ArrayList<Comments> mComments = new ArrayList<Comments>();
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 mComments.add(new Comments("yuxiao", "good description", "4.5", "1342515889935"));
		 userCommentsListView.setAdapter(CommentslistItemAdapter);
		 CommentslistItemAdapter.setData(mComments);
		 //userCommentsListView.addHeaderView(contentView);
		 
		 
		 likeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
             	    	
			}
		});
	}   
     
	
	
}
