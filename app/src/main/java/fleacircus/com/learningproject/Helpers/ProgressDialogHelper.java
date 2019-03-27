package fleacircus.com.learningproject.Helpers;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogHelper {
    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }
}