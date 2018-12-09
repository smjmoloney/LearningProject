package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fleacircus.com.learningproject.CustomClasses.CustomCourse;
import fleacircus.com.learningproject.CustomClasses.CustomQuestion;
import fleacircus.com.learningproject.CustomClasses.CustomTopic;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnQuestionItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnTopicItemAdapter;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.Listeners.AbstractFragment;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class LearnQuestionFragment extends AbstractFragment {

    public LearnQuestionFragment() {
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

        getQuestions(rootView);

        return rootView;
    }

    private void getQuestions(final View rootView) {
        getAvailableActivity(new IActivityEnabledListener() {
            @Override
            public void onActivityEnabled(FragmentActivity activity) {
                CustomCourse course = ((LearnCourseActivity) activity).getSelectedCourse();
                if (course.getSelectedTopic() == null)
                    return;

                String cTitle = course.getTitle();
                String tTitle = course.getSelectedTopic().getTitle();

                CustomDatabaseUtils.readMultipleQuestions(cTitle, tTitle, new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(Object object, boolean isQuery) {
                        if (isQuery) {
                            ArrayList<CustomQuestion> questions = new ArrayList<>();

                            for (QueryDocumentSnapshot q : (QuerySnapshot) object) {
                                CustomQuestion c = new CustomQuestion(q.getId());

                                Map<String, Object> map = q.getData();
                                List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
                                c.setAnswers(list);
                                questions.add(c);
                            }

                            CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                                    new CustomListLearnQuestionItemAdapter(questions));
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