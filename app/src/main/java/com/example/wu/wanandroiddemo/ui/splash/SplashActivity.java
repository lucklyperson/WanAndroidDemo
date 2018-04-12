package com.example.wu.wanandroiddemo.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.wu.wanandroiddemo.MainActivity;
import com.example.wu.wanandroiddemo.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    private Unbinder unbinder;

    private int[] images = {R.drawable.splash0, R.drawable.splash1, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4,
            R.drawable.splash6, R.drawable.splash7, R.drawable.splash8,
            R.drawable.splash9, R.drawable.splash10, R.drawable.splash11,
            R.drawable.splash12, R.drawable.splash13, R.drawable.splash14, R.drawable.splash15, R.drawable.splash16};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        //获取随机数
        Random random = new Random();
        int i = random.nextInt(16);
        iv.setImageResource(images[i]);
        animator();
        notifyMethod();
    }

    //闪屏动画
    private void animator() {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv, "scaleX", 1.0f, 1.2f);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv, "scaleY", 1.0f, 1.2f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000).play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //通知栏提醒
    private void notifyMethod() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Welcome")
                .setContentText("Welcome into our world")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        manager.notify(1, notification);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
