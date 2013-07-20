package com.buaa.greenlife.views.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.greenlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QisenTang on 13-7-21.
 */
public class HealthyFoodPageAdapter extends PagerAdapter{


    private final Context context;

    private LayoutInflater inflater;

    private List<FoodListItem> foodList = new ArrayList<FoodListItem>();

    private List<View> pageViews = new ArrayList<View>();

    public HealthyFoodPageAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        foodList.add(new FoodListItem("dd",false, context.getResources().getDrawable(R.drawable.food1)));
        foodList.add(new FoodListItem("dd",false, context.getResources().getDrawable(R.drawable.food2)));
        foodList.add(new FoodListItem("dd",false, context.getResources().getDrawable(R.drawable.food3)));
    }

    @Override
    public int getCount() {
        return pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (o);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pageViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageViews.get(position),0);
        return super.instantiateItem(container, position);
    }

    private class FoodListItem{
        private String desc;
        private boolean graphAbove;
        private Drawable bitmap;

        private FoodListItem(String desc, boolean graphAbove, Drawable bitmap) {
            this.desc = desc;
            this.graphAbove = graphAbove;
            this.bitmap = bitmap;
        }

        private String getDesc() {
            return desc;
        }

        private boolean isGraphAbove() {
            return graphAbove;
        }

        private Drawable getDrawable() {
            return bitmap;
        }

        public View generateView(){
            View contentView = inflater.inflate(R.layout.fooddisplayitem,null);
            ((ImageView)contentView.findViewById(R.id.food_graph)).setImageDrawable(bitmap);
            ((TextView)contentView.findViewById(R.id.food_desc)).setText(desc);
            return contentView;
        }
    }
}
