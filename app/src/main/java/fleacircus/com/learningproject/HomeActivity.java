package fleacircus.com.learningproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LayoutAnimationController;
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
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Adapters.FindUserAdapter;
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
    @BindView(R.id.find)
    RecyclerView find;
    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.cover)
    View cover;

    long animationDuration = 500;
    float coverAlpha = .75f;

    /*
     * Temporary method allowing us to test various animations as
     * they relate to user profile details; including images, text
     * views, and recycler items.
     */
    @OnClick(R.id.image_profile)
    void imageClick() {
        //noinspection ConstantConditions
        Parcelable recyclerViewState = courses.getLayoutManager().onSaveInstanceState();

        /*
         * Animate items within our recycler view
         */
        Context context = courses.getContext();
        LayoutAnimationController controller = CustomAnimationUtils.loadLayoutAnimation(context, R.anim.layout_up);

        courses.setLayoutAnimation(controller);
        courses.scheduleLayoutAnimation();

        if (recyclerViewState != null)
            courses.getLayoutManager().onRestoreInstanceState(recyclerViewState);

        /*
         * Animate all other items currently on screen
         */
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

    private void findClick(boolean isVisible) {
        View activityContent = findViewById(android.R.id.content);
//        View menuItem = findViewById(R.id.action_search);
        Animator anim = CustomAnimationUtils.circleAnimation(
                activityContent,
//                menuItem,
                find,
                animationDuration,
                isVisible);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (isVisible)
                    find.setVisibility(View.INVISIBLE);
            }
        });

        if (!isVisible) {
            find.setVisibility(View.VISIBLE);

            cover.setAlpha(0);
            cover.animate().alpha(coverAlpha).setDuration(animationDuration / 2);
        } else {
            cover.setAlpha(coverAlpha);
            cover.animate().alpha(0).setDuration(animationDuration * 2);
        }
    }

    @OnClick(R.id.fab)
    void fabClick() {
        View activityContent = findViewById(android.R.id.content);
//        FloatingActionButton fab = findViewById(R.id.fab);

        CustomAnimationUtils.circleAnimation(
                activityContent,
//                fab,
                grid,
                animationDuration,
                false);

        grid.setVisibility(View.VISIBLE);

        cover.setAlpha(0f);
        cover.animate().alpha(coverAlpha).setDuration(animationDuration / 2);
    }

    @OnItemClick(R.id.grid)
    void itemClick(int position) {
        View activityContent = findViewById(android.R.id.content);
//        FloatingActionButton fab = findViewById(R.id.fab);

        Animator anim = CustomAnimationUtils.circleAnimation(
                activityContent,
//                fab,
                grid,
                animationDuration,
                true);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                grid.setVisibility(View.INVISIBLE);
            }
        });

        cover.setAlpha(coverAlpha);
        cover.animate().alpha(0).setDuration(animationDuration * 2);

        CustomUser.getInstance().setImageID(position);
        CustomDatabaseUtils.addOrUpdateUserDocument(CustomUser.getInstance());

        int drawable = (int) grid.getAdapter().getItem(position);
        image.setImageResource(drawable);
    }

    private void setFindUsers(String text) {
        if (text.length() < 1)
            return;

        String[] collection = new String[]{"users"};
//        String[][] filters = new String[][]{
//                {"name", text},
//                {"location", text}
//        };

        CustomDatabaseUtils.read(collection, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (isQuery) {
                        List<CustomUser> mDataset = new ArrayList<>();

                        /*
                         * The above list is populated by a QueryDocumentSnapshot
                         * which retrieves all documents within a collection. Each
                         * document/object is converted into a CustomUser class
                         * and added.
                         */
                        for (QueryDocumentSnapshot q : (QuerySnapshot) object) {
                            CustomUser u = q.toObject(CustomUser.class);
                            mDataset.add(u);
                        }

                        /*
                         * The {@link RecyclerHelper#setRecyclerView(Context, View, RecyclerView.Adapter)}
                         * method will apply the provided data set to the given adapter which
                         * is then presented using our recycler view. This method is and will be
                         * used frequently, thus it has been placed within a helper class.
                         */
                        RecyclerView.Adapter adapter = new FindUserAdapter(mDataset);
                        ((FindUserAdapter) adapter).getFilter().filter(text);

                        RecyclerHelper.setRecyclerView(getApplicationContext(), find, adapter);
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

            setCurrentUser();

            cover.bringToFront();
            ((View) find.getParent()).bringToFront();
            ((View) grid.getParent()).bringToFront();

            grid.setAdapter(new GridImageAdapter(getApplicationContext()));
            find.setAdapter(new FindUserAdapter(null));
        });
    }

    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.onCreateOptionsMenu(this, menu, true);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                MenuHelper.toggleMenuVisibility(menu, false);
                findClick(false);

                SearchView searchView = (SearchView) searchItem.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        setFindUsers(searchView.getQuery().toString());
                        return false;
                    }
                });

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                invalidateOptionsMenu();
                findClick(true);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHelper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}