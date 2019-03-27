package fleacircus.com.learningproject.Login;

import android.app.ProgressDialog;
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
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class SignFragment extends Fragment {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_confirm)
    EditText confirm;
    @BindView(R.id.message_email)
    TextView messageEmail;
    @BindView(R.id.message_password)
    TextView messagePassword;
    @BindView(R.id.message_confirm)
    TextView messagePasswordConfirm;

    public SignFragment() {
    }

    @OnClick(R.id.button_submit)
    void signClick() {
        String e = email.getText().toString();
        String p = password.getText().toString();
        String c = confirm.getText().toString();
        messageEmail.setText("");
        messagePassword.setText("");
        messagePasswordConfirm.setText("");

        boolean sign = true;
        if (!InputValidationUtils.validateEmail(e)) {
            sign = false;
            messageEmail.setText(R.string.message_email);
        }

        if (!InputValidationUtils.validatePassword(p)) {
            sign = false;
            messagePassword.setText(R.string.message_password);
        }

        if (!InputValidationUtils.validateMatch(p, c)) {
            sign = false;
            messagePasswordConfirm.setText(R.string.message_confirm_password);
        }

        if (sign) {
            final ProgressDialog progressDialog = ProgressDialogHelper.createProgressDialog(
                    getActivity(), getString(R.string.dialog_confirm_sign));

            CustomDatabaseUtils.addUser(email, password, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(Object object, boolean isQuery) {
                    progressDialog.dismiss();
                }

                @Override
                public void onFailed(FirebaseFirestoreException databaseError) {
                    messageEmail.setText(R.string.message_email_usage);
                    progressDialog.dismiss();

                    Log.e("FirebaseFirestoreEx", "Failed to add user.");
                }
            });
        }
    }

    @OnClick(R.id.prompt_login)
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