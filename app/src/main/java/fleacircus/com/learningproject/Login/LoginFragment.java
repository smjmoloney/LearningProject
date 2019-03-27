package fleacircus.com.learningproject.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Helpers.ProgressDialogHelper;
import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class LoginFragment extends Fragment {

    @BindView(R.id.message_confirm)
    TextView messageConfirm;

    private ViewPager viewPager;

    public LoginFragment() {
        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        viewPager = userCreationActivity.getViewPager();
    }

    @OnClick(R.id.button_submit)
    void validateLogin() {
        TextView email = viewPager.findViewById(R.id.email);
        TextView password = viewPager.findViewById(R.id.password);
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        boolean e = InputValidationUtils.validateEmail(emailText);
        boolean p = InputValidationUtils.validatePassword(passwordText);
        if (!e || !p) {
            messageConfirm.setText(R.string.prompt_failure);
            return;
        }

        String temp = getString(R.string.dialog_confirm);
        ProgressDialog progressDialog = ProgressDialogHelper.createProgressDialog(getActivity(), temp);

        CustomDatabaseUtils.loginUser(emailText, passwordText, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                //noinspection ConstantConditions
                String uid = auth.getCurrentUser().getUid();

                if (uid.isEmpty())
                    return;

                CustomUser.updateInstance(new CustomUser());
                progressDialog.dismiss();

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                messageConfirm.setText(R.string.prompt_failure);
                progressDialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.prompt_sign)
    void promptClick() {
        FragmentHelper.progressFragment(viewPager, 1);
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