package com.example.chenhailin.myapplication;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private Button bt;
    private  long time=-1; //记录上次暂停时间
    private ObjectAnimator mMusicAnimation;
    private boolean isFromPause=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
         iv=(ImageView) findViewById(R.id.iv_icon);
        bt=(Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(mMusicAnimation.isRunning()){
//                    pauseAnimation();
//                }else {
                if (mMusicAnimation.isRunning()==false) { //停了则开始播放并倒计时
                   resumeAnimation();
                }else { //为播放续时
                    handler.removeMessages(0);
                    handler.sendEmptyMessageDelayed(0,3000);
                }
//                }
            }
        });
        findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMusicAnimation.isRunning()){ //正在播放则暂停  --
                    pauseAnimation();
                }
            }
        });
        initAnimation();
    }

   private void initAnimation(){
         mMusicAnimation = ObjectAnimator.ofFloat(iv, "rotation", 0f,360f);
       mMusicAnimation.setDuration(5000);
       mMusicAnimation.setInterpolator(new LinearInterpolator());//not stop
       mMusicAnimation.setRepeatCount(-1);//set repeat time forever
//       mMusicAnimation.start();
   }

   private void pauseAnimation(){
       handler.removeMessages(0);//移除倒计时避免 time 出错
      time= mMusicAnimation.getCurrentPlayTime();
       mMusicAnimation.cancel();
       isFromPause=true;
   }

   private void resumeAnimation(){
       mMusicAnimation.start();
       if (time!=-1) {
           mMusicAnimation.setCurrentPlayTime(time);
           isFromPause=false;
       }
       handler.removeMessages(0);
       handler.sendEmptyMessageDelayed(0,3000);
   }

   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 0:
                   pauseAnimation();
                   break;
               case 1:

                   break;

           }
       }
   };

}
