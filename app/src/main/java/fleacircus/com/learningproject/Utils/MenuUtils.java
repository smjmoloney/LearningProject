package fleacircus.com.learningproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import fleacircus.com.learningproject.CourseActivity;
import fleacircus.com.learningproject.FindActivity;
import fleacircus.com.learningproject.ProfileActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreation.CustomUser;

public class MenuUtils {
    public static void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu, menu);
    }

    public static void onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                context.startActivity(new Intent(context, ProfileActivity.class));
                break;
            case R.id.action_find:
                context.startActivity(new Intent(context, FindActivity.class));
                break;
            case R.id.action_course:
                context.startActivity(new Intent(context, CourseActivity.class));
                break;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                CustomUser.updateInstance(new CustomUser());
                break;
        }
    }
}
