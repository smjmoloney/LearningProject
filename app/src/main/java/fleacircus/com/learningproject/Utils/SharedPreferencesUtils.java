package fleacircus.com.learningproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Tools to quickly load and save SharedPreferences.
 * Use of SharedPreferences will be very common and
 * repetitious. These tools to reduce large and frequent
 * inputs.
 */
public class SharedPreferencesUtils {
    private static final String PREFERENCES_FILE = "learning_project";

    /**
     * Reading a returning a boolean value.
     * @param ctx The current application context.
     * @param settingName The preference currently saved to
     *                    to the device.
     * @param currentValue The value we will like to compare it against.
     * @return Return the value that has been found.
     */
    public static boolean readBoolean(Context ctx, String settingName, boolean currentValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(settingName, currentValue);
    }

    /**
     * Saving a boolean value to the device.
     * @param ctx The current application context.
     * @param settingName The preference we will save to
     *                    to the device.
     * @param newValue How we will like to save our preference.
     */
    public static void saveBoolean(Context ctx, String settingName, boolean newValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(settingName, newValue);
        editor.apply();
    }

    /**
     * Saving a string value to the device.
     * @param ctx The current application context.
     * @param settingName The preference we will save to
     *                    to the device.
     * @param newValue How we will like to save our preference.
     */
    public static void saveString(Context ctx, String settingName, String newValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, newValue);
        editor.apply();
    }
}