package fleacircus.com.learningproject.UserCreation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class NameFragment extends Fragment {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.message_name)
    TextView messageName;

    public NameFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.button_submit)
    void nameClick() {
        boolean length = InputValidationUtils.validateLength(name.getText().toString(), 3);
        if (length) {
            messageName.setText(R.string.message_name);
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //noinspection ConstantConditions
        String e = auth.getCurrentUser().getEmail();
        String n = name.getText().toString();

        CustomUser customUser = CustomUser.getInstance();
        customUser.setEmail(e);
        customUser.setName(n);

        CustomDatabaseUtils.addOrUpdateUserDocument(customUser);
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}