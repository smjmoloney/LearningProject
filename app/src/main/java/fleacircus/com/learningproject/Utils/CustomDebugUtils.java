package fleacircus.com.learningproject.Utils;

import android.util.Log;

public class CustomDebugUtils {
    public static boolean matchString() {

        return false;
    }

    public static boolean valueNotInitialised(String nullValue, String value) {
        if (value != null) {
            Log.e("VALUE", value);
            return false;
        }

        Log.e("VALUE", nullValue);
        return true;
    }

    public static boolean valueNotInitialised(String nullValue, Object value) {
        if (value != null) {
            Log.e("VALUE", "NOT NULL");
            return false;
        }

        Log.e("VALUE", nullValue);
        return true;
    }
}
