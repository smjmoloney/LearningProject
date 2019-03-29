package fleacircus.com.learningproject;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fleacircus.com.learningproject.Adapters.GridImageAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Classes.CustomViewPager;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Home.CreatedFragment;
import fleacircus.com.learningproject.Home.LearnedFragment;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.image_profile)
    ImageView image;
    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.cover)
    View cover;

    /*
     * Temporary method allowing us to test various animations as
     * they relate to user profile details; including images, text
     * views, and recycler items.
     */
    @OnClick(R.id.image_profile)
    void imageClick() {
//        //noinspection ConstantConditions
//        Parcelable recyclerViewState = courses.getLayoutManager().onSaveInstanceState();
//
//        /*
//         * Animate items within our recycler view
//         */
//        Context context = courses.getContext();
//        LayoutAnimationController controller = CustomAnimationUtils.loadLayoutAnimation(context, R.anim.layout_up);
//
//        courses.setLayoutAnimation(controller);
//        courses.scheduleLayoutAnimation();
//
//        if (recyclerViewState != null)
//            courses.getLayoutManager().onRestoreInstanceState(recyclerViewState);
//
//        /*
//         * Animate all other items currently on screen
//         */
//        Animation right = CustomAnimationUtils.loadAnimation(this, R.anim.animation_slide_right);
//        right.setDuration(animationDuration);
//
//        image.startAnimation(right);
//        fab.startAnimation(right);
//
//        Animation left = CustomAnimationUtils.loadAnimation(this, R.anim.animation_slide_left);
//        left.setDuration(animationDuration);
//
//        name.startAnimation(left);
//        location.startAnimation(left);
//        course.startAnimation(left);
    }

    @OnClick(R.id.fab)
    void fabClick() {
        long duration = (long) getResources().getInteger(R.integer.duration_default);
        float alpha = (float) getResources().getInteger(R.integer.alpha_transparent_default) / 100;

        View content = findViewById(android.R.id.content);
        CustomAnimationUtils.circleAnimation(
                content,
                grid,
                duration,
                false);

        CustomAnimationUtils.alphaAnimation(cover, 0, alpha, duration / 2);
        grid.setVisibility(View.VISIBLE);
    }

    @OnItemClick(R.id.grid)
    void itemClick(int position) {
        long duration = (long) getResources().getInteger(R.integer.duration_default);
        float alpha = (float) getResources().getInteger(R.integer.alpha_transparent_default) / 100;

        View content = findViewById(android.R.id.content);
        Animator anim = CustomAnimationUtils.circleAnimation(
                content,
                grid,
                duration,
                true);

        CustomAnimationUtils.visibilityListener(anim, grid, false);
        CustomAnimationUtils.alphaAnimation(cover, alpha, 0, duration * 2);

        CustomUser customUser = CustomUser.getInstance();
        customUser.setImageID(position);

        CustomDatabaseUtils.addOrUpdateUserDocument(customUser);

        int drawable = (int) grid.getAdapter().getItem(position);
        image.setImageResource(drawable);
    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LearnedFragment());
        adapter.addFragment(new CreatedFragment());

        /*
         * The {@link ViewPager} that will host the section contents.
         */
        CustomViewPager viewPager = findViewById(R.id.container);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);
    }

    private void setCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //noinspection ConstantConditions
        String uid = auth.getCurrentUser().getUid();
        String[] document = new String[]{"users", uid};

        CustomDatabaseUtils.read(document, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (!isQuery) {
                        CustomUser customUser = ((DocumentSnapshot) object).toObject(CustomUser.class);
                        if (customUser == null) {
                            FirebaseAuth.getInstance().signOut();
                            return;
                        }

                        CustomUser.updateInstance(customUser);
                        if (customUser.getName() == null) {
                            startActivity(new Intent(HomeActivity.this, UserCreationActivity.class));
                            return;
                        }

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
                        if (imageID != 0)
                            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));

                        boolean update = false;
                        if (customUser.getUid() == null) {
                            customUser.setUid(auth.getUid());
                            update = true;
                        }

                        if (customUser.getEmail() == null) {
                            customUser.setEmail(auth.getCurrentUser().getEmail());
                            update = true;
                        }

                        if (update)
                            CustomDatabaseUtils.addOrUpdateUserDocument(customUser);
                    } else {
                        Log.e("OnSuccess", object + " must not be a query.");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return;
            }

            setContentView(R.layout.activity_home);
            setSupportActionBar(findViewById(R.id.toolbar));

            View content = findViewById(android.R.id.content);
            ButterKnife.bind(HomeActivity.this, content);

            setupViewPager();
            cover.bringToFront();

            View parent = (View) grid.getParent();
            parent.bringToFront();

            GridImageAdapter gridImageAdapter = new GridImageAdapter(getApplicationContext());
            grid.setAdapter(gridImageAdapter);

            setCurrentUser();
        });
    }

    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
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