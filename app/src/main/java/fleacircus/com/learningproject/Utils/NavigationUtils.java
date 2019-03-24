package fleacircus.com.learningproject.Utils;

import android.content.Context;
import android.content.Intent;

import androidx.viewpager.widget.ViewPager;

public class NavigationUtils {
    /**
     * Minimise the application when the back
     * button is pressed.
     * @param context The current context of the application.
     */
    public static void onBackPressed(Context context) {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(a);
    }

    public static void disableDragging(ViewPager viewPager) {
        viewPager.beginFakeDrag();
    }
}
