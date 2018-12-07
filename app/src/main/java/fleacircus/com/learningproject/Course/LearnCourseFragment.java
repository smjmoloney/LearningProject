package fleacircus.com.learningproject.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import fleacircus.com.learningproject.CustomClasses.CustomCourse;
import fleacircus.com.learningproject.CustomClasses.CustomTopic;
import fleacircus.com.learningproject.CustomList.CustomListLearnCourseItemAdapter;
import fleacircus.com.learningproject.CustomList.CustomListAdapter;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;

public class LearnCourseFragment extends Fragment {

    public LearnCourseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_course_fragment, container, false);

        getCourses(rootView);

        return rootView;
    }

    private void getCourses(final View rootView) {
        CustomDatabaseUtils.readMultipleCourses("learn", new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                if (isQuery) {
                    ArrayList<CustomCourse> courses = new ArrayList<>();
                    for (QueryDocumentSnapshot o : (QuerySnapshot) object) {
                        final CustomCourse c = new CustomCourse();
                        c.setTitle(o.getId());
                        courses.add(c);

                        CustomDatabaseUtils.readMultipleTopics(c.getTitle(), new OnGetDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(Object object, boolean isQuery) {
                                if (!isQuery) {
                                    Map<String, Object> map = ((DocumentSnapshot) object).getData();

                                    ArrayList<CustomTopic> topics = new ArrayList<>();
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        CustomTopic t = new CustomTopic();
                                        t.setTitle(entry.getKey());
                                        topics.add(t);
                                    }

                                    c.setTopics(topics);
                                }
                            }

                            @Override
                            public void onFailed(FirebaseFirestoreException databaseError) {
                                Log.e("FirebaseFirestoreEx", databaseError.toString());
                            }
                        });
                    }

                    CustomListAdapter.setRecyclerView(getActivity(), rootView.findViewById(R.id.recycler),
                            new CustomListLearnCourseItemAdapter(courses));
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

//    private void questionTest(final View rootView, String course, String topic) {
//        final String question = "example question";
//
//        CustomDatabaseUtils.testRead(course, topic, question, new OnGetDataListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(Object object, boolean isQuery) {
//                TextView questionView = rootView.findViewById(R.id.question);
//                questionView.setText(question);
//
//                Map<String, Object> map = ((DocumentSnapshot) object).getData();
//                LinearLayout answers = rootView.findViewById(R.id.answers);
//
//                List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
//                Collections.shuffle(list);
//
//                int i = 0;
//                for (Map.Entry<String, Object> entry : list) {
//                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
//
//                    TextView answer = (TextView) answers.getChildAt(i);
//                    answer.setText((String) values.get("answer"));
//
//                    if ((boolean) values.get("isCorrect"))
//                        answer.setTextColor(getResources().getColor(R.color.green));
//
//                    i++;
//                }
//            }
//
//            @Override
//            public void onFailed(FirebaseFirestoreException databaseError) {
//                Log.e("FirebaseFirestoreEx", databaseError.toString());
//            }
//        });
//    }
}