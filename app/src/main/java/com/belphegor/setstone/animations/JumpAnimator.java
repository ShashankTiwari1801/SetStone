package com.belphegor.setstone.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class JumpAnimator {

    View object;
    Context context;
    int dpToJump = 10;
    int jumpDurationMS = 100;
    int downDurationMS = 200;

    public JumpAnimator(Context context, View objectToMakeJump){
        this.context = context;
        this.object = objectToMakeJump;
    }

    public int getTotalJumpDuration(){
        return jumpDurationMS + downDurationMS;
    }

    public void animateBallJump() {
        // 1. Animate Up
        // The 'jumpHeight' should be a negative value to move upwards.
        // Calculate it based on screen height or a fixed dp value.
        // For example, jump 200dp upwards.
        float jumpHeight = - dpToJump * context.getResources().getDisplayMetrics().density; // Convert dp to pixels

//        ObjectAnimator jumpUpAnimator = ObjectAnimator.ofFloat(object, "translationY", 0f, jumpHeight);
        ObjectAnimator jumpUpAnimator = ObjectAnimator.ofFloat(object, "translationY", 0f, jumpHeight);
        jumpUpAnimator.setDuration(jumpDurationMS); // Duration for going up (in milliseconds)
        jumpUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Smooth acceleration and deceleration
//        jumpUpAnimator.setInterpolator(new DecelerateInterpolator()); // Smooth acceleration and deceleration

        // 2. Animate Down (back to original position or with a bounce)
        ObjectAnimator fallDownAnimator = ObjectAnimator.ofFloat(object, "translationY", jumpHeight, 0f);
        fallDownAnimator.setDuration(downDurationMS); // Duration for coming down
        // For a simple fall:
        // fallDownAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        // For a bounce effect at the end:
        fallDownAnimator.setInterpolator(new BounceInterpolator());


        // 3. Combine animations using AnimatorSet to play them sequentially
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(jumpUpAnimator, fallDownAnimator);

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.1f);
// Starts from current scale (implicitly 1.0f if not set) and goes to 1.1f

// Create PropertyValuesHolder for scaleY
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.1f);
// Starts from current scale (implicitly 1.0f if not set) and goes to 1.1f

// Create ObjectAnimator with both PropertyValuesHolders
        ObjectAnimator scaleUpAnimator = ObjectAnimator.ofPropertyValuesHolder(object, pvhScaleX, pvhScaleY);
//        scaleUpAnimator.setDuration(500); // Set your desired duration
//        scaleUpAnimator.start();
        // Optional: Add a start delay
        // animatorSet.setStartDelay(500);

        animatorSet.start();
    }

}
