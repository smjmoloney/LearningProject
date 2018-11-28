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
import fleacircus.com.learningproject.Login.SetupFragment;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

public class LoginActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupViewPager((ViewPager) findViewById(R.id.container));
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private void setupViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new SetupFragment());
        viewPager.setAdapter(adapter);

        OnboardingUtils.noDragOnlyOnboarding(viewPager);
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
