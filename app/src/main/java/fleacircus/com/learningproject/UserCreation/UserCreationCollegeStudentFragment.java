package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;

public class UserCreationCollegeStudentFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationCollegeStudentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_creation, container, false);

        TextView questionView = (TextView) rootView.findViewById(R.id.question_view);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        collegeSchoolFragment(questionView, linearLayout);

        return rootView;
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
                                userCreationActivity.viewPager.setCurrentItem(
                                        userCreationActivity.viewPager.getCurrentItem() + 1
                                );

                                updateFragment();
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
                                userCreationActivity.viewPager.setCurrentItem(
                                        userCreationActivity.viewPager.getCurrentItem() + 1
                                );

                                updateFragment();

//                                CustomDebugUtils.valueNotInitialised("NULL", i + ". " + User.getInstance().getCollegeSchool());
//                                CustomDebugUtils.valueNotInitialised("NULL", i + ". " + User.getInstance().getTeacherStudent());
                            }
                        }
                )
        );
    }

    public void updateFragment() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }
}