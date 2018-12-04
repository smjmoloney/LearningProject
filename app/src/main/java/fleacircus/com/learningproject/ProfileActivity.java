package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.MenuUtils;

/**
 * This class will identify the current user and apply various
 * features. Users can access CRUD tools and, with which, they can
 * update their profile.
 */
public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        applyCurrentUserToProfile();
    }

    /**
     * Updates text area within the profile layout to display
     * details of the currently logged in user.
     */
    private void applyCurrentUserToProfile() {
        String sampleProfileText =
                CustomUser.getInstance().getName() + "\n" +
                CustomUser.getInstance().getEmail() + "\n" +
                CustomUser.getInstance().getTeacherStudent() + "\n" +
                CustomUser.getInstance().getCollegeSchool() + "\n" +
                CustomUser.getInstance().getLocation() + "\n" +
                CustomUser.getInstance().getCourse() + "\n";

        ((TextView) findViewById(R.id.profile)).setText(sampleProfileText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}