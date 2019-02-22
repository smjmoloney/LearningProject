package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fleacircus.com.learningproject.CustomClasses.CustomTopic;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnTopicItemAdapter;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.Listeners.AbstractFragment;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class LearnTopicFragment extends AbstractFragment {

    public LearnTopicFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_course_fragment, container, false);

        CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                new CustomListLearnTopicItemAdapter(new ArrayList<CustomTopic>()));

        getTopics(rootView);

        return rootView;
    }

    private void getTopics(final View rootView) {
        getAvailableActivity(new IActivityEnabledListener() {
            @Override
            public void onActivityEnabled(FragmentActivity activity) {
                if (((LearnCourseActivity) activity).getSelectedCourse() == null)
                    return;

                String temp = ((LearnCourseActivity) activity).getSelectedCourse().getTitle();

                if (temp == null)
                    temp = "NA";

                CustomDatabaseUtils.readMultipleTopics(temp, new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(Object object, boolean isQuery) {
                        if (isQuery) {
                            ArrayList<CustomTopic> topics = new ArrayList<>();
                            for (QueryDocumentSnapshot q : (QuerySnapshot) object) {
                                topics.add(new CustomTopic(q.getId()));
                                Log.e("TEST", q.getId());
                            }

                            CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                                    new CustomListLearnTopicItemAdapter(topics));
                        }
                    }

                    @Override
                    public void onFailed(FirebaseFirestoreException databaseError) {
                        Log.e("FirebaseFirestoreEx", databaseError.toString());
                    }
                });
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}