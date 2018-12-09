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

import fleacircus.com.learningproject.Helpers.ProgressDialogHelper;
import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class SignUpFragment extends Fragment {

    private EditText email, password, confirm;
    private TextView emailPrompt, passwordPrompt, confirmPrompt;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_setup_fragment, container, false);

        setSignUpFragment(rootView);

        return rootView;
    }

    private void setSignUpFragment(View rootView) {
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
                validateSignUpInput();
            }
        });

        TextView login = rootView.findViewById(R.id.login_prompt);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).getViewPager().setCurrentItem(
                        ((LoginActivity) getActivity()).getViewPager().getCurrentItem() - 1
                );
            }
        });
    }

    private void validateSignUpInput() {
        emailPrompt.setText("");
        passwordPrompt.setText("");
        confirmPrompt.setText("");

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirm.getText().toString();

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
            CustomDatabaseUtils.addUser(getActivity(), ProgressDialogHelper.createProgressDialog(getActivity(),
                    getString(R.string.setup_confirm_result)), confirmPrompt, emailText, passwordText);
    }
}