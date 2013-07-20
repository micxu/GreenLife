package com.buaa.greenlife;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.adapter.CommentsAdapter;
import com.buaa.greenlife.bean.Comments;
import com.buaa.greenlife.thread.GetAllCommentsThread;
import com.buaa.greenlife.thread.GetAllCommentsThread.GetAllCommentsHandler;
import com.buaa.greenlife.thread.GetAllCommentsThread.GetAllCommentsListener;
import com.buaa.greenlife.util.JsonUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FoodDetailActivity extends Activity implements GetAllCommentsListener{
	
	private ListView userCommentsListView;
	private Button likeButton;
	private Button shareButton;
	private Button sellerButton;
	private Button baikeButton;
	private TextView titleText;
	private String baiduinfo;
	private String id;
	

	
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	private	CommentsAdapter CommentslistItemAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initViews();
		
		Intent intent= getIntent();
		String likenumber = intent.getStringExtra("like_users");
		String title = intent.getStringExtra("title");
		id = intent.getStringExtra("id");
		baiduinfo = intent.getStringExtra("baidu_info");
		
		
		likeButton.setText("Like("+likenumber.toString()+")");
		titleText.setText(title.toString());
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		GetAllCommentsThread.GetAllCommentsHandler handler = new GetAllCommentsHandler(FoodDetailActivity.this);
		GetAllCommentsThread commentsThread = new GetAllCommentsThread(handler,id.toString());
		commentsThread.start();
		super.onResume();
	}

	
	protected void initViews() {
		// TODO Auto-generated method stub
		 setContentView(R.layout.activity_food_detail);
		 likeButton = (Button)findViewById(R.id.ButtonLike);
		 shareButton = (Button)findViewById(R.id.Buttonshare);
		 sellerButton = (Button)findViewById(R.id.Buttonseller);
		 baikeButton = (Button)findViewById(R.id.Buttonbaike);
		 titleText = (TextView)findViewById(R.id.fooddetail_textview_title);
		 
		 Intent intent=getIntent();
		 String drugid =  intent.getStringExtra("detail");
		//System.out.println("hashmap"+drugid);
		  	
		 
		 userCommentsListView = (ListView)findViewById(R.id.drugdetail_comment_listview);
		 userCommentsListView.setDivider(null);
		 CommentslistItemAdapter = new CommentsAdapter(this, null);
		 ArrayList<Comments> mComments = new ArrayList<Comments>();
		 userCommentsListView.setAdapter(CommentslistItemAdapter);
		// CommentslistItemAdapter.setData(mComments);
		 //userCommentsListView.addHeaderView(contentView);
		 
		 
		 shareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(FoodDetailActivity.this, ShareActivity.class);
				
				FoodDetailActivity.this.startActivity(intent);
			}
		});
		 
		 likeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawable drawable= getResources().getDrawable(R.drawable.rating_good_black);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//				likeButton.setCompoundDrawables(drawable,null,null,null);
				
               // likeButton.setText("ϲ��(237)");	    	
			}
		});
		 
		 baikeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(FoodDetailActivity.this, SimpleWebView.class);
				
				
				String url = new String("http://baike.baidu.com/list-php/dispose/searchword.php/?word=lettuce&pic=2");
				
				String foodname = titleText.getText().toString();
				
				//String foodnameunicode  = toEncodedUnicode(foodname, true);
				
				//String newurl = url.replace("%s", foodnameunicode);
				
				//Log.e("error", newurl.toString());

				intent.putExtra("url", baiduinfo.toString());
				
				FoodDetailActivity.this.startActivity(intent);
			}
		});
		 
		 
	}   
	
	@Override
	public void getAllCommentsSuccessed(String json) {
		// TODO Auto-generated method stub
		//Log.e("error","hello");
		ArrayList<Comments> data = JsonUtil.praseCommentsJson(json);
	//	Comments totalcomment = data.get(0);
//		String commentstring = totalcomment.getmPoints();
//		float totalpoints = Float.parseFloat(commentstring);
//		data.remove(0);
		
		String commentsnumber = data.size()+"";
		
		CommentslistItemAdapter.setData(data);
	}
	@Override
	public void getAllCommentsFailed() {
		// TODO Auto-generated method stub
		
	}
	
}
