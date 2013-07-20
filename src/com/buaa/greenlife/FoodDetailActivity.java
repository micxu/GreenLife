package com.buaa.greenlife;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.greenlife.adapter.CommentsAdapter;
import com.buaa.greenlife.bean.Comments;

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

public class FoodDetailActivity extends Activity {
	
	private ListView userCommentsListView;
	private Button likeButton;
	private Button shareButton;
	private Button sellerButton;
	private Button baikeButton;
	private TextView titleText;
	

	
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	private	CommentsAdapter CommentslistItemAdapter;
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
	
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
		 baikeButton = (Button)findViewById(R.id.Buttonbaike);
		 titleText = (TextView)findViewById(R.id.fooddetail_textview_title);
		 
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
				/// 这一步必须要做,否则不会显示.
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//				likeButton.setCompoundDrawables(drawable,null,null,null);
				
                likeButton.setText("喜欢(237)");	    	
			}
		});
		 
		 baikeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(FoodDetailActivity.this, SimpleWebView.class);
				
				
				String url = new String("http://baike.baidu.com/list-php/dispose/searchword.php/?word=%s&pic=2");
				
				String foodname = titleText.getText().toString();
				
				String foodnameunicode  = toEncodedUnicode(foodname, true);
				
				String newurl = url.replace("%s", foodnameunicode);
				
				//Log.e("error", newurl.toString());

				intent.putExtra("url", newurl.toString());
				
				FoodDetailActivity.this.startActivity(intent);
			}
		});
		 
		 
	}   
	
	
	
	
	
	private static char toHex(int nibble) {

        return hexDigit[(nibble & 0xF)];

    }
     
	public static String toEncodedUnicode(String theString, boolean escapeSpace) {

        int len = theString.length();

        int bufLen = len * 2;

        if (bufLen < 0) {

            bufLen = Integer.MAX_VALUE;

        }

        StringBuffer outBuffer = new StringBuffer(bufLen);

 


        for (int x = 0; x < len; x++) {

            char aChar = theString.charAt(x);

            // Handle common case first, selecting largest block that

            // avoids the specials below

            if ((aChar > 61) && (aChar < 127)) {

                if (aChar == '\\') {

                    outBuffer.append('\\');

                    outBuffer.append('\\');

                    continue;

                }

                outBuffer.append(aChar);

                continue;

            }

           

            switch (aChar) {

            case ' ':

                if (x == 0 || escapeSpace) outBuffer.append('\\');

                outBuffer.append(' ');

                break;

            case '\t':

                outBuffer.append('\\');

                outBuffer.append('t');

                break;

            case '\n':

                outBuffer.append('\\');

                outBuffer.append('n');

                break;

            case '\r':

                outBuffer.append('\\');

                outBuffer.append('r');

                break;

            case '\f':

                outBuffer.append('\\');

                outBuffer.append('f');

                break;

            case '=': // Fall through

            case ':': // Fall through

            case '#': // Fall through

            case '!':

                outBuffer.append('\\');

                outBuffer.append(aChar);

                break;

            default:

                if ((aChar < 0x0020) || (aChar > 0x007e)) {

                    // 每个unicode有16位，每四位对应的16进制从高位保存到低位

                    outBuffer.append('\\');

                    outBuffer.append('u');

                    outBuffer.append(toHex((aChar >> 12) & 0xF));

                    outBuffer.append(toHex((aChar >> 8) & 0xF));

                    outBuffer.append(toHex((aChar >> 4) & 0xF));

                    outBuffer.append(toHex(aChar & 0xF));

                } else {

                    outBuffer.append(aChar);

                }

            }

        }

        return outBuffer.toString();

    }


	
}
