package com.example.xumingming.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by xumingming on 2017/6/30.
 * 倒计时 组件
 */

public class CountDownView extends FrameLayout {

    private TextView tvHour,tvMinute,tvSecond,tvPoint1,tvPoint2;

    public CountDownView(Context context) {
        super(context);
        init();
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }


    private void init(){

        View view = View.inflate(getContext(),R.layout.view_countdown,null);
        tvHour = (TextView) view.findViewById(R.id.tv_hour);
        tvMinute = (TextView) view.findViewById(R.id.tv_minute);
        tvSecond = (TextView) view.findViewById(R.id.tv_second);
        tvPoint1 = (TextView) view.findViewById(R.id.tv_point1);
        tvPoint2 = (TextView) view.findViewById(R.id.tv_point2);
        addView(view);


    };

    /**
     * 设置小时
     * @param hour
     */
    public void setTvHour(String hour){
        if(tvHour != null){
            tvHour.setText(hour);
        }
    }

    /**
     * 设置分钟
     * @param minute
     */
    public void setTvMinute(String minute){
        if(tvMinute != null){
            tvMinute.setText(minute);
        }
    }
    /**
     * 设置秒
     * @param second
     */
    public void setTvSecond(String second){
        if(tvSecond != null){
            tvSecond.setText(second);
        }
    }

    /**
     * 设置 时分秒 背景颜色
     * @param resoures
     */
    public void setTimeBackhround(int resoures){
        if((tvHour != null) && (tvMinute != null) && (tvSecond != null)){
            tvHour.setBackgroundResource(resoures);
            tvMinute.setBackgroundResource(resoures);
            tvSecond.setBackgroundResource(resoures);
        }
    }

    /**
     * 设置点的颜色
     * @param resoures
     */
    public void setPointBackhround(int resoures){
        if((tvPoint1 != null) && (tvPoint2 != null)){
            tvPoint1.setBackgroundResource(resoures);
            tvPoint2.setBackgroundResource(resoures);
        }
    }

    /**
     * 设置文字的颜色
     * @param resoures
     */
    public void setTextColor(int resoures){
        if((tvHour != null) && (tvMinute != null) && (tvSecond != null)){
            tvHour.setTextColor(resoures);
            tvMinute.setTextColor(resoures);
            tvSecond.setTextColor(resoures);
        }
    }


    //一共的秒数
    private int totalTime = 0;
    private void setTime(int time){

        totalTime = time;
        //小时
        int hour = time/60/60 ;
        //分钟
        int minute = (time - hour*60*60)/60 ;
        //秒
        int second = time % 60;


        tvHour.setText(hour<10?"0"+hour:hour+"");
        tvMinute.setText(minute<10?"0"+minute:minute+"");
        tvSecond.setText(second<10?"0"+second:second+"");


    }

    public  void startTime(int time){
        setTime(time);
        mHandler.sendEmptyMessageDelayed(1,1000);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(totalTime != 0){
                totalTime -- ;
                setTime(totalTime);
                if(totalTime == 0){
                    if(listener != null){
                        listener.onFinish(true);
                    }
                }else{
                    mHandler.sendEmptyMessageDelayed(1,1000);
                }
            }

        }
    };

    private OnFinishListener listener;

    public OnFinishListener getListener() {
        return listener;
    }

    public void setListener(OnFinishListener listener) {
        this.listener = listener;
    }

    public interface OnFinishListener{
        void onFinish(boolean isFinish);
    }



}
