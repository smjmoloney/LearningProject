package fleacircus.com.learningproject;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fleacircus.com.learningproject.Adapters.GridImageAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Classes.CustomViewPager;
import fleacircus.com.learningproject.Classes.SectionsPagerAdapter;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Home.CreateFragment;
import fleacircus.com.learningproject.Home.LearnFragment;
import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.gridViewImages)
    GridView gridViewImages;
    @BindView(R.id.viewPagerCourses)
    CustomViewPager viewPager;
    @BindView(R.id.overlayAlpha)
    View overlayAlpha;

    int swapValue = 1;

    float alpha = 0;
    long duration = 0;

    /*
     * Temporary method allowing us to test various animations as
     * they relate to user profile details; including images, text
     * views, and recycler items.
     */
    @OnClick(R.id.imageViewProfile)
    void imageViewProfile() {
        Animation animationPop = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutProfile);
        for (int i = 0; i < constraintLayout.getChildCount(); i++)
            constraintLayout.getChildAt(i).startAnimation(animationPop);

        /*
         * Animate all other items currently on screen
         */
//        Animation imagePop = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop);
//        image.startAnimation(imagePop);
//
//        Animation fabPop = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop_delayed);
//        fab_image_swap.startAnimation(fabPop);

//        Animation left = CustomAnimationUtils.loadAnimation(this, R.anim.pop);
//        left.setDuration(animationDuration);
//
//        name.startAnimation(left);
//        location.startAnimation(left);
//        course.startAnimation(left);
    }

    @OnClick(R.id.fabProfile)
    void fabProfile() {
        CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 10);
        FloatingActionButton fabProfile = findViewById(R.id.fabProfile);

        Animation fabPress = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop);
        fabPress.setInterpolator(interpolator);
        fabProfile.startAnimation(fabPress);

        CustomAnimationUtils.circleAnimation(
                findViewById(android.R.id.content),
                gridViewImages,
                duration,
                false);

        CustomAnimationUtils.alphaAnimation(overlayAlpha, 0, alpha, duration / 2, true);
        gridViewImages.setVisibility(View.VISIBLE);
    }

    @OnItemClick(R.id.gridViewImages)
    void gridViewImage(int position) {
        Animator anim = CustomAnimationUtils.circleAnimation(
                findViewById(android.R.id.content),
                gridViewImages,
                duration,
                true);

        CustomAnimationUtils.visibilityListener(anim, gridViewImages, false);
        CustomAnimationUtils.alphaAnimation(overlayAlpha, alpha, 0, duration * 2, true);
        CustomUser.getInstance().setImageID(position);
        CustomDatabaseUtils.addOrUpdateUserDocument(CustomUser.getInstance());

        int drawable = (int) gridViewImages.getAdapter().getItem(position);
        imageViewProfile.setImageResource(drawable);
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 10);
        FloatingActionButton fabViewSwap = findViewById(R.id.fabViewSwap);

        Animation fabPress = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop);
        fabPress.setInterpolator(interpolator);
        fabViewSwap.startAnimation(fabPress);

        FragmentHelper.progressFragment(viewPager, swapValue);
        swapValue = -swapValue;
    }

    private void setupGridAdapter() {
        GridImageAdapter gridImageAdapter = new GridImageAdapter(getApplicationContext());
        gridViewImages.setAdapter(gridImageAdapter);
    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LearnFragment());
        adapter.addFragment(new CreateFragment());

        /*
         * The {@link ViewPager} that will host the section contents.
         */
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    private void setCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null)
            return;

        String[] document = new String[]{"users", firebaseUser.getUid()};
        CustomDatabaseUtils.read(document, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (isQuery) {
                        Log.e("OnSuccess", object + " must not be a query.");
                        return;
                    }

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

                    String name = StringUtils.toUpperCase(customUser.getName());
                    TextView textViewName = findViewById(R.id.textViewName);
                    textViewName.setText(name);

                    String location = StringUtils.capitaliseEach(customUser.getLocation());
                    TextView textViewLocationCourse = findViewById(R.id.textViewLocationCourse);

                    String status = customUser.getTeacherStudent();
                    String answer = getString(R.string.answer_teacher);
                    boolean isStudent = !StringUtils.hasMatch(status, answer);
                    if (!isStudent) {
                        textViewLocationCourse.setText(getString(R.string.home_teacher, location));
                    } else {
                        String educationStatus = customUser.getCollegeSchool();
                        String educationAnswer = getString(R.string.answer_college);
                        boolean isCollegeStudent = StringUtils.hasMatch(educationStatus, educationAnswer);
                        if (!isCollegeStudent) {
                            textViewLocationCourse.setText(getString(R.string.home_student, location));
                        } else {
                            String course = StringUtils.capitaliseEach(customUser.getCourse());
                            String concat = getString(R.string.home_student, location) + getString(R.string.home_course, course);
                            textViewLocationCourse.setText(concat);
                        }
                    }

                    int imageID = customUser.getImageID();
                    if (imageID != 0)
                        imageViewProfile.setImageResource(GridImageAdapterHelper.getDrawable(imageID));

                    boolean update = false;
                    if (customUser.getUid() == null || customUser.getEmail() == null) {
                        customUser.setUid(auth.getUid());
                        customUser.setEmail(auth.getCurrentUser().getEmail());
                        update = true;
                    }

                    if (update) {
                        CustomDatabaseUtils.addOrUpdateUserDocument(customUser);
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
        setContentView(R.layout.activity_home);
        setSupportActionBar(findViewById(R.id.toolbar));

        ButterKnife.bind(this, findViewById(android.R.id.content));

        alpha = (float) getResources().getInteger(R.integer.alpha_transparent_default) / 100;
        duration = (long) getResources().getInteger(R.integer.duration_default);

        setupGridAdapter();
        setupViewPager();
        setCurrentUser();
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
}