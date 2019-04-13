package fleacircus.com.learningproject;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FoundUserActivity extends AppCompatActivity {

    String foundUid;

    @OnClick(R.id.buttonSubmit)
    void sendDocument() {
        if (foundUid.isEmpty())
            return;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null)
            return;

        String uid = user.getUid();
        String[] from =  new String[]{"users", foundUid, "courses", "poayKZr0qQwwOWudgrTz"};
        String[] to =  new String[]{"users", uid, "courses"};

        CustomDatabaseUtils.copyDocument(from, to, "Classes.CustomCourse");
        Toast.makeText(this, R.string.courses_message_addition, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method accesses the courses collection of the currently
     * logged in user and populates our recycler view with each
     * course. They are defined as documents, each with a number
     * of fields.
     */
    private void setCourses() {
        //noinspection ConstantConditions
        String[] collection = new String[]{"users", foundUid, "courses"};

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
                        //noinspection ConstantConditions
                        RecyclerView courses = findViewById(R.id.viewPagerCourses);
                        RecyclerHelper.setRecyclerView(getApplicationContext(), courses, new CourseAdapter(mDataset, foundUid));
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

    private void setSelectedUser() {
        //noinspection ConstantConditions
        CustomUser customUser = (CustomUser) getIntent().getSerializableExtra("user");
        foundUid = customUser.getUid();

        String n = StringUtils.toUpperCase(customUser.getName());
        String l = StringUtils.toUpperCase(customUser.getLocation());
        TextView name = findViewById(R.id.textViewName);
        TextView location = findViewById(R.id.textViewLocation);
        name.setText(n);
        location.setText(l);

        String t = customUser.getTeacherStudent();
        if (!StringUtils.hasMatch(t, getString(R.string.answer_teacher))) {
            String c = customUser.getCollegeSchool();
            if (StringUtils.hasMatch(c, getString(R.string.answer_college))) {
                TextView course = findViewById(R.id.textViewCourse);
                course.setText(StringUtils.toUpperCase(customUser.getCourse()));
            }
        }

        int imageID = customUser.getImageID();
        if (imageID != 0) {
            ImageView image = findViewById(R.id.imageViewProfile);
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));
        }

        setCourses();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_user);
        setSupportActionBar(findViewById(R.id.toolbar));

        ButterKnife.bind(this, findViewById(android.R.id.content));

        setSelectedUser();
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
