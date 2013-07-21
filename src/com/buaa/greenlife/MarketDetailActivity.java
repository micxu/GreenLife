package com.buaa.greenlife;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.adapter.CommentsAdapter;
import com.buaa.greenlife.bean.Comments;
import com.buaa.greenlife.thread.GetAllCommentsThread;
import com.buaa.greenlife.thread.GetAllCommentsThread.GetAllCommentsHandler;
import com.buaa.greenlife.thread.GetSellerThread.GetAllSellerListener;
import com.buaa.greenlife.thread.GetSellerThread;
import com.buaa.greenlife.thread.GetSellerThread.GetAllSellersHandler;
import com.buaa.greenlife.util.JsonUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MarketDetailActivity extends Activity implements GetAllSellerListener {
	
	
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	private	CommentsAdapter CommentslistItemAdapter;

	private ImageView marketdetailImage;
	private Button buttonlike;
	private TextView marketTitle;
	private Button marketCommet;
	private ListView marketCommentsListView;
	private String farm_id;
	private Button marketLocation;
	private String number;
	private TextView productNumber;

	private AsyncHttpClient httpClient = new AsyncHttpClient();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market_detail);
		
		 Intent inte=getIntent();     
		 
	     String farm_name=inte.getStringExtra("farm_name"); 
	     farm_id = inte.getStringExtra("farm_id");
		
		 marketCommentsListView = (ListView)findViewById(R.id.marketdetail_comment_listview);
		 marketCommentsListView.setDivider(null);
		 CommentslistItemAdapter = new CommentsAdapter(this, null);
		 ArrayList<Comments> mComments = new ArrayList<Comments>();
		
		 number = String.valueOf((int)(Math.random() * 10+1)) ;
		 
		 mComments = new ArrayList<Comments>();
		 mComments.add(new Comments("keith", "nice", "3.5", "2013-07-20T23:44:10Z"));
		 mComments.add(new Comments("yuxiao", "good", "3.0", "2013-07-21T12:44:10Z"));
		 
		 CommentslistItemAdapter.setData(mComments);

		 marketCommentsListView.setAdapter(CommentslistItemAdapter);
		 
		 
		 marketTitle = (TextView)findViewById(R.id.marketdetail_textview_title);
		 //marketTitle.setText(farm_name);
		 marketLocation = (Button)findViewById(R.id.marketlocation);
		 marketdetailImage = (ImageView)findViewById(R.id.marketdetail_imageview);
		 buttonlike = (Button)findViewById(R.id.Marketlike);
		 productNumber = (TextView)findViewById(R.id.product_text);
		 
		 
		 String myproduct = this.getString(R.string.product);
		 productNumber.setText(myproduct+"("+number.toString()+")");
		 //buttonlike.setText(number.toString());
		 
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		GetSellerThread.GetAllSellersHandler handler = new GetAllSellersHandler(MarketDetailActivity.this);
		GetSellerThread commentsThread = new GetSellerThread(handler,farm_id.toString());
		commentsThread.start();
		super.onResume();
	}

	@Override
	public void getAllCommentsSuccessed(String json) {
		// TODO Auto-generated method stub
		HashMap<String, Object> data = JsonUtil.praseMarketJson(json);
		
		marketTitle.setText(data.get("title").toString());
		marketLocation.setText(data.get("location").toString());
		
		 String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
		 httpClient.get(data.get("logo").toString(), new BinaryHttpResponseHandler(allowedContentTypes) {
	            @Override
	            public void onSuccess(byte[] fileData) {
	                // Do something with the file
	                try{
	                    Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
	                    marketdetailImage.setImageBitmap(bitmap);
	                } catch (Exception e){
	                    e.printStackTrace(); 
	                }
	            }
	        });
		
		
	}


	@Override
	public void getAllCommentsFailed() {
		// TODO Auto-generated method stub
		
	}
	
	
}
