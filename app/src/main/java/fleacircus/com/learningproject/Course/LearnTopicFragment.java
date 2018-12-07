package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fleacircus.com.learningproject.CustomClasses.CustomCourse;
import fleacircus.com.learningproject.CustomList.CustomListLearnTopicItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.R;
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

        CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                new CustomListLearnTopicItemAdapter(c.getTopics()));

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}