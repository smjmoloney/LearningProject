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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Adapters.GridImageAdapter;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.image_profile)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.course)
    TextView course;
    @BindView(R.id.courses)
    RecyclerView courses;
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

        grid.setVisibility(View.VISIBLE);
        CustomAnimationUtils.alphaAnimation(cover, 0, alpha, duration / 2);
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

        CustomUser.getInstance().setImageID(position);
        CustomDatabaseUtils.addOrUpdateUserDocument(CustomUser.getInstance());

        int drawable = (int) grid.getAdapter().getItem(position);
        image.setImageResource(drawable);
    }

    /**
     * Method accesses the courses collection of the currently
     * logged in user and populates our recycler view with each
     * course. They are defined as documents, each with a number
     * of fields.
     */
    private void setCourses() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //noinspection ConstantConditions
        String uid = auth.getCurrentUser().getUid();
        String[] collection = new String[]{"users", uid, "courses"};

        CustomDatabaseUtils.read(collection, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (isQuery) {
                        List<CustomCourse> mDataset = new ArrayList<>();

                        /*
                         * The above list is populated by a QueryDocumentSnapshot
                         * which retrieves all documents within a collection. Each
                         * document/object is converted into a CustomCourse class
                         * and added.
                         */
                        for (QueryDocumentSnapshot q : (QuerySnapshot) object) {
                            CustomCourse c = q.toObject(CustomCourse.class);
                            mDataset.add(c);
                        }

                        /*
                         * The {@link RecyclerHelper#setRecyclerView(Context, View, RecyclerView.Adapter)}
                         * method will apply the provided data set to the given adapter which
                         * is then presented using our recycler view. This method is and will be
                         * used frequently, thus it has been placed within a helper class.
                         */
                        RecyclerHelper.setRecyclerView(getApplicationContext(), courses, new CourseAdapter(mDataset));
                    } else
                        Log.e("OnSuccess", object + " must be a query.");
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
                        CustomUser user = ((DocumentSnapshot) object).toObject(CustomUser.class);
                        if (user == null) {
                            FirebaseAuth.getInstance().signOut();
                            return;
                        }

                        CustomUser.updateInstance(user);
                        if (user.getName() == null) {
                            startActivity(new Intent(HomeActivity.this, UserCreationActivity.class));
                            return;
                        }

                        String n = StringUtils.toUpperCase(user.getName());
                        String l = StringUtils.toUpperCase(user.getLocation());
                        name.setText(n);
                        location.setText(l);

                        String c = user.getCollegeSchool();
                        if (c.equals(StringUtils.toLowerCase(getString(R.string.answer_college))))
                            course.setText(StringUtils.toUpperCase(user.getCourse()));

                        int imageID = user.getImageID();
                        if (imageID != 0)
                            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));

                        setCourses();
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

            cover.bringToFront();

            ((View) grid.getParent()).bringToFront();
            grid.setAdapter(new GridImageAdapter(getApplicationContext()));

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
}