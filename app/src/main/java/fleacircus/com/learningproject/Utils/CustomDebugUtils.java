package fleacircus.com.learningproject.Utils;

import android.util.Log;

public class CustomDebugUtils {
    public static boolean matchString() {

        return false;
    }

    public static void valueNotInitialised(String nullValue, String value) {
        if (value != null) {
            Log.e("SET", value);
            return;
        }

        Log.e("NULL", nullValue);
    }
}
