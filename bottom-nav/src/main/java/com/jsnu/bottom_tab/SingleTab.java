package com.jsnu.bottom_tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhudeng on 2017/6/7.
 */

public class SingleTab extends LinearLayout{
    private ImageView imgView;
    private TextView textView;

    private float textSize;         //文字大小
    private String textInactive;
    private String textActive;
    private int textInactiveColor;
    private int textActiveColor;
    private boolean hasText=false;

    private float imgHeight;      //图标高度
    private int imgId;
    private int imgActiveId;
    private int imgInactiveId;
    private int imgActiveColor;
    private int imgInactiveColor;
    private boolean useColorFilter;  //是否使用colorFilter

    public SingleTab(Context context, @Nullable AttributeSet attrs)throws LackResourceException {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs)throws LackResourceException {
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SingleTab);
        /*
         *添加ImageView，添加一个LayoutParam,在Measure时测量ImageView
         */
        imgView=new ImageView(context);
        imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgHeight=typedArray.getDimension(R.styleable.SingleTab_imgHeight,MetricUtils.dp2px(context,27));  //默认宽27dp
        addView(imgView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)imgHeight));

        /*
         *处理TextView,首先判断是否有Inactive或者Active时的文字，有的任何一个的话，添加一个TextView
         */
        this.textInactive=typedArray.getString(R.styleable.SingleTab_textInactive);
        this.textActive=typedArray.getString(R.styleable.SingleTab_textActive);
        if(textInactive!=null || textActive!=null){
            if(textActive==null) textActive="";
            if(textInactive==null) textInactive="";
            this.hasText=true;
            textView=new TextView(context);
            textView.setGravity(Gravity.CENTER);
            addView(textView);//添加一个TextView

            this.textSize=typedArray.getDimension(R.styleable.SingleTab_textSize,15);
            this.textActiveColor=typedArray.getColor(R.styleable.SingleTab_textActiveColor,Color.BLACK);   //默认激活时为黑色
            this.textInactiveColor=typedArray.getColor(R.styleable.SingleTab_textInactiveColor,Color.GRAY);   //默认未激活时为灰色
            textView.setTextColor(textInactiveColor);
            textView.setTextSize(textSize);
            textView.setText(textInactive);
            textView.setTextColor(textInactiveColor);
        }


        /*
         *开始处理ImageView.如果使用ColorFilter，那么只需要提供一张icon，并且指定颜色。默认与text的颜色一致.
         * 没有指定icon抛出LackResourceException
         * 如果不使用ColorFilter，那么要提供iconActive和iconInactive两张图片
         * 如果相应的图片资源未提供，抛出LackResourceException
         */
        this.useColorFilter=typedArray.getBoolean(R.styleable.SingleTab_useColorFilter,false);
        if(useColorFilter){
            this.imgId=typedArray.getResourceId(R.styleable.SingleTab_img,-1);
            if(this.imgId!=-1){
                this.imgActiveColor=typedArray.getColor(R.styleable.SingleTab_imgActiveColor,this.textActiveColor);
                this.imgInactiveColor=typedArray.getColor(R.styleable.SingleTab_imgInactiveColor,this.textInactiveColor);
                imgView.setImageResource(imgId);
                /*
                 *使用ColorFilter改变原始图片的颜色
                 */
                imgView.setColorFilter(imgInactiveColor, PorterDuff.Mode.SRC_ATOP);
            }else{
                throw new LackResourceException();
            }
        }else{
            this.imgActiveId=typedArray.getResourceId(R.styleable.SingleTab_imgActive,-1);
            this.imgInactiveId=typedArray.getResourceId(R.styleable.SingleTab_imgInactive,-1);
            if(imgActiveId==-1 || imgInactiveId==-1){
                throw new LackResourceException();
            }
            imgView.setImageResource(imgInactiveId);
        }
        typedArray.recycle();
    }

    public void setActive(boolean active){
        if(hasText){
            textView.setTextColor(active ? textActiveColor : textInactiveColor);
            textView.setText(active ? textActive : textInactive);
        }

        if(useColorFilter){
            imgView.setColorFilter(active ? imgActiveColor : imgInactiveColor, PorterDuff.Mode.SRC_ATOP);
        }else{
            imgView.setImageResource((active) ? imgActiveId :imgInactiveId);
        }
    }

    private ImageView getImgView(){return imgView;}
    private TextView getTextView(){return textView;}
}
