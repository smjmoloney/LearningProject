package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import fleacircus.com.learningproject.CustomList.CustomListLearnCourseItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnTopicItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListView;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.CustomDebugUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class LearnTopicFragment extends Fragment {

    public LearnTopicFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_course_fragment, container, false);

        CustomCourse c = ((LearnCourseActivity) getActivity()).getSelectedCourse();
        if (c == null)
            return rootView;

        CustomListView.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                new CustomListLearnTopicItemAdapter(c.getTopics()));

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}