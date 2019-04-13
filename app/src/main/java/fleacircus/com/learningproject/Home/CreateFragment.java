package fleacircus.com.learningproject.Home;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CreateFragment extends Fragment {

    private void entryAnimation(View view) {
        //noinspection ConstantConditions
        RecyclerView courses = view.findViewById(R.id.recyclerViewCourses);
        if (courses.getLayoutManager() != null) {
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
        }
    }

    /**
     * Method accesses the courses collection of the currently
     * logged in user and populates our recycler view with each
     * course. They are defined as documents, each with a number
     * of fields.
     */
    private void setCourses(View view) {
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
                            if (StringUtils.hasMatch(c.getCreatorID(), uid))
                                mDataset.add(c);
                        }


                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        /*
                         * The {@link RecyclerHelper#setRecyclerView(Context, View, RecyclerView.Adapter)}
                         * method will apply the provided data set to the given adapter which
                         * is then presented using our recycler view. This method is and will be
                         * used frequently, thus it has been placed within a helper class.
                         */
                        //noinspection ConstantConditions
                        RecyclerView created = view.findViewById(R.id.recyclerViewCourses);
                        RecyclerHelper.setRecyclerView(homeActivity, created, new CourseAdapter(mDataset));

                        entryAnimation(view);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        setCourses(view);

        return view;
    }
}
