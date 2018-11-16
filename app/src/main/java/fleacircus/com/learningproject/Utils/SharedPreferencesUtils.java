package fleacircus.com.learningproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static final String PREFERENCES_FILE = "learning_project";

    public static boolean readBoolean(Context ctx, String settingName, boolean currentValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(settingName, currentValue);
    }

    public static void saveBoolean(Context ctx, String settingName, boolean newValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(settingName, newValue);
        editor.apply();
    }

    public static void saveSting(Context ctx, String settingName, String newValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, newValue);
        editor.apply();
    }
}