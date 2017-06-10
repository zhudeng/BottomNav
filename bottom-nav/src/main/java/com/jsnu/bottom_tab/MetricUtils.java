package com.jsnu.bottom_tab;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by zhudeng on 2017/6/9.
 */

public class MetricUtils {
    public static float getDensity(Context context){
        DisplayMetrics metrics=new DisplayMetrics();
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }


    public static float dp2px(Context context,int dpVal){
        float scale=getDensity(context);
        return (dpVal*scale+0.5f);
    }
}
