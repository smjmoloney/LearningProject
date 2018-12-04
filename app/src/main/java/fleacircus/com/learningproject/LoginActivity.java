package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.Login.LoginFragment;
import fleacircus.com.learningproject.Login.SignUpFragment;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

public class LoginActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupViewPager();
    }

    /**
     * Initialises our viewPager and attaches an adapter
     * that stores a number of user creation fragments.
     */
    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new SignUpFragment());

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

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}
