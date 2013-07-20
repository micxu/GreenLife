package com.buaa.baidumap;

import java.util.ArrayList;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.buaa.greenlife.MainActivity;
import com.buaa.greenlife.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NearlyPos extends Activity {

	private MapView mMapView = null;
	private MapController mMapController = null;
	private BMapManager mBMapMan = null;
	
	private ArrayList<GPSPoint> points = null;
	private MyOverlay mOverlay = null;
	
	private MapButton detail_button = null;
	private MapView.LayoutParams layoutParams = null;
	
	private Bundle src_bundle = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO 一个类似的从外面activity跳转到这里的实例，其中x代表纬度，y代表经度
		/*Intent intent = new Intent(******.this, NearlyPos.class);
		Bundle bundle = new Bundle();
		bundle.putDouble("x", x_pos);
		bundle.putDouble("y", y_pos);
		points = new ArrayList<GPSPoint>();
		for(int i = 0; i < 5; i++){
			GPSPoint point = new GPSPoint(x_pos + i * 0.02, y_pos - i * 0.01);
			point.setId("bj_" + i);
			point.setTitle("北京菜市口_" + i);
			points.add(point);
		}
		bundle.putParcelableArrayList("points", points);
		intent.putExtras(bundle);
		startActivity(intent);*/
		
		super.onCreate(savedInstanceState);
		Intent src_intent = getIntent();
		src_bundle = src_intent.getExtras();
		
		if( src_intent.hasExtra("points") ){
        	points = src_bundle.getParcelableArrayList("points");
        }else{
        	createdata();
        }
		
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("0A6611A400CAA717744E58C500884D4F65406BFE", null);
        
        setContentView(R.layout.activity_overlay);
        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapView.setBuiltInZoomControls(true);
        mMapController = mMapView.getController();
        mMapController.enableClick(true);
        mMapController.setZoom(12);
        
        GeoPoint p ;
        double cLat = 39.945 ;
        double cLon = 116.404 ;
        
        if ( src_intent.hasExtra("x") && src_intent.hasExtra("y") ){
        	//当用intent参数时，设置中心点为指定点
        	double x = src_bundle.getDouble("x");
        	double y = src_bundle.getDouble("y");
        	p = new GeoPoint((int)(x * 1E6), (int)(y * 1E6));
        }else{
        	//设置中心点为天安门
        	p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
        }
        initOverlay();
        mMapController.setCenter(p);
	}
	
	// 生成测试用数据
	private void createdata(){
		points = new ArrayList<GPSPoint>();
		
		double x = 39.945;
		double y = 116.404;
		for(int i = 0; i < 5; i++){
			x += 0.01 * i;
			y -= 0.02 * i;
			GPSPoint point = new GPSPoint(x, y);
			points.add(point);
		}
	}
	
	public void initOverlay(){
		mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_marka), mMapView);
		for(int i = 0; i < points.size(); i++){
			GPSPoint point = points.get(i);
			//Log.v("x", point.x + "");
			//Log.v("y", point.y + "");
			GeoPoint p = new GeoPoint((int)(point.x * 1E6), (int)(point.y * 1E6));
			OverlayItem item = new OverlayItem(p, point.getId(), point.getTitle());
			item.setMarker(getResources().getDrawable(R.drawable.icon_marka));
			mOverlay.addItem(item);
		}
		mMapView.getOverlays().add(mOverlay);
		mMapView.refresh();
		
		detail_button = new MapButton(this); 
		detail_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO 将MainActivity变为农场详细介绍页的介绍
				Intent intent = new Intent(NearlyPos.this, MainActivity.class);
				intent.putExtra("farm_id", detail_button.getFramId());
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.destroy();
		if(mBMapMan != null){
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		mMapView.onPause();
		if(mBMapMan != null){
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		mMapView.onResume();
		if(mBMapMan != null){
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}
	
	public class MyOverlay extends ItemizedOverlay {
		public MyOverlay(Drawable defaultmarker, MapView mapview){
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
					MapView.LayoutParams.WRAP_CONTENT,
					point,
					0,
					-32,
					MapView.LayoutParams.BOTTOM_CENTER
					);
			mMapView.addView(detail_button, layoutParams);
			return true;
		}
	}
	
	public class MapButton extends Button{
		public MapButton(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		public void setFarmId(String farm_id) {
			this.farm_id = farm_id;
		}
		
		public String getFramId(){
			return farm_id;
		}
		String farm_id;
	}
}
