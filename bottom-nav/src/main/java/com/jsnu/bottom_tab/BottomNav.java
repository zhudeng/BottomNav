package com.jsnu.bottom_tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zhudeng on 2017/6/9.
 */

public class BottomNav extends LinearLayout{
    private int activeId=-1;
    private int inActiveId=-1;
    private int defaultActiveIndex=-1;

    private BottomNavListener listener;
    public BottomNav(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.BottomNav);
        this.defaultActiveIndex=typedArray.getInt(R.styleable.BottomNav_defaultActive,0);        //0默认激活
        typedArray.recycle();
    }

    @Override
    protected  void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed,l,t,r,b);
        int childCount=getChildCount();
        /*
         *设置默认激活选项
         */
        if(withinTheRange(defaultActiveIndex)){
            ((SingleTab)getChildAt(defaultActiveIndex)).setActive(true);
            activeId=defaultActiveIndex;
        }
        /*
         *设置各个监听监听
         */
        for(int i=0;i<childCount;i++){
            final int selectId=i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v){
                    inActiveId=activeId;
                    activeId=selectId;
                    if(listener!=null){
                        listener.onSelected(activeId,(SingleTab)getChildAt(activeId));
                        if(withinTheRange(inActiveId)){
                            listener.offSelected(inActiveId,(SingleTab)getChildAt(inActiveId));
                        }
                    }
                    ((SingleTab)getChildAt(activeId)).setActive(true);
                    ((SingleTab)getChildAt(inActiveId)).setActive(false);
                }
            });
        }
    }

    private boolean withinTheRange(int index){
        return index>=0 && index<getChildCount();
    }

    public void setListener(BottomNavListener listener){this.listener=listener;}
}
