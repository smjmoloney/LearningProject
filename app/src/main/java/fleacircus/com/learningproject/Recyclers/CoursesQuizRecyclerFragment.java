package fleacircus.com.learningproject.Recyclers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.Adapters.CourseAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.CourseRecyclerHelper;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.RecyclerHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FirebaseUtils;

public class CoursesQuizRecyclerFragment extends Fragment {

    /**
     * Method accesses the courses collection of the currently
     * logged in user and populates our recycler view with each
     * course. They are defined as documents, each with a number
     * of fields.
     */
    private void setCourses(View view) {
        Log.e("TEST", "TEST");

        boolean isCollegeLibrary = FragmentHelper.getBoolean(this, "isCollegeLibrary");
        boolean isCreate = FragmentHelper.getBoolean(this, "isCreate");
        String foundUid = FragmentHelper.getString(this, "foundUid");
        String uid = FirebaseUtils.getUid();

        String[] collection = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid};
        if (isCollegeLibrary) {
            String location = CustomUser.getInstance().getLocation();
            collection = new String[]{"quiz_list", location, "quiz_list" + "_" + location};
        }

        for (String s : collection)
            Log.e("QUIZ", s);

        CustomDatabaseUtils.read(collection, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (isQuery) {
                        List<DocumentSnapshot> mDataset = new ArrayList<>();

                        /*
                         * The above list is populated by a QueryDocumentSnapshot
                         * which retrieves all documents within a collection. Each
                         * document/object is converted into a CustomCourse class
                         * and added.
                         */
                        List<DocumentSnapshot> documentSnapshots = ((QuerySnapshot) object).getDocuments();
                        for (int i = 0; i < documentSnapshots.size(); i++) {
                            String courseID = documentSnapshots.get(i).getId();
                            String location = CustomUser.getInstance().getLocation();
                            if (getArguments() == null) return;

                            String value = uid;
                            if (isCollegeLibrary) value = location;

                            String[] documentReferenceLocation = new String[]{"quizzes", value, courseID + "_" + value, courseID};
                            DocumentReference documentReference = (DocumentReference) CustomDatabaseUtils.retrieveCollectionOrDocument(documentReferenceLocation);

                            int finalI = i;
                            documentReference.get().addOnCompleteListener(task -> {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if ((documentSnapshot == null) || !documentSnapshot.exists())
                                    return;

                                if (isCollegeLibrary) {
                                    mDataset.add(documentSnapshot);
                                } else {
                                    if ((foundUid != null) && !foundUid.isEmpty() && isCreate)
                                        mDataset.add(documentSnapshot);
                                    else if (isCreate) {
                                        if (uid.equals(documentSnapshot.getString("creatorID")))
                                            mDataset.add(documentSnapshot);
                                    }
                                    else if (!uid.equals(documentSnapshot.getString("creatorID")))
                                        mDataset.add(documentSnapshot);
                                }

                                if (documentSnapshots.size() - 1 == finalI) {
                                    /**
                                     * The {@link RecyclerHelper#setRecyclerView(Context, View, RecyclerView.Adapter)}
                                     * method will apply the provided data set to the given adapter which
                                     * is then presented using our recycler view. This method is and will be
                                     * used frequently, thus it has been placed within a helper class.
                                     */
                                    RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCourses);
                                    recyclerView.setNestedScrollingEnabled(false);
                                    if (isCollegeLibrary)
                                        RecyclerHelper.setRecyclerView(getActivity(), recyclerView, new CourseAdapter(getActivity(), mDataset, location));
                                    else if ((foundUid != null) && !foundUid.isEmpty())
                                        RecyclerHelper.setRecyclerView(getActivity(), recyclerView, new CourseAdapter(getActivity(), mDataset, foundUid));
                                    else
                                        RecyclerHelper.setRecyclerView(getActivity(), recyclerView, new CourseAdapter(getActivity(), mDataset));

                                    RecyclerHelper.recyclerEntryAnimation(recyclerView);
                                }
                            });
                        }
                    } else Log.e("OnSuccess", object + " must be a query.");
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses_create, container, false);

        if (getArguments() != null) {
            boolean isFlashCard = FragmentHelper.getBoolean(this,"isFlashCard");
            boolean isCollege = FragmentHelper.getBoolean(this,"isCollegeLibrary");
            boolean isCreate = FragmentHelper.getBoolean(this,"isCreate");
            String foundUid = FragmentHelper.getString(this,"foundUid");

            CourseRecyclerHelper.isCreator(getActivity(), view.findViewById(R.id.courseCreate), isFlashCard, isCollege, isCreate, foundUid);
        }

        setCourses(view);
        return view;
    }
}