package fleacircus.com.learningproject;

import android.graphics.drawable.Drawable;
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
        ImageView imageview = findViewById(R.id.profilePic);
        Drawable myDrawable = getResources().getDrawable(R.drawable.profilepic);
        imageview.setImageDrawable(myDrawable);

        String nAme = CustomUser.getInstance().getName();
        TextView name = findViewById(R.id.nameProfile);
        name.setText("Name: " + nAme);
        String eMail = CustomUser.getInstance().getEmail();
        TextView email = findViewById(R.id.emailProfile);
        email.setText("Email: " + eMail);
        String accType = CustomUser.getInstance().getTeacherStudent();
        TextView account = findViewById(R.id.studentTeacherProfile);
        account.setText("Account Type: " + accType);
        String collegeSchool = CustomUser.getInstance().getCollegeSchool();
        TextView collSchool = findViewById(R.id.collegeSchoolProfile);
        collSchool.setText("Education: " + collegeSchool);
        String loc = CustomUser.getInstance().getLocation();
        TextView location = findViewById(R.id.locationProfile);
        location.setText("Location: " + loc);
        String courseName = CustomUser.getInstance().getCourse();
        TextView course = findViewById(R.id.courseProfile);
        course.setText("Course Name: " + courseName);
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