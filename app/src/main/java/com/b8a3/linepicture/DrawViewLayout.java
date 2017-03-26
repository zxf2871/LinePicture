package com.b8a3.linepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by B8A3 on 2017/2/20.
 */

public class DrawViewLayout extends LinearLayout {

    private Canvas canvas;
    private SurfaceView mMainDrawView;
    private View mControlView;
    private final Paint mGesturePaint = new Paint();
    private boolean mIsDrawing;


    private int mWidth;
    private int mHeight;

    private int centerX;
    private int centerY;

    private int mRadius;

    private final static double CYCLE_TIME = Math.PI * 2;

    private Path mPatternPath;
    private int mStartCount = 20;
    private int mEndCount = 30;
    private int mLineColor = Color.RED;
    private int mBackground = Color.BLACK;

    private int mStartRadius;
    private int mEndRadius;


    SurfaceHolder surfaceHolder;

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
//            startDraw();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mIsDrawing = false;
        }
    };

    public DrawViewLayout(Context context) {
        this(context, null);
    }

    public DrawViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.line_drawer, this, true);
        ((SeekBar) findViewById(R.id.sb_start_count)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.tv_start_count)).setText(String.valueOf(progress));
                mStartCount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ((SeekBar) findViewById(R.id.sb_end_count)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.tv_end_count)).setText(String.valueOf(progress));
                mEndCount = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ((SeekBar) findViewById(R.id.sb_start_radius)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.tv_start_radius)).setText(String.valueOf(progress));
                mStartRadius = mRadius * progress/100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ((SeekBar) findViewById(R.id.sb_end_radius)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.tv_end_radius)).setText(String.valueOf(progress));
                mEndRadius = mRadius * progress/100;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMainDrawView = (SurfaceView) findViewById(R.id.dv_example);
        surfaceHolder = mMainDrawView.getHolder();
        surfaceHolder.addCallback(callback);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight = mMainDrawView.getMeasuredHeight();
                mWidth = mMainDrawView.getMeasuredWidth();
                centerX = mWidth / 2;
                centerY = mHeight / 2;
                if (mHeight > mWidth) {
                    mRadius = mWidth / 2;
                } else {
                    mRadius = mHeight / 2;
                }
                mStartRadius = mEndRadius = mRadius;
            }
        });

        findViewById(R.id.start).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startDraw();
            }
        });
        findViewById(R.id.end).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        findViewById(R.id.clean).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clean();
            }
        });

        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setStrokeWidth(2F);
        mGesturePaint.setColor(mLineColor);
        mPatternPath = new Path();

    }

    private void drawLine(float startX, float startY, float stopX, float stopY) {
        try {
            canvas = surfaceHolder.lockCanvas(null);
            if (canvas != null) {
//                清屏
                canvas.drawColor(Color.BLACK);
                canvas.drawPath(mPatternPath, mGesturePaint);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void clean() {
        try {
            canvas = surfaceHolder.lockCanvas(null);
            if (canvas != null) {
                canvas.drawColor(mBackground, PorterDuff.Mode.CLEAR);//清除屏幕
                mPatternPath.reset();
                canvas.drawPath(mPatternPath, mGesturePaint);

            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
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


    public void startDraw() {
        clean();
        mIsDrawing = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int startX;
                int startY;
                int endX;
                int endY;
                int commonMultiple = minCommonMultiple(mStartCount, mEndCount);
                Double startR = 0D;
                Double endR = 0D;
                double mStartRoundInterval = CYCLE_TIME / mStartCount;
                double mEndRoundInterval = CYCLE_TIME / mEndCount;

                int count = 0;

                while (mIsDrawing) {
                    endX = (int) (mEndRadius * Math.cos(endR) + centerX);
                    endY = (int) (centerY - mRadius * Math.sin(endR));

                    startX = (int) (mStartRadius * Math.cos(startR) + centerX);
                    startY = (int) (centerY - mRadius * Math.sin(startR));

                    mPatternPath.moveTo(startX, startY);
                    mPatternPath.lineTo(endX, endY);
                    drawLine(startX, startY, endX, endY);

                    startR += mStartRoundInterval;
                    endR += mEndRoundInterval;

                    if (count == commonMultiple) {
                        mIsDrawing = false;
                        break;

                    }
                    count++;
                }
            }
        }).start();
    }

    public void stop() {
        mIsDrawing = false;
    }


}
