package fleacircus.com.learningproject.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AnimationUtils;

import io.codetail.animation.ViewAnimationUtils;

public class CustomAnimationUtils extends AnimationUtils {
    private static Animator startAnimator(Animator anim, long animationDuration) {
        anim.setDuration(animationDuration);
        anim.start();

        return anim;
    }

    public static Animator circleAnimation(View content,
                                           View clickedView,
                                           View revealedView,
                                           long animationDuration,
                                           boolean isVisibleOnEnd) {
        int cx = (clickedView.getLeft() + clickedView.getRight()) / 2;
        int cy = (clickedView.getTop() + clickedView.getBottom()) / 2;

        int start = 0, end = 0;
        int radius = Math.max(content.getWidth(), content.getHeight());

        if (isVisibleOnEnd)
            start = radius;
        else
            end = radius;

        return CustomAnimationUtils.startAnimator(
                ViewAnimationUtils.createCircularReveal(revealedView, cx, cy, start, end),
                animationDuration
        );
    }

    public static Animator circleAnimation(View content,
                                           View revealedView,
                                           long animationDuration,
                                           boolean isVisibleOnEnd) {
        int start = 0, end = 0;
        int radius = Math.max(content.getWidth(), content.getHeight());

        if (isVisibleOnEnd)
            start = radius;
        else
            end = radius;

        int x = content.getWidth() / 2;
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

        int start = 0, end = 0;
        int radius = Math.max(activityContent.getWidth(), activityContent.getHeight());

        if (isVisible)
            start = radius;
        else
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

    public static void visibilityListener(Animator animator, View view, boolean isVisibleOnEnd) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility((isVisibleOnEnd) ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }
}
