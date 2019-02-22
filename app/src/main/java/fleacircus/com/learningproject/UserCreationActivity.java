package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.UserCreation.UserCreationCollegeSchoolFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationCourseFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationLocationFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationNameFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationTeacherStudentFragment;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

/**
 * This class is the foundation of tools given to the user
 * when setting up an account.
 */
public class UserCreationActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    /**
     * @return The viewPager variable that stores our
     * fragment container (view pager) that will present
     * various user creation tools to the user.
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_creation_activity);

        setupViewPager();
    }

    /**
     * Initialises our viewPager and attaches an adapter
     * that stores a number of user creation fragments.
     */
    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserCreationTeacherStudentFragment());
        adapter.addFragment(new UserCreationCollegeSchoolFragment());
        adapter.addFragment(new UserCreationLocationFragment());
        adapter.addFragment(new UserCreationCourseFragment());
        adapter.addFragment(new UserCreationNameFragment());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        OnboardingUtils.noDragOnlyOnboarding(viewPager);
    }

    /**
     * When the back button, available on Android devices, is pressed.
     */
    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        private void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}
