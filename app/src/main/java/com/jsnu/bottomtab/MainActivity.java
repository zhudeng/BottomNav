package com.jsnu.bottomtab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jsnu.bottom_tab.BottomNav;
import com.jsnu.bottom_tab.BottomNavListener;
import com.jsnu.bottom_tab.SingleTab;

public class MainActivity extends AppCompatActivity {
    private BottomNav bottomTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomTab=(BottomNav) findViewById(R.id.tab);
        bottomTab.setListener(new BottomNavListener() {
            @Override
            public void onSelected(int pos, SingleTab singleTab) {
                Toast.makeText(MainActivity.this,"Pos:"+pos+" is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void offSelected(int pos,SingleTab singleTab) {
                Toast.makeText(MainActivity.this,"Pos:"+pos+" is  off selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
