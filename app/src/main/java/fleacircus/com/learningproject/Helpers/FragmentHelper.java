package fleacircus.com.learningproject.Helpers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class FragmentHelper {
    public static void progressFragment(ViewPager viewPager, int count) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + count);
    }

    private static Bundle getArguments(Fragment fragment) {
        return fragment.getArguments();
    }

    public static boolean getBoolean(Fragment fragment, String value) {
        return getArguments(fragment).getBoolean(value);
    }

    public static String getString(Fragment fragment, String value) {
        return getArguments(fragment).getString(value);
    }
}
