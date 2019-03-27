package fleacircus.com.learningproject.Utils;

import android.view.View;

public class ColorUtils {
    public static void setBackgroundColor(View view, int color) {
        view.setBackgroundColor(view.getResources().getColor(color));
    }
}
