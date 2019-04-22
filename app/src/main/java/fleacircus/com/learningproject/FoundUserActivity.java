package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Adapters.SectionsPagerAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Classes.CustomViewPagerMeasure;
import fleacircus.com.learningproject.Helpers.AnimationHelper;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.Recyclers.CoursesFlashCardRecyclerFragment;
import fleacircus.com.learningproject.Recyclers.CoursesQuizRecyclerFragment;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FoundUserActivity extends AppCompatActivity {

    @BindView(R.id.itemAlpha) View itemAlpha;
    @BindView(R.id.linearLayoutSelector) LinearLayout linearLayoutSelector;
    @BindView(R.id.viewPagerCourses) CustomViewPagerMeasure viewPagerCourses;
    @BindView(R.id.textViewSelectorOne) TextView textViewSelectorOne;
    @BindView(R.id.textViewSelectorTwo) TextView textViewSelectorTwo;
    @BindView(R.id.textViewSend) TextView textViewSend;

    View.OnClickListener onClickListenerHome;
    View.OnClickListener onClickListenerRecycler;

    private String foundUid;
    private boolean isFlashCard;

    float alpha = 0;
    long duration = 0;

    private Bundle applyCourseBundle(boolean isCreate) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFlashCard", isFlashCard);
        bundle.putBoolean("isCreate", isCreate);
        bundle.putString("foundUid", foundUid);

        return bundle;
    }

    @OnClick(R.id.textViewSelectorOne)
    void textViewSelectorOne() {
        if (textViewSelectorOne.getText() != null)
            isFlashCard = true;

        CustomAnimationUtils.alphaAnimation(linearLayoutSelector, alpha, 0, 0 / 2, true);
        applyRecyclerView();
    }

    @OnClick(R.id.textViewSelectorTwo)
    void textViewSelectorTwo() {
        if (textViewSelectorTwo.getText() != null)
            isFlashCard = false;

        CustomAnimationUtils.alphaAnimation(linearLayoutSelector, alpha, 0, 0 / 2, true);
        applyRecyclerView();
    }

    @OnClick(R.id.textViewSend)
    void textViewSend() {
        AnimationHelper.popAnimation(textViewSend);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        if (recyclerView != null)
            RecyclerHelper.setRecyclerView(this, recyclerView, new CourseAdapter(null));

        View view = findViewById(android.R.id.content);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintSelector);
        CustomAnimationUtils.circleAnimation(view, constraintLayout, duration, false);
        CustomAnimationUtils.alphaAnimation(itemAlpha, 0, alpha, duration / 2, true);

        applySelector();
    }

    private void applySelector() {
        View view = (View) textViewSelectorOne.getParent().getParent();
        CustomAnimationUtils.alphaAnimation(view, 0, 1, duration, true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(onClickListenerRecycler);
        }
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

    private void applyRecyclerView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(onClickListenerHome);
        }

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if (isFlashCard) {
            adapter.addFragment(setupViewPagerFlashCard(true));
        } else {
            adapter.addFragment(setupViewPagerQuiz(true));
        }

        viewPagerCourses.setPagingEnabled(false);
        viewPagerCourses.setAdapter(adapter);
    }

    private void applySelectedUser() {
        CustomUser customUser = (CustomUser) getIntent().getSerializableExtra("user");
        foundUid = customUser.getUid();

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
        if (imageID != 0) {
            ImageView image = findViewById(R.id.imageViewProfile);
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_user);

        ButterKnife.bind(this, findViewById(android.R.id.content));

        alpha = (float) getResources().getInteger(R.integer.alpha_transparent_default) / 100;
        duration = (long) getResources().getInteger(R.integer.duration_default);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            onClickListenerHome = v -> NavigationUtils.startActivity(FoundUserActivity.this, new Intent(FoundUserActivity.this, HomeActivity.class));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(onClickListenerHome);
        }

        onClickListenerRecycler = v -> {
            View view = findViewById(android.R.id.content);
            ConstraintLayout constraintLayout = findViewById(R.id.constraintSelector);
            CustomAnimationUtils.circleAnimation(view, constraintLayout, duration, false);
            CustomAnimationUtils.alphaAnimation(constraintLayout, 1, 0, duration / 2, true);
        };

        applySelectedUser();
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
