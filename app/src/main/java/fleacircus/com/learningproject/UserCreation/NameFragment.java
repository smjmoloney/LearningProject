package fleacircus.com.learningproject.UserCreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class NameFragment extends Fragment {

    @BindView(R.id.editTextViewName)
    EditText name;
    @BindView(R.id.textViewName)
    TextView messageName;

    public NameFragment() {
    }

    @OnClick(R.id.buttonSubmit)
    void nameClick() {
        boolean length = InputValidationUtils.validateLength(name.getText().toString(), 3);
        if (length) {
            messageName.setText(R.string.message_name);
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //noinspection ConstantConditions
        String e = auth.getCurrentUser().getEmail();
        String u = auth.getUid();
        String n = name.getText().toString();

        CustomUser.getInstance().setEmail(e);
        CustomUser.getInstance().setUid(u);
        CustomUser.getInstance().setName(n);

        CustomDatabaseUtils.addOrUpdateUserDocument(CustomUser.getInstance());

        Activity activity = getActivity();

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        if (activity != null)
            NavigationUtils.startActivity(activity, intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}