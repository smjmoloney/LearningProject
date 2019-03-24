package fleacircus.com.learningproject.Helpers;

import androidx.viewpager.widget.ViewPager;

public class FragmentHelper {
    public static void progressFragment(ViewPager viewPager, int count) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + count);
    }
}
