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

public class UserCreationCollegeSchoolFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationCollegeSchoolFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_fragment, container, false);

        TextView questionView = (TextView) rootView.findViewById(R.id.question_view);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        collegeSchoolFragment(questionView, linearLayout);

        return rootView;
    }

    private void collegeSchoolFragment(TextView questionView, LinearLayout linearLayout) {
        UserCreationHelper.updateQuestionText(questionView, R.string.user_creation_college_school_question);

        final int college = R.string.user_creation_college;
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        college,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchool(getString(college));
                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );
                            }
                        }
                )
        );

        final int school = R.string.user_creation_school;
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        school,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchool(getString(school));
                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );
                            }
                        }
                )
        );
    }
}