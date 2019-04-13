package fleacircus.com.learningproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import fleacircus.com.learningproject.WorkspaceActivity;
import fleacircus.com.learningproject.ProfileActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreation.CustomUser;

public class MenuUtils {
    public static void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu, menu);
    }

    public static void onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                CustomUser.updateInstance(new CustomUser());
                break;
            case R.id.action_icon_logout:
                FirebaseAuth.getInstance().signOut();
                CustomUser.updateInstance(new CustomUser());
                break;
            case R.id.action_workspace:
                context.startActivity(new Intent(context, WorkspaceActivity.class));
                break;
            case R.id.action_profile:
                context.startActivity(new Intent(context, ProfileActivity.class));
                break;
        }
    }
}
