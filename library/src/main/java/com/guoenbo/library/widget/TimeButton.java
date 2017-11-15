package com.guoenbo.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by M-T-C on 2017/3/21 0021.
 */

public class TimeButton extends Button implements View.OnClickListener {
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "秒后重新获取~";
    private String textbefore = "点击获取验证码~";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    Map<String,Long> map = new HashMap<String,Long>();

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);

    }
    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }
    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                clearTimer();
            }
        };
    };
    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                Log.e("yung", time / 1000 + "");
                han.sendEmptyMessage(0x01);
            }
        };
    }
    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
        // t.scheduleAtFixedRate(task, delay, period);
    }
    public void onDestroy() {
        /*if (MainApplication.map == null)
            MainApplication.map = new HashMap<String, Long>();
        MainApplication.map.put(TIME, time);
        MainApplication.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.e("yung", "onDestroy");*/
    }
    public void onCreate(Bundle bundle) {
        /*Log.e("yung", MainApplication.map + "");
        if (MainApplication.map == null)
            return;
        if (MainApplication.map.size() <= 0)// �����ʾû���ϴ�δ��ɵļ�ʱ
            return;
        long time = System.currentTimeMillis() - MainApplication.map.get(CTIME)
                - MainApplication.map.get(TIME);
        MainApplication.map.clear();*/
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }


}
