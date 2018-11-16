package fleacircus.com.learningproject.Utils;

import android.util.Log;

public class CustomDebugUtils {
    public static boolean matchString() {

        return false;
    }

    public static void valueNotInitialised(String nullValue, String value) {
        if (value != null) {
            Log.e("VALUE", value);
            return;
        }

        Log.e("VALUE", nullValue);
    }

    public static void valueNotInitialised(String nullValue, Object value) {
        if (value != null) {
            Log.e("VALUE", "NOT NULL");
            return;
        }

        Log.e("VALUE", nullValue);
    }
}
