package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserCreationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private UserCreationActivity userCreationActivity;

    public UserCreationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UserCreationFragment newInstance(int sectionNumber) {
        UserCreationFragment fragment = new UserCreationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userCreationActivity = (UserCreationActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_user_creation, container, false);

        TextView questionView = rootView.findViewById(R.id.question_view);
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.constraintLayout);
        LinearLayout linearLayout = rootView.findViewById(R.id.linear_layout);

        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    teacherStudentSection(questionView, linearLayout);
                    break;
                case 2:
                    collegeSchoolFragment(questionView, linearLayout);
                    break;
                case 3:
                    whichFragment(questionView, linearLayout);
                    break;
            }
        }

        return rootView;
    }

    private void teacherStudentSection(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.student_teacher_question);

        Button answerA = UserCreationHelper.createAnswerButton(getActivity(), R.string.student_teacher_answer_a);
        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreationActivity.user.setTeacherStudent(getString(R.string.student_teacher_answer_a));
                userCreationActivity.currentPage += 1;
                userCreationActivity.viewPager.setCurrentItem(userCreationActivity.currentPage, true);
            }
        });

        Button answerB = UserCreationHelper.createAnswerButton(getActivity(), R.string.student_teacher_answer_b);
        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreationActivity.user.setTeacherStudent(getString(R.string.student_teacher_answer_b));
                userCreationActivity.currentPage += 1;
                userCreationActivity.viewPager.setCurrentItem(userCreationActivity.currentPage, true);
            }
        });

        linearLayout.addView(answerA);
        linearLayout.addView(answerB);
    }

    private void collegeSchoolFragment(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.college_school_question);

        Button answerA = UserCreationHelper.createAnswerButton(getActivity(), R.string.college_school_answer_a);
        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreationActivity.user.setTeacherStudent(getString(R.string.college_school_answer_a));
                userCreationActivity.currentPage += 1;
                userCreationActivity.viewPager.setCurrentItem(userCreationActivity.currentPage, true);
            }
        });

        Button answerB = UserCreationHelper.createAnswerButton(getActivity(), R.string.college_school_answer_b);
        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreationActivity.user.setCollegeSchool(getString(R.string.college_school_answer_b));
                userCreationActivity.currentPage += 1;
                userCreationActivity.viewPager.setCurrentItem(userCreationActivity.currentPage, true);
            }
        });

        linearLayout.addView(answerA);
        linearLayout.addView(answerB);
    }

    private void whichFragment(TextView questionView, LinearLayout linearLayout) {
        String temp = getString(R.string.which_question, userCreationActivity.user.getCollegeSchool());
        UserCreationHelper.updateQuestionText(questionView, temp);

        final EditText editText = UserCreationHelper.createAnswerEditText(getActivity(), temp);

        Button button = UserCreationHelper.createAnswerButton(getActivity(), R.string.continue_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreationActivity.user.setLocation(editText.getText().toString());
                userCreationActivity.currentPage += 1;
                userCreationActivity.viewPager.setCurrentItem(userCreationActivity.currentPage, true);

//                Log.i("USER", userCreationActivity.user.getTeacherStudent());
//                Log.i("USER", userCreationActivity.user.getCollegeSchool());
            }
        });

        linearLayout.addView(editText);
        linearLayout.addView(button);
    }
}