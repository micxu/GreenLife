package com.buaa.greenlife;

import com.baidu.social.core.BaiduSocialException;
import com.baidu.social.core.BaiduSocialListener;
import com.baidu.social.core.Utility;
import com.baidu.sociallogin.BaiduSocialLogin;
import com.buaa.configs.BaiduSocialShareConfig;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ShareActivity extends Activity {

	final Handler handler = new Handler(Looper.getMainLooper());

	private BaiduSocialLogin socialLogin;

	private final static String appKey = BaiduSocialShareConfig.mbApiKey;

	private ImageButton sinaWeibo;

	private ImageButton qqzone;

	private ImageButton qqWeibo;

	private ImageButton kaixin;
	
	private ImageButton renren;

	private Button clean;
	
	private ImageButton baidu;

	private EditText info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		setContentView(R.layout.activity_share);

		socialLogin = BaiduSocialLogin.getInstance(this, appKey);

		
		socialLogin.supportQQSso(BaiduSocialShareConfig.QQ_SSO_APP_KEY);

		
		socialLogin.supportWeiBoSso(BaiduSocialShareConfig.SINA_SSO_APP_KEY);

		sinaWeibo = (ImageButton) findViewById(R.id.imageButton1);
		qqzone = (ImageButton) findViewById(R.id.imageButton2);
		qqWeibo = (ImageButton) findViewById(R.id.imageButton3);
		renren=(ImageButton)findViewById(R.id.imageButton5);
		
		
		

		baidu=(ImageButton)findViewById(R.id.imageButton6);
		
		

		sinaWeibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (socialLogin
						.isAccessTokenValid(Utility.SHARE_TYPE_SINA_WEIBO)) {
					socialLogin.getUserInfoWithShareType(ShareActivity.this,
							Utility.SHARE_TYPE_SINA_WEIBO,
							new UserInfoListener());
				} else
					socialLogin.authorize(ShareActivity.this,
							Utility.SHARE_TYPE_SINA_WEIBO,
							new UserInfoListener());

			}
		});

		
	}
	
	
	class UserInfoListener implements BaiduSocialListener {

		@Override
		public void onAuthComplete(Bundle values) {
			// TODO Auto-generated method stubis
		}

		@Override
		public void onApiComplete(String responses) {
			// TODO Auto-generated method stub
			final String responseStr = responses;
			handler.post(new Runnable() {
				@Override
				public void run() {
					//info = (EditText) findViewById(R.id.editText1);
					//info.setText(Utility.decodeUnicode(responseStr));
				}
			});
		}

		@Override
		public void onError(BaiduSocialException e) {
			final String error = e.toString();
			handler.post(new Runnable() {
				@Override
				public void run() {
					//info = (EditText) findViewById(R.id.editText1);
					//info.setText(error);
				}
			});
		}
	}
}
