package fleacircus.com.learningproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.Helpers.ProgressDialogHelper;
import fleacircus.com.learningproject.WorkspaceActivity;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class LoginFragment extends Fragment {

    private EditText email, password;
    private TextView inputPrompt;

    private LoginActivity loginActivity;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_login_fragment, container, false);

        setLoginFragment(rootView);

        return rootView;
    }

    private void setLoginFragment(View rootView) {
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);

        inputPrompt = rootView.findViewById(R.id.input_prompt);

        Button submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        TextView signUp = rootView.findViewById(R.id.sign_prompt);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity.getViewPager().setCurrentItem(
                        loginActivity.getViewPager().getCurrentItem() + 1
                );
            }
        });
    }

    private void validateLogin() {
        inputPrompt.setText("");

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (!InputValidationUtils.validateEmail(emailText)
                || !InputValidationUtils.validatePassword(passwordText)) {
            inputPrompt.setText(R.string.login_failure_prompt);
            return;
        }

        CustomDatabaseUtils.login(ProgressDialogHelper.createProgressDialog(getActivity(),
                getString(R.string.login_confirm_result)), emailText, passwordText, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                CustomUser.updateInstance(new CustomUser());
                getActivity().startActivity(new Intent(getActivity(), WorkspaceActivity.class));
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                inputPrompt.setText(R.string.login_failure_prompt);
            }
        });
    }
}