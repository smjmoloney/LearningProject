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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Adapters.GridImageAdapter;
import fleacircus.com.learningproject.Adapters.SectionsPagerAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Classes.CustomViewPagerMeasure;
import fleacircus.com.learningproject.Helpers.AnimationHelper;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.Recyclers.CoursesFlashCardRecyclerFragment;
import fleacircus.com.learningproject.Recyclers.CoursesQuizRecyclerFragment;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FirebaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.itemAlpha) View itemAlpha;
    @BindView(R.id.imageViewProfile) ImageView imageViewProfile;
    @BindView(R.id.textViewSelectorOne) TextView textViewSelectorOne;
    @BindView(R.id.textViewSelectorTwo) TextView textViewSelectorTwo;
    @BindView(R.id.gridViewProfileImages) GridView gridViewProfileImages;
    @BindView(R.id.linearLayoutSelector) LinearLayout linearLayoutSelector;
    @BindView(R.id.viewPagerCourses) CustomViewPagerMeasure viewPagerCourses;

    float alpha = 0;
    long duration = 0;
    int swapValue = 1;
    boolean isFlashCard;
    boolean isCollegeLibrary;

    private Bundle applyCourseBundle(boolean isCreate) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFlashCard", isFlashCard);
        bundle.putBoolean("isCollegeLibrary", isCollegeLibrary);
        bundle.putBoolean("isCreate", isCreate);

        return bundle;
    }

    private CoursesFlashCardRecyclerFragment setupViewPagerFlashCard(boolean isCreate) {
        CoursesFlashCardRecyclerFragment fragment = new CoursesFlashCardRecyclerFragment();
        fragment.setArguments(applyCourseBundle(isCreate));

        return fragment;
    }

    private CoursesQuizRecyclerFragment setupViewPagerQuiz(boolean isCreate) {
        CoursesQuizRecyclerFragment fragment = new CoursesQuizRecyclerFragment();
        fragment.setArguments(applyCourseBundle(isCreate));

        return fragment;
    }

    private void applyCoursesViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if (isFlashCard) {
            adapter.addFragment(setupViewPagerFlashCard(true));
            if (!isCollegeLibrary)
                adapter.addFragment(setupViewPagerFlashCard(false));
        } else {
            adapter.addFragment(setupViewPagerQuiz(true));
            if (!isCollegeLibrary)
                adapter.addFragment(setupViewPagerQuiz(false));
        }

        viewPagerCourses.setPagingEnabled(false);
        viewPagerCourses.setAdapter(adapter);
    }

    @OnClick(R.id.textViewLibrary)
    void textViewLibrary() {
        AnimationHelper.popAnimation(findViewById(R.id.textViewLibrary));
        LinearLayout linearLayout = findViewById(R.id.linearLayoutProfile);
        RecyclerView recyclerView = linearLayout.findViewById(R.id.recyclerViewCourses);
        if (recyclerView != null)
            RecyclerHelper.setRecyclerView(this, recyclerView, new CourseAdapter(this, null));

        applySelector();
    }

    @OnClick(R.id.fabProfileImage)
    void fabProfileImage() {
        View view = findViewById(android.R.id.content);
        CustomAnimationUtils.circleAnimation(view, gridViewProfileImages, duration, false);
        CustomAnimationUtils.alphaAnimation(itemAlpha, 0, alpha, duration / 2, true);
    }

    @OnClick(R.id.fabSwap)
    void fabSwap() {
        AnimationHelper.popAnimation(findViewById(R.id.fabSwap));
        FragmentHelper.progressFragment(viewPagerCourses, swapValue);
        swapValue = -swapValue;
    }

    private void courseLibrarySelect(boolean isCollegeLibrary) {
        textViewSelectorOne.setText(R.string.courses_type_flashcard);
        textViewSelectorTwo.setText(R.string.courses_type_quiz);
        this.isCollegeLibrary = isCollegeLibrary;
    }

    private void courseTypeSelect(boolean isFlashCard) {
        LinearLayout linearLayoutSelector = findViewById(R.id.linearLayoutSelector);
        CustomAnimationUtils.alphaAnimation(linearLayoutSelector, 1, 0, duration, true);
        this.isFlashCard = isFlashCard;

        applyCoursesViewPager();
    }

    @OnClick(R.id.textViewSelectorOne)
    void textViewSelectorOne() {
        if (textViewSelectorOne.getText() != null) {
            String textViewSelectorOneValue = textViewSelectorOne.getText().toString();
            String flashCard = getString(R.string.courses_type_flashcard);
            if (textViewSelectorOneValue.equals(flashCard))
                courseTypeSelect(true);
            else
                courseLibrarySelect(false);
        }
    }

    @OnClick(R.id.textViewSelectorTwo)
    void textViewSelectorTwo() {
        if (textViewSelectorTwo.getText() != null) {
            String textViewSelectorTwoValue = textViewSelectorTwo.getText().toString();
            String quiz = getString(R.string.courses_type_quiz);
            if (textViewSelectorTwoValue.equals(quiz))
                courseTypeSelect(false);
            else
                courseLibrarySelect(true);
        }
    }

    @OnItemClick(R.id.gridViewProfileImages)
    void gridViewImage(int position) {
        View view = findViewById(android.R.id.content);
        Animator circleAnimation = CustomAnimationUtils.circleAnimation(view, gridViewProfileImages, duration, true);
        CustomAnimationUtils.visibilityListener(circleAnimation, gridViewProfileImages, false);

        CustomAnimationUtils.alphaAnimation(itemAlpha, alpha, 0, duration * 2, true);

        CustomUser.getInstance().setImageID(position);
        CustomDatabaseUtils.addOrUpdateUserDocument(CustomUser.getInstance());

        imageViewProfile.setImageResource((int) gridViewProfileImages.getAdapter().getItem(position));
    }

    private void applyGridViewAdapter() {
        GridImageAdapter gridImageAdapter = new GridImageAdapter(getApplicationContext());
        gridViewProfileImages.setAdapter(gridImageAdapter);
    }

    private void applySelector() {
        viewPagerCourses.removeAllViews();
        viewPagerCourses.setAdapter(null);

        /*
         * The {@link ViewPager} that will host the section contents.
         */
        textViewSelectorOne.setText(R.string.courses_library_user);
        textViewSelectorTwo.setText(R.string.courses_library_college);

        View view = (View) textViewSelectorOne.getParent().getParent();
        CustomAnimationUtils.alphaAnimation(view, 0, 1, duration, true);
    }

    private void applyCurrentUser(CustomUser customUser) {
        String customUserName = StringUtils.toUpperCase(customUser.getName());
        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(customUserName);

        TextView textViewLocationCourse = findViewById(R.id.textViewLocationCourse);
        String status = customUser.getTeacherStudent();
        if (StringUtils.hasMatch(status, getString(R.string.answer_teacher))) {
            textViewLocationCourse.setText(getString(R.string.home_teacher, StringUtils.capitaliseEach(customUser.getLocation())));
            return;
        }

        String education = customUser.getCollegeSchool();
        String location = customUser.getLocation();
        if (!StringUtils.hasMatch(education, getString(R.string.answer_college))) {
            textViewLocationCourse.setText(getString(R.string.home_student, StringUtils.capitaliseEach(location)));
            return;
        }

        String l = getString(R.string.home_student, StringUtils.capitaliseEach(location));
        String c = getString(R.string.home_course, StringUtils.capitaliseEach(customUser.getCourse()));
        String concat = l + c;
        textViewLocationCourse.setText(concat);

        int imageID = customUser.getImageID();
        if (imageID != 0)
            imageViewProfile.setImageResource(GridImageAdapterHelper.getDrawable(imageID));

        if (customUser.getUid() == null || customUser.getEmail() == null) {
            customUser.setUid(FirebaseUtils.getUid());
            customUser.setEmail(FirebaseUtils.getUserEmail());
            CustomDatabaseUtils.addOrUpdateUserDocument(customUser);
        }
    }

    private void applyCurrentUser() {
        String[] document = new String[]{"users", FirebaseUtils.getUid()};
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

                    DocumentSnapshot documentSnapshot = (DocumentSnapshot) object;
                    CustomUser customUser = documentSnapshot.toObject(CustomUser.class);
                    if (customUser == null) {
                        FirebaseAuth.getInstance().signOut();
                        return;
                    }

                    CustomUser.updateInstance(customUser);
                    if (customUser.getName() == null) {
                        Intent intent = new Intent(HomeActivity.this, UserCreationActivity.class);
                        NavigationUtils.startActivity(HomeActivity.this, intent);
                        return;
                    }

                    applyCurrentUser(customUser);
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

        applySelector();
        applyCurrentUser();
        applyGridViewAdapter();
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