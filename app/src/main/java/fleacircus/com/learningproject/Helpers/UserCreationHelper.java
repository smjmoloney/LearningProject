package fleacircus.com.learningproject.Helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class UserCreationHelper {
    public static void updateQuestionText(TextView textView, int text) {
        textView.setText(text);
    }

    public static void updateQuestionText(TextView textView, String text) {
        textView.setText(text);
    }

    public static Button createAnswerButton(Context context, int text, View.OnClickListener onClickListener) {
        Button button = new Button(context);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setText(text);
        button.setTextSize(32);
        button.setTypeface(button.getTypeface(), Typeface.BOLD);
        button.setPadding(0, 100, 0, 100);
        button.setOnClickListener(onClickListener);

        return button;
    }

    public static EditText createAnswerEditText(Context context, int placeholder) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(placeholder);
        editText.setTextSize(32);

        return editText;
    }

    public static EditText createAnswerEditText(Context context, String placeholder) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(placeholder);
        editText.setTextSize(32);

        return editText;
    }

    public static Spinner createSpinnerWithValues(Context context, int values) {
        Spinner spinner = new Spinner(context, Spinner.MODE_DIALOG);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        String[] temp = context.getResources().getStringArray(values);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, temp);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public static Spinner createSpinner(Context context) {
        Spinner spinner = new Spinner(context, Spinner.MODE_DIALOG);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        return spinner;
    }
    public static Spinner updateSpinnerWithValues(Context context, Spinner spinner, List<String> values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(adapter);

        return spinner;
    }

}