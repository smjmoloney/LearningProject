package fleacircus.com.learningproject.UserCreation;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import fleacircus.com.learningproject.R;

public class UserCreationActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;
    /**
     *
     */
    User user = new User();

    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        userCreationOnboarding();
    }

    @Override
    public void onBackPressed() {
        /*
         * Nothing
         */
    }

    public void userCreationOnboarding() {
        final int[] colourList = new int[]{
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.green)
        };

        viewPager.setCurrentItem(currentPage);
        viewPager.beginFakeDrag();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset,
                        colourList[position],
                        colourList[position == 2 ? position : position + 1]);

//                viewPager.setBackgroundColor(colorUpdate);
                viewPager.setCurrentItem(currentPage, true);
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
//                viewPager.setBackgroundColor(colourList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return UserCreationFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
