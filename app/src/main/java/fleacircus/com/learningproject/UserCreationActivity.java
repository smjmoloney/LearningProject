package fleacircus.com.learningproject;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import fleacircus.com.learningproject.Classes.CustomViewPager;
import fleacircus.com.learningproject.UserCreation.CollegeSchoolFragment;
import fleacircus.com.learningproject.UserCreation.CourseFragment;
import fleacircus.com.learningproject.UserCreation.LocationFragment;
import fleacircus.com.learningproject.UserCreation.NameFragment;
import fleacircus.com.learningproject.UserCreation.TeacherStudentFragment;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class UserCreationActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager viewPager;

    public CustomViewPager getViewPager() {
        return viewPager;
    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TeacherStudentFragment());
        adapter.addFragment(new CollegeSchoolFragment());
        adapter.addFragment(new LocationFragment());
        adapter.addFragment(new CourseFragment());
        adapter.addFragment(new NameFragment());

        viewPager = findViewById(R.id.container);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        setupViewPager();
    }

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

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
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
