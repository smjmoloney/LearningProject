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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
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
        /*
         * CREATE CUSTOM LAYOUTS FOR EACH FRAGMENT
         */
        View rootView = inflater.inflate(R.layout.login_login_fragment, container, false);

        setLoginActivity(rootView);

        return rootView;
    }

    private void setLoginActivity(View rootView) {
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
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        inputPrompt.setText("");

        if (!InputValidationUtils.validateEmail(emailText)
                || !InputValidationUtils.validatePassword(passwordText)) {
            inputPrompt.setText(R.string.login_failure_prompt);
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(loginActivity, "TEST", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(loginActivity, "SUCCESS", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}