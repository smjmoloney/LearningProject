package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.MenuUtils;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        applyCurrentUserToProfile();
    }

    private void applyCurrentUserToProfile() {
        TextView temp = findViewById(R.id.profile);
        temp.setText(
                CustomUser.getInstance().getName() + "\n" +
                        CustomUser.getInstance().getEmail() + "\n" +
                        CustomUser.getInstance().getTeacherStudent() + "\n" +
                        CustomUser.getInstance().getCollegeSchool() + "\n" +
                        CustomUser.getInstance().getLocation() + "\n" +
                        CustomUser.getInstance().getCourse() + "\n"
        );
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