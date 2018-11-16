package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.UserCreation.UserCreationCollegeStudentFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationCourseFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationLocationFragment;
import fleacircus.com.learningproject.UserCreation.UserCreationTeacherStudentFragment;

public class UserCreationActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        setupViewPager((ViewPager) findViewById(R.id.container));
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserCreationTeacherStudentFragment());
        adapter.addFragment(new UserCreationCollegeStudentFragment());
        adapter.addFragment(new UserCreationLocationFragment());
        adapter.addFragment(new UserCreationCourseFragment());
        adapter.addFragment(new UserCreationCourseFragment());
        adapter.addFragment(new UserCreationCourseFragment());
        adapter.addFragment(new UserCreationCourseFragment());
        viewPager.setAdapter(adapter);

//        OnboardingUtils.colourOnlyOnboarding(
//                viewPager,
//                new int[]{
//                        ContextCompat.getColor(this, R.color.cyan),
//                        ContextCompat.getColor(this, R.color.orange),
//                        ContextCompat.getColor(this, R.color.green),
//                        ContextCompat.getColor(this, R.color.white)
//                }
//        );
    }

    @Override
    public void onBackPressed() {
        /*
         * Nothing
         */
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
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

        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}
