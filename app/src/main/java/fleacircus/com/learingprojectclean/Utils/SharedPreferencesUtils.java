package fleacircus.com.learingprojectclean.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Suleiman on 30-04-2015.
 */
public class SharedPreferencesUtils {
    private static final String PREFERENCES_FILE = "learning_project";

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
}