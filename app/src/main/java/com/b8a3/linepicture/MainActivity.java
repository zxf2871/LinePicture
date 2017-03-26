package com.b8a3.linepicture;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {


    private DrawView mDrawView;
    private SeekBar mControlSeekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
//        mDrawView = (DrawView) findViewById(R.id.dv_example);
//        mDrawView.setBackground(Color.BLACK);
//        mDrawView.setLineColor(Color.GREEN);
//
//        mDrawView.setStartCount(30);
//        mDrawView.setEndCount(20);
//
//
//
//        mDrawView.setRadiusLarge(1);
//
//        mControlSeekBar = (SeekBar)findViewById(R.id.sb_start_count);
//        mControlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                ((TextView)findViewById(R.id.tv_start_count)).setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                Log.e("------------","开始滑动！");
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                Log.e("------------","停止滑动！");
//            }
//        });
    }
}
