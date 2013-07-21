package com.buaa.greenlife.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.buaa.greenlife.bean.Comments;


public class JsonUtil {

    public static class Keys{
        
        //general
        public static final String ERRORCODE = "error";
        
        //this is for news
        public static final String NEWS = "news";
        public static final String ITEM = "item";
        public static final String ID = "id";
        public static final String _ID = "_id";
        public static final String NEWS_ID = "news_id";
        
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String ICON = "icon";
        public static final String DETAILS = "details";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String CATEGROY = "category";
        public static final String TAGS = "tags";
        public static final String COMMENTS = "comments";
        public static final String SCORE_AVG = "score_ave";
        public static final String NEWS_INDIVIDUAL = "news_individual";
        public static final String TASKS_INDIVIDUAL = "tasks_individual";
        public static final String QUESTIONS = "questions";
        
    }
    
    
    
    public static ArrayList<Comments> praseCommentsJson(String json){
        ArrayList<Comments> allcomments = new ArrayList<Comments>();
        try {     
            
            JSONObject jsonobject = new JSONObject(json);
            JSONArray comments = jsonobject.getJSONArray("results");
            String total = jsonobject.getString("count");
            //Log.e("error","hehe"+total.toString());
            //Comments commentitemtotal = new Comments("","",total,"");
            //allcomments.add(commentitemtotal);
            if (jsonobject !=null){
               
                for (int i = 0 ; i < comments.length(); i ++){
                    JSONObject comment = (JSONObject)comments.get(i);
                    //Log.e("error","hehe"+comment.toString());
                    Comments commentitem = new Comments(comment.getString("user_name"),comment.getString("comment"),"4.5",comment.getString("submit_date"));
                    allcomments.add(commentitem);                
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allcomments;
    }
    
    
    public static HashMap<String, Object> praseMarketJson(String json){
    	 HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            
            JSONObject jsonobject = new JSONObject(json);
            String logo = jsonobject.getString("logo");
            Log.e("error",logo.toString());
            if (jsonobject !=  null){
                logo = jsonobject.getString("logo");
                map.put("logo", logo.toString());
                String title = jsonobject.getString("title");
                map.put("title", title.toString());
                String location=jsonobject.getString("location");
                map.put("location", location.toString());
                String like_users_count = jsonobject.getString("like_users_count");
                map.put("like_users_count",like_users_count.toString());      
            }
        } catch (JSONException e) {   
            e.printStackTrace();
        }
        return map;
    }
 
   
}
