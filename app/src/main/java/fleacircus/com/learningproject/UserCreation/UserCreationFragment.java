package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_user_creation, container, false);
        processFragments(rootView);

        return rootView;
    }

    private void processFragments(View rootView) {
        TextView questionView = rootView.findViewById(R.id.question_view);
        LinearLayout linearLayout = rootView.findViewById(R.id.linear_layout);

        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    teacherStudentFragment(questionView, linearLayout);
                    break;
                case 2:
                    collegeSchoolFragment(questionView, linearLayout);
                    break;
                case 3:
                    whichFragment(questionView, linearLayout);
                    break;
                case 4:
                    courseFragment(questionView, linearLayout);
                    break;
            }
        }
    }

    private void teacherStudentFragment(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.student_teacher_question);

        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.student_teacher_answer_a,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setTeacherStudent(getString(R.string.student_teacher_answer_a));
                                progressToNextFragment();
                            }
                        }
                )
        );

        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.student_teacher_answer_b,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setTeacherStudent(getString(R.string.student_teacher_answer_b));
                                progressToNextFragment();
                            }
                        }
                )
        );
    }

    private void collegeSchoolFragment(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.college_school_question);

        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.college_school_answer_a,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchool(getString(R.string.college_school_answer_a));
                                progressToNextFragment();
                            }
                        }
                )
        );

        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.college_school_answer_b,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchool(getString(R.string.college_school_answer_b));
                                progressToNextFragment();
                            }
                        }
                )
        );
    }

    private void whichFragment(TextView questionView, LinearLayout linearLayout) {
        String temp = getString(R.string.which_question, User.getInstance().getCollegeSchool());
        UserCreationHelper.updateQuestionText(questionView, temp);

        final EditText editText = UserCreationHelper.createAnswerEditText(getActivity(),
                getString(R.string.which_placeholder, User.getInstance().getCollegeSchool()));

        linearLayout.addView(editText);
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.continue_button,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchoolLocation(editText.getText().toString());
                                progressToNextFragment();
                            }
                        }
                )
        );
    }

    private void courseFragment(TextView questionView, LinearLayout linearLayout) {
        String temp = getString(R.string.which_course_question);
        UserCreationHelper.updateQuestionText(questionView, temp);

        final EditText editText = UserCreationHelper.createAnswerEditText(getActivity(),
                getString(R.string.which_course_placeholder));

        linearLayout.addView(editText);
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.continue_button,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchoolLocation(editText.getText().toString());
                                progressToNextFragment();
                            }
                        }
                )
        );
    }

    private void progressToNextFragment() {
        if (getArguments() == null)
            return;

        int temp = getArguments().getInt(ARG_SECTION_NUMBER);
        userCreationActivity.viewPager.setCurrentItem(temp, true);
        userCreationActivity.currentPage = temp;

        updateFragment();
    }

    public void updateFragment() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }
}