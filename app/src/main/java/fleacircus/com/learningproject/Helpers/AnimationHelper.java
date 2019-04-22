package fleacircus.com.learningproject.Helpers;

import android.view.View;
import android.view.animation.Animation;

import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;

public class AnimationHelper {
    public static void popAnimation(View view) {
        Animation pop = CustomAnimationUtils.loadAnimation(view.getContext(), R.anim.animation_pop);
        pop.setInterpolator(new CustomBounceInterpolator(0.2, 10));
        view.startAnimation(pop);
    }
}
