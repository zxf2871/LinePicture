package com.b8a3.linepicture;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {


    private DrawView mDrawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mDrawView = (DrawView) findViewById(R.id.dv_example);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        if (screenHeight > screenWidth) {
            mDrawView.getLayoutParams().height = screenWidth;
        } else if (screenHeight < screenWidth) {
            mDrawView.getLayoutParams().width = screenHeight;
        }
        Log.e("-----w", screenWidth + "");
        Log.e("-----h", screenHeight + "");

    }
}
