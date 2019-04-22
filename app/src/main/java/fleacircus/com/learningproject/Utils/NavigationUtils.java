package fleacircus.com.learningproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;

public class NavigationUtils {
    /**
     * Minimise the application when the back
     * button is pressed.
     *
     * @param context The current context of the application.
     */
    public static void onBackPressed(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void startActivity(Activity activity, Intent intent, ActivityOptionsCompat options) {
        activity.startActivity(intent, options.toBundle());
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
