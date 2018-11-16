package fleacircus.com.learningproject.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentUtils {
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
