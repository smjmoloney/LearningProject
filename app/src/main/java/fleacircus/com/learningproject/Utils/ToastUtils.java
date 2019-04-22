package fleacircus.com.learningproject.Utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtils {

    public static void show(Context context, String message, int length) {
        Toast toast = Toast.makeText(context, message, length);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(toast::cancel, length);
    }
}
