package fleacircus.com.learningproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FoundUserActivity extends AppCompatActivity {

//    private void setupViewPager() {
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FoundUserFragment());
//
//        /*
//         * The {@link ViewPager} that will host the section contents.
//         */
//        CustomViewPager viewPager = findViewById(R.id.container);
//
//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager.setAdapter(adapter);
//    }

    private void setSelectedUser() {
        //noinspection ConstantConditions
        CustomUser customUser = (CustomUser) getIntent().getSerializableExtra("user");

        String n = StringUtils.toUpperCase(customUser.getName());
        String l = StringUtils.toUpperCase(customUser.getLocation());
        TextView name = findViewById(R.id.name);
        TextView location = findViewById(R.id.location);
        name.setText(n);
        location.setText(l);

        String t = customUser.getTeacherStudent();
        if (!StringUtils.hasMatch(t, getString(R.string.answer_teacher))) {
            String c = customUser.getCollegeSchool();
            if (StringUtils.hasMatch(c, getString(R.string.answer_college))) {
                TextView course = findViewById(R.id.course);
                course.setText(StringUtils.toUpperCase(customUser.getCourse()));
            }
        }

        int imageID = customUser.getImageID();
        if (imageID != 0) {
            ImageView image = findViewById(R.id.image_profile);
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_user);
        setSupportActionBar(findViewById(R.id.toolbar));

        setSelectedUser();
//        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.onCreateOptionsMenu(this, menu, true);
        MenuHelper.onCreateOptionsMenuSearch(menu, menu.findItem(R.id.action_search), this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHelper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
//
//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> fragmentList = new ArrayList<>();
//
//        SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//        void addFragment(Fragment fragment) {
//            fragmentList.add(fragment);
//        }
//    }
}
