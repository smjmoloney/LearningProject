package fleacircus.com.learningproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.HomeActivity;
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
        final String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        inputPrompt.setText("");

        if (!InputValidationUtils.validateEmail(emailText)
                || !InputValidationUtils.validatePassword(passwordText)) {
            inputPrompt.setText(R.string.login_failure_prompt);
            return;
        }

        CustomDatabaseUtils.loginUser(loginActivity, getString(R.string.login_confirm_result),
                emailText, passwordText, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DocumentSnapshot data) {
                FirebaseFirestore.getInstance().collection("users").document(
                        FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                CustomUser.updateInstance(documentSnapshot.toObject(CustomUser.class));
                                CustomUser.getInstance().setEmail(emailText);
                            }
                        });

                        loginActivity.startActivity(new Intent(loginActivity, HomeActivity.class));
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                inputPrompt.setText(R.string.login_failure_prompt);
            }
        });
    }
}