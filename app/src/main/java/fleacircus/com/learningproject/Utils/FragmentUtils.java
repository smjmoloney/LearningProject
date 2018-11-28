package fleacircus.com.learningproject.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Tools to assist in the development of fragments
 */
public class FragmentUtils {
    /**
     * Will refresh a fragment on visibility.
     * @param fragmentManager The default fragment manager the will
     *                        allow you to manipulate a designated fragment.
     * @param fragment The fragment to be refreshed.
     */
    public static void refreshFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager == null)
            return;

        fragmentManager
                .beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }
}
