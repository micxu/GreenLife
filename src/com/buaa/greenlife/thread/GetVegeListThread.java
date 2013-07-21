package com.buaa.greenlife.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.buaa.configs.MyHealth;
import com.buaa.greenlife.network.AbstractNetWorkThread;

public class GetVegeListThread extends AbstractNetWorkThread implements Runnable{

	private String mUrl;
    private GetVegeListHandler handler;
	
    @Override
    public void run() {
        try {
        	 String result =  executeGet();
        	 Message msg = new Message();
             if (result != null ){
                 Bundle mBundle = new Bundle();
                 mBundle.putString(MyHealth.Bundle_keys.VEGELIST_JSON, result);
                 msg.setData(mBundle);
                 msg.what = MyHealth.Msg.GET_VEGELIST_SUCCESSED;
             } else{
                 // network error
                 msg.what = MyHealth.Msg.GET_VEGELIST_fAILED;
             }
             handler.sendMessage(msg);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetVegeListThread(GetVegeListHandler handler){
        this.handler = handler;
    }
    
    public static interface GetVegeListListener{
        
        public void getVegeListSuccessed(String json);
        
        public void getVegeListFailed();
    }
    
    public static class GetVegeListHandler extends Handler{
        private GetVegeListListener listener;
        
        public GetVegeListHandler(GetVegeListListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_VEGELIST_SUCCESSED:
            	Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.VEGELIST_JSON);
                
                listener.getVegeListSuccessed(json);
                break;
            case MyHealth.Msg.GET_VEGELIST_fAILED:
                listener.getVegeListFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
    	//TODO api url
        mUrl = MyHealth.Url.BASE_URL +  "/products/";
        return mUrl;
    }
	

}







