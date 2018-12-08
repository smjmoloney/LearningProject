package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fleacircus.com.learningproject.CustomClasses.CustomAnswer;
import fleacircus.com.learningproject.CustomClasses.CustomCourse;
import fleacircus.com.learningproject.CustomClasses.CustomQuestion;
import fleacircus.com.learningproject.CustomClasses.CustomTopic;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnAnswerItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnQuestionItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListLearnTopicItemAdapter;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.Listeners.AbstractFragment;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class LearnAnswerFragment extends AbstractFragment {

    public LearnAnswerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_course_fragment, container, false);

        CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                new CustomListLearnAnswerItemAdapter(new ArrayList<CustomAnswer>()));

        getQuestions(rootView);

        return rootView;
    }

    private void getQuestions(final View rootView) {
        getAvailableActivity(new IActivityEnabledListener() {
            @Override
            public void onActivityEnabled(FragmentActivity activity) {
                if (((LearnCourseActivity) activity).getSelectedQuestion() == null)
                    return;

                CustomQuestion question = ((LearnCourseActivity) activity).getSelectedQuestion();
                TextView textView = rootView.findViewById(R.id.textView);
                textView.setText(question.getTitle());

                for (Map.Entry<String, Object> entry : question.getAnswers()) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
                    textView.setText(textView.getText() + "\n Answer: " + values.get("answer"));
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}