package fleacircus.com.learningproject.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.ProgressDialogHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class SignFragment extends Fragment {

    @BindView(R.id.editTextViewEmail)
    EditText email;
    @BindView(R.id.editTextViewPassword)
    EditText password;
    @BindView(R.id.editTextViewConfirm)
    EditText confirm;
    @BindView(R.id.textViewEmail)
    TextView messageEmail;
    @BindView(R.id.textViewPassword)
    TextView messagePassword;
    @BindView(R.id.textViewConfirm)
    TextView messagePasswordConfirm;

    public SignFragment() {
    }

    @OnClick(R.id.buttonSubmit)
    void signClick() {
        messageEmail.setText("");
        messagePassword.setText("");
        messagePasswordConfirm.setText("");

        boolean sign = true;
        if (!InputValidationUtils.validateEmail(email.getText().toString())) {
            sign = false;
            messageEmail.setText(R.string.message_email);
        }

        if (!InputValidationUtils.validatePassword(password.getText().toString())) {
            sign = false;
            messagePassword.setText(R.string.message_password);
        }

        if (!InputValidationUtils.validateMatch(
                password.getText().toString(), confirm.getText().toString())) {
            sign = false;
            messagePasswordConfirm.setText(R.string.message_confirm_password);
        }

        if (sign) {
            final ProgressDialog progressDialog = ProgressDialogHelper.createProgressDialog(
                    getActivity(), getString(R.string.dialog_confirm_sign));

            Activity activity = getActivity();

            Intent intent = new Intent(getActivity(), UserCreationActivity.class);
            CustomDatabaseUtils.addUser(email, password, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(Object object, boolean isQuery) {
                    progressDialog.dismiss();

                    if (activity != null)
                        NavigationUtils.startActivity(activity, intent);
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFailed(FirebaseFirestoreException databaseError) {
                    messageEmail.setText("Failed to add user.");
                    progressDialog.dismiss();

                    Log.e("FirebaseFirestoreEx", "Failed to add user.");
                }
            });
        }
    }

    @OnClick(R.id.textViewPromptLogin)
    void promptClick() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(loginActivity.getViewPager(), -1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}