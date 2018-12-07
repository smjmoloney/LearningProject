package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.Course.CustomCourse;
import fleacircus.com.learningproject.Course.LearnCourseFragment;
import fleacircus.com.learningproject.Course.LearnTopicFragment;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

public class LearnCourseActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;
    private ArrayList<CustomCourse> courses;
    private CustomCourse selectedCourse;

    /**
     * @return The viewPager variable that stores our
     * fragment container (view pager) that will present
     * various user creation tools to the user.
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    public ArrayList<CustomCourse> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<CustomCourse> courses) {
        this.courses = courses;
    }

    public CustomCourse getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(CustomCourse selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_course_activity);

        setupViewPager();
    }

    /**
     * Initialises our viewPager and attaches an adapter
     * that stores a number of user creation fragments.
     */
    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LearnCourseFragment());
        adapter.addFragment(new LearnTopicFragment());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        OnboardingUtils.noDragOnlyOnboarding(viewPager);
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
