package com.buaa.greenlife.views.fragment;

import java.util.ArrayList;

import com.loopj.android.http.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.buaa.baidumap.GPSPoint;
import com.buaa.greenlife.MarketDetailActivity;
import com.buaa.greenlife.R;

/**
 * Created by QisenTang on 13-7-20.
 */
public class LocationFragment extends BaseFragment {
	private MapView mMapView = null;
	private MapController mMapController = null;
	private BMapManager mBMapMan = null;

	private ArrayList<GPSPoint> points = null;
	private MyOverlay mOverlay = null;

	private MapButton detail_button = null;
	private MapView.LayoutParams layoutParams = null;

	private double x = 39.945;
	private double y = 116.404;
	
	private String result_data;

	public LocationFragment(Context context, Handler handler) {
		super(context, handler);
		createdata();
		getDataFromInternet();
	}

	@Override
	protected int getAsyncInitViewResId() {
		mBMapMan = new BMapManager(context);
		mBMapMan.init("0A6611A400CAA717744E58C500884D4F65406BFE", null);

		return R.layout.activity_overlay;
	}

	@Override
	protected void onInflated() {
		Log.d("May", "on Inflated");

		mMapView = (MapView) contentView.findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(14);

		GeoPoint p = new GeoPoint((int) (x * 1E6), (int) (y * 1E6));
		initOverlay(p);
		mMapController.setCenter(p);

	}

	private void createdata() {
		points = new ArrayList<GPSPoint>();
		double x_ = x;
		double y_ = y;
		GPSPoint point = new GPSPoint(x_ + 0.02, y_ + 0.01);
		point.setId("bj_" + 1);
		point.setTitle("farm_" + 1);
		points.add(point);
		
		point = new GPSPoint(x_ - 0.01, y_ - 0.01);
		point.setId("bj_" + 2);
		point.setTitle("farm_" + 2);
		points.add(point);
		
		point = new GPSPoint(x_ + 0.02, y_ - 0.03);
		point.setId("bj_" + 3);
		point.setTitle("farm_" + 3);
		points.add(point);
		
		point = new GPSPoint(x_ - 0.02, y_ + 0.02);
		point.setId("bj_" + 4);
		point.setTitle("farm_" + 4);
		points.add(point);
		
		
//		for (int i = 0; i < 4; i++) {
//			x_ += 0.01 * i;
//			y_ -= 0.02 * i;
//			GPSPoint point = new GPSPoint(x_, y_);
//			point.setId("bj_" + i);
//			point.setTitle("beijing_farm_" + i);
//			points.add(point);
//		}
	}

	private void getDataFromInternet(){
		String locUrl = "http://192.168.1.194:1999/seller/";
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(locUrl, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.d("v", response);
				handle_responese(response);
				
			}
		});
		
	}
	
	private void handle_responese(String responese){
		if(points == null){
			points = new ArrayList<GPSPoint>();
		}
		// TODO : resovle the data from the Internet
	}
	
	public void initOverlay(GeoPoint myloc) {
		mOverlay = new MyOverlay(context.getResources().getDrawable(
				R.drawable.icon_marka), mMapView);
		for (int i = 0; i < points.size(); i++) {
			GPSPoint point = points.get(i);
			// Log.v("x", point.x + "");
			// Log.v("y", point.y + "");
			GeoPoint p = new GeoPoint((int) (point.x * 1E6),
					(int) (point.y * 1E6));
			OverlayItem item = new OverlayItem(p, point.getTitle(),
					point.getId());
			item.setMarker(context.getResources().getDrawable(
					R.drawable.icon_marka));
			mOverlay.addItem(item);
		}
		OverlayItem item = new OverlayItem(myloc, "my_loc", "");
		//item.setMarker(context.getResources().getDrawable(R.drawable.icon_location));
		mOverlay.addItem(item);
		mMapView.getOverlays().add(mOverlay);
		mMapView.refresh();

		detail_button = new MapButton(context);
		// TODO jump to the page of farms' detail info
		detail_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO jump to market detail activity
				Intent intent = new Intent(context, MarketDetailActivity.class);
//				intent.putExtra("farm_id", detail_button.getFramId());
//				intent.putExtra("farm_name", detail_button.getText());
				startActivity(intent);
			}
		});
	}

	public class MapButton extends Button {
		public MapButton(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		public void setFarmId(String farm_id) {
			this.farm_id = farm_id;
		}

		public String getFramId() {
			return farm_id;
		}

		String farm_id;
	}

	public class MyOverlay extends ItemizedOverlay {
		public MyOverlay(Drawable defaultmarker, MapView mapview) {
			super(defaultmarker, mapview);
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mapview) {
			// TODO Auto-generated method stub
			return super.onTap(pt, mapview);
		}

		@Override
		protected boolean onTap(int index) {
			// TODO Auto-generated method stub
			// return super.onTap(index);

			mMapView.removeView(detail_button);
			OverlayItem item = getItem(index);
			GeoPoint point = item.getPoint();

			detail_button.setText(item.getTitle());
			detail_button.setFarmId(item.getSnippet());

			layoutParams = new MapView.LayoutParams(
					MapView.LayoutParams.WRAP_CONTENT,
					MapView.LayoutParams.WRAP_CONTENT, point, 0, -32,
					MapView.LayoutParams.BOTTOM_CENTER);
			mMapView.addView(detail_button, layoutParams);
			return true;
		}
	}
}
