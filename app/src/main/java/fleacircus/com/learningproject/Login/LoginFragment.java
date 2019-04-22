package fleacircus.com.learningproject.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestoreException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Classes.CustomViewPager;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.ProgressDialogHelper;
import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FirebaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class LoginFragment extends Fragment {

    @OnClick(R.id.buttonSubmit)
    void validateLogin() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        //noinspection ConstantConditions
        CustomViewPager viewPager = loginActivity.getViewPager();

        EditText email = viewPager.findViewById(R.id.editTextViewEmail);
        EditText password = viewPager.findViewById(R.id.editTextViewPassword);
        TextView messageConfirm = viewPager.findViewById(R.id.textViewConfirm);

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        boolean e = InputValidationUtils.validateEmail(emailText);
        boolean p = InputValidationUtils.validatePassword(passwordText);
        if (!e || !p) {
            messageConfirm.setText(R.string.prompt_failure);
            return;
        }

        Activity activity = getActivity();

        String dialog = getString(R.string.dialog_confirm);
        ProgressDialog progressDialog = ProgressDialogHelper.createProgressDialog(getActivity(), dialog);

        CustomDatabaseUtils.loginUser(emailText, passwordText, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                if (FirebaseUtils.getUid().isEmpty()) return;

                CustomUser.updateInstance(new CustomUser());
                progressDialog.dismiss();

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                if (activity != null) NavigationUtils.startActivity(activity, intent);
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                messageConfirm.setText(R.string.prompt_failure);
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.textViewPromptSign)
    void promptClick() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(loginActivity.getViewPager(), 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}