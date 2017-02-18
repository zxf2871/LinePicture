package com.b8a3.linepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;
    private final Paint mGesturePaint = new Paint();
    private boolean mIsDrawing;

    private int mWidth;
    private int mHeight;

    private int centerX;
    private int centerY;

    private int mRadius;

    private final static double CYCLE_TIME = Math.PI * 2;

    private Path mPatternPath;
    private int mStartCount;
    private int mEndCount;
    private int mLineColor;
    private int mBackground;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight = DrawView.this.getMeasuredHeight();
                mWidth = DrawView.this.getMeasuredWidth();
                centerX = mWidth / 2;
                centerY = mHeight / 2;
                if (mHeight > mWidth) {
                    mRadius = mWidth / 2;
                } else {
                    mRadius = mHeight / 2;
                }
            }
        });


        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Style.STROKE);
        mGesturePaint.setStrokeWidth(2F);
        mGesturePaint.setColor(mLineColor);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);

    }

    private void drawLine(float startX, float startY, float stopX, float stopY) {
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            if (canvas != null) {
//                清屏
//                canvas.drawColor(Color.BLACK);
                canvas.drawPath(mPatternPath, mGesturePaint);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        int startX;
        int startY;
        int endX;
        int endY;
        int commonMultiple = minCommonMultiple(mStartCount, mEndCount);
        Double startR = 0D;
        Double endR = 0D;
        mPatternPath = new Path();
        double mStartRoundInterval = CYCLE_TIME / mStartCount;
        double mEndRoundInterval = CYCLE_TIME / mEndCount;

        int count = 0;

        while (mIsDrawing) {
            endX = (int) (mRadius * Math.cos(endR) + centerX);
            endY = (int) (centerY - mRadius * Math.sin(endR));

            startX = (int) (mRadius * Math.cos(startR) + centerX);
            startY = (int) (centerY - mRadius * Math.sin(startR));

            mPatternPath.moveTo(startX, startY);
            mPatternPath.lineTo(endX, endY);
            drawLine(startX, startY, endX, endY);

            startR += mStartRoundInterval;
            endR += mEndRoundInterval;

            if (count == commonMultiple) {
                mIsDrawing = false;
            }
            count++;
        }
    }

    // 递归法求最大公约数
    public static int maxCommonDivisor(int m, int n) {
        if (m < n) {// 保证m>n,若m<n,则进行数据交换
            int temp = m;
            m = n;
            n = temp;
        }
        if (m % n == 0) {// 若余数为0,返回最大公约数
            return n;
        } else { // 否则,进行递归,把n赋给m,把余数赋给n
            return maxCommonDivisor(n, m % n);
        }
    }

    // 求最小公倍数
    public static int minCommonMultiple(int m, int n) {
        return m * n / maxCommonDivisor(m, n);
    }

    public void clear() {
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            if (canvas != null) {
                canvas.drawColor(mBackground);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void setRadiusLarge(int radiusLarge) {
        this.mRadius = radiusLarge;
    }


    public void setmStartCount(int mStartCount) {
        this.mStartCount = mStartCount;
    }

    public void setmEndCount(int mEndCount) {
        this.mEndCount = mEndCount;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public void setmBackground(int mBackground) {
        this.mBackground = mBackground;
    }

}
