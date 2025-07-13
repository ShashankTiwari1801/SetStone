package com.belphegor.setstone.ui_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.belphegor.setstone.AnalysisActivity;
import com.belphegor.setstone.JUMP;
import com.belphegor.setstone.MainActivity;
import com.belphegor.setstone.R;
import com.belphegor.setstone.ProfilePageActivity;
import com.belphegor.setstone.animations.JumpAnimator;
import com.belphegor.setstone.ui.NavBar;

import java.util.List;

public class NavBarManager {

    Context context;

    NavBar navBar;

    List<ImageView> navbarBtnList;
    Class<?> currentClass;

    Class<?>[] activityList;
    Intent[] intents;

    int currentIndex = -1;
    final static int BUFFER_DURATION = 50;

    public NavBarManager(Context context, NavBar navBar, Class<?> currentClass){
        this.context = context;
        this.navBar = navBar;
        this.currentClass = currentClass;

        init();

    }

    private void init(){

        navbarBtnList = navBar.getBtnList();

        activityList = new Class[]{
                MainActivity.class,
                AnalysisActivity.class,
                ProfilePageActivity.class
        };

        intents = new Intent[activityList.length];

        for (int i = 0; i < intents.length; i++) {
            intents[i] = new Intent(context, activityList[i]);
            if(context.getClass().getName().equals(activityList[i].getName())){
                currentIndex = i;
            }
        }

        for (int i = 0; i < intents.length; i++) {
            addSwitchFunctionality(navbarBtnList.get(i), intents[i], i);
        }

    }
    private void addSwitchFunctionality(ImageView imageView, Intent intent, int target_index){
        JumpAnimator jumpAnimator = new JumpAnimator(context, imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpAnimator.animateBallJump();

                // TOTAL JUMP Duration = 300ms, start the targetActivity after 350ms
                int totalAnimationDuration = jumpAnimator.getTotalJumpDuration() - BUFFER_DURATION;

                new CountDownTimer(totalAnimationDuration, 10){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        if (!intent.getComponent().getClassName().equals(currentClass.getName())) {

                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        }
                    }
                }.start();



            }
        });

    }

}
