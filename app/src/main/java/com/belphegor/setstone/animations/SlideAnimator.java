package com.belphegor.setstone.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class SlideAnimator {

    public enum Direction {
        LEFT, RIGHT
    }

    /**
     * Slides a view in the specified direction.
     *
     * @param view The view to animate
     * @param direction SlideAnimator.Direction.LEFT or RIGHT
     * @param distance The distance in pixels to move (positive value)
     * @param duration Duration of animation in milliseconds
     */
    public static void slide(View view, Direction direction, float distance, long duration) {
        float toX = (direction == Direction.LEFT) ? -distance : distance;

        ObjectAnimator slideAnim = ObjectAnimator.ofFloat(view, "translationX", 0f, toX);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(slideAnim, scaleYAnim, alphaAnim);
        animatorSet.start();
    }

    /**
     * Slides the view back to original position (useful after slide).
     */
    public static void reset(View view, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), 0f);
        animator.setDuration(duration);
        animator.start();
    }
}