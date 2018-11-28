package fleacircus.com.learningproject.Login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class SetupFragment extends Fragment {

    private EditText email, password, confirm;
    private TextView emailPrompt, passwordPrompt, confirmPrompt;

    private LoginActivity loginActivity;

    public SetupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_setup_fragment, container, false);

        setLoginActivity(rootView);

        return rootView;
    }

    private void setLoginActivity(View rootView) {
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        confirm = rootView.findViewById(R.id.confirm);

        emailPrompt = rootView.findViewById(R.id.email_prompt);
        passwordPrompt = rootView.findViewById(R.id.password_prompt);
        confirmPrompt = rootView.findViewById(R.id.confirm_prompt);

        Button submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSetupInput();
            }
        });

        TextView login = rootView.findViewById(R.id.login_prompt);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity.getViewPager().setCurrentItem(
                        loginActivity.getViewPager().getCurrentItem() - 1
                );
            }
        });
    }

    private void validateSetupInput() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirm.getText().toString();

        emailPrompt.setText("");
        passwordPrompt.setText("");
        confirmPrompt.setText("");

        boolean confirmSetup = true;
        if (!InputValidationUtils.validateEmail(emailText)) {
            emailPrompt.setText(R.string.setup_email_prompt);
            confirmSetup = false;
        }

        if (!InputValidationUtils.validatePassword(passwordText)) {
            passwordPrompt.setText(R.string.setup_password_prompt);
            confirmSetup = false;
        } else if (!InputValidationUtils.validateMatch(passwordText, confirmPasswordText)) {
            confirmPrompt.setText(R.string.setup_confirm_prompt);
            confirmSetup = false;
        }

        if (confirmSetup)
            CustomDatabaseUtils.addUser(loginActivity, getString(R.string.setup_confirm_result),
                    emailText, passwordText);
    }
}