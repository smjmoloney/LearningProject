package fleacircus.com.learningproject.Utils;

import android.animation.ArgbEvaluator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fleacircus.com.learningproject.R;

public class OnboardingUtils {
    /**
     * Implementing OnboardingUtils
     * http://blog.grafixartist.com/onboarding-android-viewpager-google-way/
     */
    private static int currentPage = 0;

    private static ArgbEvaluator evaluator = new ArgbEvaluator();

    /**
     * This static method implements back/forward onboarding
     * within the declared activity. Navigation will alter the
     * background colour and can be navigated via button press
     * and swiping/dragging.
     *
     * @param viewPager   A variable required by the onboarding method.
     *                    Onboarding requires a tabbed activity; a viewPager
     *                    variable will be automatically created.
     * @param buttons     An array of buttons that will have OnClickListeners
     *                    implemented for page changing.
     * @param colourList  The activity background will change be altered
     *                    for each page and is determined by this array of
     *                    colour ints.
     * @param isDraggable Tabbed activities can be navigated via dragging
     *                    by default. This can be altered by the isDraggable
     *                    boolean.
     */
    public static void genericOnboarding(final ViewPager viewPager,
                                         final Button[] buttons,
                                         final int[] colourList,
                                         boolean isDraggable) {
        viewPager.setCurrentItem(currentPage);

        if (!isDraggable)
            viewPager.beginFakeDrag();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colourList[position], colourList[position == 2 ? position : position + 1]);
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                viewPager.setBackgroundColor(colourList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage -= 1;
                viewPager.setCurrentItem(currentPage, true);

                if (currentPage < 0)
                    currentPage = 0;
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage += 1;
                viewPager.setCurrentItem(currentPage, true);

                if (currentPage > colourList.length)
                    currentPage = colourList.length;
            }
        });
    }

    public static void colourOnlyOnboarding(final ViewPager viewPager, final int[] colourList) {
        viewPager.beginFakeDrag();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset,
                        colourList[position],
                        colourList[position == 2 ? position : position + 1]);

                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setBackgroundColor(colourList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
