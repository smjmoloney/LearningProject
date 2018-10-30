package fleacircus.com.learningproject;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UserCreationHelper {
    public static Button createAnswerButton(Context context, int text) {
        Button button = new Button(context);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setText(text);
        button.setTextSize(32);
        button.setPadding(0, 100, 0, 100);

        return button;
    }

    public static EditText createAnswerTextField(Context context, int placeholder) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(placeholder);
        editText.setTextSize(32);
        editText.setPadding(0, 100, 0, 100);

        return editText;
    }
}
