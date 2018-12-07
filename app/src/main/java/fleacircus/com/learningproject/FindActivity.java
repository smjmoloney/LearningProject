package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.CustomList.CustomListUserItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.CustomClasses.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.MenuUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FindActivity extends AppCompatActivity {

    private Spinner locations;
    private Spinner courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_activity);

        locations = findViewById(R.id.location);
        courses = findViewById(R.id.course);

        showColleges();

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find(StringUtils.toLowerCase(locations.getSelectedItem().toString()),
                        StringUtils.toLowerCase(courses.getSelectedItem().toString()));
            }
        });

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateCourses();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void find(String location, String course) {
        CustomDatabaseUtils.readMultipleUsersWhere(location, course, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                if (isQuery)
                    CustomListAdapter.setRecyclerView(FindActivity.this, findViewById(R.id.recycler),
                            new CustomListUserItemAdapter(new ArrayList<>(((QuerySnapshot) object)
                                    .toObjects(CustomUser.class))));
                else
                    Log.e("OnSuccess", "Must be a query.");
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    private void showColleges() {
        CustomDatabaseUtils.read("user_creation", "college", new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                List<String> list = (List<String>) ((DocumentSnapshot) object).get("names");
                if (list != null) {
                    locations.setAdapter(new ArrayAdapter<>(FindActivity.this, android.R.layout.simple_spinner_item, list));
                    updateCourses();
                }
            }


            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    public void updateCourses() {
        CustomDatabaseUtils.read("user_creation", "courses", new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                List<String> list = (List<String>) ((DocumentSnapshot) object).get(
                        StringUtils.toLowerCase(locations.getSelectedItem().toString()));

                if (list != null)
                    courses.setAdapter(new ArrayAdapter<>(FindActivity.this, android.R.layout.simple_spinner_item, list));
            }


            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}