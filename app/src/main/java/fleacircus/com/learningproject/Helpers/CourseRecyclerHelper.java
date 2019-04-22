package fleacircus.com.learningproject.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import fleacircus.com.learningproject.FlashCardCreateActivity;
import fleacircus.com.learningproject.QuizCreateActivity;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class CourseRecyclerHelper {
    public static void isCreator(Activity activity, View view, boolean isFlashCard, boolean isCollege, boolean isCreate) {
        Log.e("isCreator", isCollege + "");

        if (isCollege || !isCreate) {
            ((View) view.getParent()).setVisibility(View.GONE);
            return;
        }

        Intent intent = new Intent(activity, (isFlashCard) ? FlashCardCreateActivity.class : QuizCreateActivity.class);
        view.setOnClickListener(v -> NavigationUtils.startActivity(activity, intent));
    }

    public static void isCreator(Activity activity, View view, boolean isFlashCard, boolean isCollege, boolean isCreate, String foundUid) {
        Log.e("isCreator", isCollege + "");

        if (isCollege || !isCreate || ((foundUid != null) && !foundUid.isEmpty())) {
            ((View) view.getParent()).setVisibility(View.GONE);
            return;
        }

        Intent intent = new Intent(activity, (isFlashCard) ? FlashCardCreateActivity.class : QuizCreateActivity.class);
        view.setOnClickListener(v -> NavigationUtils.startActivity(activity, intent));
    }
}
