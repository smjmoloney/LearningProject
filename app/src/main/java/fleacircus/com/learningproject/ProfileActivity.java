package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fleacircus.com.learningproject.CustomClasses.CustomUser;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.title_profile);

        applyCurrentUserToProfile();
    }

    /**
     * Updates text area within the profile layout to display
     * details of the currently logged in user.
     */
    private void applyCurrentUserToProfile() {
        ((ImageView) findViewById(R.id.profile_picture))
                .setImageDrawable(getResources().getDrawable(R.drawable.profilepic));

        String name = getString(R.string.profile_name) + CustomUser.getInstance().getName();
        ((TextView) findViewById(R.id.name_profile)).setText(name);

        String email = getString(R.string.profile_email) + CustomUser.getInstance().getEmail();
        ((TextView) findViewById(R.id.email_profile)).setText(email);

        String account = getString(R.string.profile_account) + CustomUser.getInstance().getTeacherStudent();
        ((TextView) findViewById(R.id.student_teacher_profile)).setText(account);

        String collegeSchool = getString(R.string.profile_education) + CustomUser.getInstance().getCollegeSchool();
        ((TextView) findViewById(R.id.college_school_profile)).setText(collegeSchool);

        String location = getString(R.string.profile_location) + CustomUser.getInstance().getLocation();
        ((TextView) findViewById(R.id.location_profile)).setText(location);

        if (CustomUser.getInstance().getCollegeSchool().equals("school"))
            return;

        String course = getString(R.string.profile_course) + CustomUser.getInstance().getCourse();
        ((TextView) findViewById(R.id.course_profile)).setText(course);
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