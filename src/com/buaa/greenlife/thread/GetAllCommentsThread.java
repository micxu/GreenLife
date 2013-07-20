package com.buaa.greenlife.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.buaa.configs.MyHealth;
import com.buaa.greenlife.network.AbstractNetWorkThread;

public class GetAllCommentsThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private String productid;
    private GetAllCommentsHandler handler;
    
    @Override
    public void run() {
        try {
        	 String result =  executeGet();
        	 Message msg = new Message();
             if (result != null ){
                 Bundle mBundle = new Bundle();
                 mBundle.putString(MyHealth.Bundle_keys.COMMENTS_JSON, result);
                 msg.setData(mBundle);
                 msg.what = MyHealth.Msg.GET_ALLCOMMENTS_SUCCESSED;
             } else{
                 // network error
                 msg.what = MyHealth.Msg.GET_ALLCOMMENTS_fAILED;
             }
             handler.sendMessage(msg);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetAllCommentsThread(GetAllCommentsHandler handler,String drug_id){
    	productid = drug_id.toString();  
        this.handler = handler;
    }
    
    public static interface GetAllCommentsListener{
        
        public void getAllCommentsSuccessed(String json);
        
        public void getAllCommentsFailed();
    }
    
    public static class GetAllCommentsHandler extends Handler{
        private GetAllCommentsListener listener;
        
        public GetAllCommentsHandler(GetAllCommentsListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLCOMMENTS_SUCCESSED:
            	Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.COMMENTS_JSON);
                
               Log.e("error",json.toString());
                listener.getAllCommentsSuccessed(json);
                break;
            case MyHealth.Msg.GET_ALLCOMMENTS_fAILED:
                listener.getAllCommentsFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
    	//TODO api url
        mUrl = MyHealth.Url.BASE_URL +  "/comments/product/"+productid+"/";
        return mUrl;
    }

}
