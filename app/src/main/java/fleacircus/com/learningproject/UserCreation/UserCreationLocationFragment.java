package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class UserCreationLocationFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationLocationFragment() {
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

        locationFragment(questionView, linearLayout);

        return rootView;
    }

    private void locationFragment(TextView questionView, LinearLayout linearLayout) {
        String temp = getString(R.string.location_question, User.getInstance().getCollegeSchool());
        UserCreationHelper.updateQuestionText(questionView, temp);

        final EditText editText = UserCreationHelper.createAnswerEditText(getActivity(),
                getString(R.string.location_placeholder, User.getInstance().getCollegeSchool()));

        linearLayout.addView(editText);
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.continue_button,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchoolLocation(editText.getText().toString());
                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );
                            }
                        }
                )
        );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}