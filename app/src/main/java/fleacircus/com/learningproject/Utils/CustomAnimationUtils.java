package fleacircus.com.learningproject.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AnimationUtils;

import io.codetail.animation.ViewAnimationUtils;

public class CustomAnimationUtils extends AnimationUtils {
    public static Animator startAnimator(Animator anim, long animationDuration) {
        anim.setDuration(animationDuration);
        anim.start();

        return anim;
    }

    public static Animator circleAnimation(View activityContent,
                                           View clickedView,
                                           View revealedView,
                                           long animationDuration,
                                           boolean isVisible) {
        int cx = (clickedView.getLeft() + clickedView.getRight()) / 2;
        int cy = (clickedView.getTop() + clickedView.getBottom()) / 2;

        int radius = Math.max(activityContent.getWidth(), activityContent.getHeight());

        int start = 0;
        if (isVisible)
            start = radius;

        int end = 0;
        if (!isVisible)
            end = radius;

        return CustomAnimationUtils.startAnimator(
                ViewAnimationUtils.createCircularReveal(revealedView, cx, cy, start, end),
                animationDuration
        );
    }

    public static Animator circleAnimation(View activityContent,
                                           View revealedView,
                                           long animationDuration,
                                           boolean isVisible) {
        int radius = Math.max(activityContent.getWidth(), activityContent.getHeight());

        int start = 0;
        if (isVisible)
            start = radius;

        int end = 0;
        if (!isVisible)
            end = radius;

        int x = activityContent.getWidth() / 2;
        return CustomAnimationUtils.startAnimator(
                ViewAnimationUtils.createCircularReveal(revealedView, x, 0, start, end),
                animationDuration
        );
    }

    public static Animator circleAnimationFromScreenLocation(View activityContent,
                                                             View clickedView,
                                                             View revealedView,
                                                             long animationDuration,
                                                             boolean isVisible) {
        int[] cLocation = new int[2];
        clickedView.getLocationOnScreen(cLocation);

        int radius = Math.max(activityContent.getWidth(), activityContent.getHeight());

        int start = 0;
        if (isVisible)
            start = radius;

        int end = 0;
        if (!isVisible)
            end = radius;

        return CustomAnimationUtils.startAnimator(
                ViewAnimationUtils.createCircularReveal(revealedView, cLocation[0], cLocation[1], start, end),
                animationDuration
        );
    }

    public static void alphaAnimation(View view, float start, float end, long duration) {
        view.setAlpha(start);
        view.animate().alpha(end).setDuration(duration);
    }

    public static void visibilityListener(Animator animatior, View view, boolean isVisibleOnEnd) {
        animatior.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility((isVisibleOnEnd) ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }
}
