package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;

public class UserCreationTeacherStudentFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationTeacherStudentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
         * CREATE CUSTOM LAYOUTS FOR EACH FRAGMENT
         */
        View rootView = inflater.inflate(R.layout.user_creation_teacher_student_fragment, container, false);

        TextView questionView = (TextView) rootView.findViewById(R.id.question_view);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        teacherStudentFragment(questionView, linearLayout);

        return rootView;
    }

    private void teacherStudentFragment(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.user_creation_student_teacher_question);

        final int student = R.string.user_creation_student;
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        student,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setTeacherStudent(getString(student));
                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );
                            }
                        }
                )
        );

        final int teacher = R.string.user_creation_teacher;
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        teacher,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setTeacherStudent(getString(teacher));
                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );
                            }
                        }
                )
        );
    }
}