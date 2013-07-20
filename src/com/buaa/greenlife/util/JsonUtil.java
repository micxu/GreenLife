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
            JSONObject jsondrug = jsonobject.getJSONObject("drugs");
            String total = jsondrug.getString(Keys.SCORE_AVG);
            Comments commentitemtotal = new Comments("","",total,"");
            allcomments.add(commentitemtotal);
            if (jsonobject.getInt(Keys.ERRORCODE) == 0){
                JSONArray comments = jsondrug.getJSONArray(Keys.COMMENTS);
                for (int i = 0 ; i < comments.length(); i ++){
                    JSONObject comment = (JSONObject)comments.get(i);
                    Comments commentitem = new Comments(comment.getString("user"),comment.getString("description"),comment.getString("score"),comment.getString("comment_time"));
                 
                    allcomments.add(commentitem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allcomments;
    }
    
   
}
