package fleacircus.com.learningproject.UserCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import fleacircus.com.learningproject.HomeActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.InputValidationUtils;

public class UserCreationNameFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationNameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.user_creation_name_fragment, container, false);
        final TextView name = rootView.findViewById(R.id.name);

        Button button = rootView.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputValidationUtils.validateLength(name.getText().toString(), 3)) {
                    TextView name_prompt = rootView.findViewById(R.id.name_prompt);
                    name_prompt.setText(R.string.user_creation_name_prompt);
                    return;
                }

                CustomUser.getInstance().setName(name.getText().toString());
                CustomDatabaseUtils.updateObject("users",
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), CustomUser.getInstance(),
                        userCreationActivity, getString(R.string.setup_update_result));

                startActivity(new Intent(userCreationActivity, HomeActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}