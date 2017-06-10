package com.jsnu.bottom_tab;

/**
 * Created by zhudeng on 2017/6/9.
 */

public interface BottomNavListener {
    void onSelected(int pos,SingleTab singleTab);
    void offSelected(int pos,SingleTab singleTab);
}
