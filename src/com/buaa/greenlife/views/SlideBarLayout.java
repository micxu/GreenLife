package com.buaa.greenlife.views;

import android.util.Log;
import android.view.ViewGroup;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.MotionEvent;
import android.view.View;

import com.buaa.greenlife.R;

/**
 * Created by QisenTang on 13-7-20.
 */

public class SlideBarLayout extends ViewGroup{
    public final static int DURATION = 500;

    protected boolean mPlaceLeft = true;
    protected boolean mOpened;
    protected View mSidebar;
    protected View mContent;
    protected int mSidebarWidth = 150; /* assign default value. It will be overwrite
                                          in onMeasure by Layout xml resource. */

    protected Animation mAnimation;
    protected OpenListener    mOpenListener;
    protected CloseListener   mCloseListener;
    protected SlideListener mListener;

    protected boolean mPressed = false;

    public SlideBarLayout(Context context) {
        this(context, null);
    }

    public SlideBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mSidebar = findViewById(R.id.animation_layout_sidebar);
        mContent = findViewById(R.id.animation_layout_content);

        mOpenListener = new OpenListener(mSidebar, mContent);
        mCloseListener = new CloseListener(mSidebar, mContent);
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("May","onLayout");
        Log.d("May",l+" "+t+" "+r+" "+b);
        /* the title bar assign top padding, drop it */
        int sidebarLeft = l;
        if (!mPlaceLeft) {
            sidebarLeft = r - mSidebarWidth;
        }
        mSidebar.layout(sidebarLeft,
                0,
                sidebarLeft + mSidebarWidth,
                0 + mSidebar.getMeasuredHeight());
        Log.d("May",sidebarLeft+" "+0+" "+(sidebarLeft + mSidebarWidth)+" "+mSidebar.getMeasuredHeight());

        if (mOpened) {
            if (mPlaceLeft) {
                mContent.layout(l + mSidebarWidth, 0, r + mSidebarWidth, b);
                Log.d("May","A" + (l + mSidebarWidth)+" "+0+" "+(r + mSidebarWidth)+" "+b);
            } else  {
                mContent.layout(l - mSidebarWidth, 0, r - mSidebarWidth, b);
                Log.d("May","B" + (l - mSidebarWidth)+" "+0+" "+(r - mSidebarWidth)+" "+b);
            }
        } else {
            mContent.layout(l, 0, r, b);
            Log.d("May","C" + l+" "+0+" "+r+" "+b);
        }
    }

    @Override
    public void onMeasure(int w, int h) {
        Log.d("May","onMeasure");
        Log.d("May",mSidebarWidth + "");
        super.onMeasure(w, h);
        super.measureChildren(w, h);
        mSidebarWidth = mSidebar.getMeasuredWidth();
        Log.d("May",mSidebarWidth + "");
    }

    @Override
    protected void measureChild(View child, int parentWSpec, int parentHSpec) {
        /* the max width of Sidebar is 90% of Parent */
        if (child == mSidebar) {
            int mode = MeasureSpec.getMode(parentWSpec);
            int width = (int)(getMeasuredWidth() * 0.9);
            super.measureChild(child, MeasureSpec.makeMeasureSpec(width, mode), parentHSpec);
        } else {
            super.measureChild(child, parentWSpec, parentHSpec);
        }
    }

    float originx = 0.0f;
    float originy = 0.0f;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isOpening()) {
            return false;
        }

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
                if (ev.getX() - originx > 8) {
                    toggleSidebar();
                } else if(ev.getX() - originx < -8) {
                    toggleSidebar();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                originx = ev.getX();
                originy = ev.getY();
                break;
        }


        /* if user press and release both on Content while
         * sidebar is opening, call listener. otherwise, pass
         * the event to child. */
        return false;
    }

    public void setListener(SlideListener l) {
        mListener = l;
    }

    /* to see if the Sidebar is visible */
    public boolean isOpening() {
        return mOpened;
    }

    public void toggleSidebar() {
        if (mContent.getAnimation() != null) {
            return;
        }

        if (mOpened) {
            /* opened, make close animation*/
            if (mPlaceLeft) {
                mAnimation = new TranslateAnimation(0, -mSidebarWidth, 0, 0);
            } else {
                mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
            }
            mAnimation.setAnimationListener(mCloseListener);
        } else {
            /* not opened, make open animation */
            if (mPlaceLeft) {
                mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
            } else {
                mAnimation = new TranslateAnimation(0, -mSidebarWidth, 0, 0);
            }
            mAnimation.setAnimationListener(mOpenListener);
        }
        mAnimation.setDuration(DURATION);
        mAnimation.setFillAfter(true);
        mAnimation.setFillEnabled(true);
        mContent.startAnimation(mAnimation);
    }

    public void openSidebar() {
        if (!mOpened) {
            toggleSidebar();
        }
    }

    public void closeSidebar() {
        if (mOpened) {
            toggleSidebar();
        }
    }

    class OpenListener implements Animation.AnimationListener {
        View iSidebar;
        View iContent;

        OpenListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            iSidebar.setVisibility(View.VISIBLE);
        }

        public void onAnimationEnd(Animation animation) {
            iContent.clearAnimation();
            mOpened = !mOpened;
            requestLayout();
            if (mListener != null) {
                mListener.onSidebarOpened();
            }
        }
    }

    class CloseListener implements Animation.AnimationListener {
        View iSidebar;
        View iContent;

        CloseListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            iContent.clearAnimation();
            iSidebar.setVisibility(View.INVISIBLE);
            mOpened = !mOpened;
            requestLayout();
            if (mListener != null) {
                mListener.onSidebarClosed();
            }
        }
    }

    public interface SlideListener {
        public void onSidebarOpened();
        public void onSidebarClosed();
        public boolean onContentTouchedWhenOpening();
    }
}
