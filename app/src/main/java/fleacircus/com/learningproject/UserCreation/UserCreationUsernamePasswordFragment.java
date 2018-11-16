package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDebugUtils;

public class UserCreationUsernamePasswordFragment  extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationUsernamePasswordFragment() {
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

        usernamePasswordFragment(questionView, linearLayout);

        return rootView;
    }

    private void usernamePasswordFragment(TextView questionView, LinearLayout linearLayout) {
        String temp = getString(R.string.username_password_question);
        UserCreationHelper.updateQuestionText(questionView, temp);

        final EditText username = UserCreationHelper.createAnswerEditText(getActivity(),
                getString(R.string.username_placeholder));

        final EditText password = UserCreationHelper.createAnswerEditText(getActivity(),
                getString(R.string.password_placeholder));
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        linearLayout.addView(username);
        linearLayout.addView(password);
        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.continue_button,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setUsername(username.getText().toString());
                                User.getInstance().setPassword(password.getText().toString());

                                userCreationActivity.getViewPager().setCurrentItem(
                                        userCreationActivity.getViewPager().getCurrentItem() + 1
                                );

                                CustomDebugUtils.valueNotInitialised("NULL", User.getInstance().getPassword());
                            }
                        }
                )
        );
    }
}
