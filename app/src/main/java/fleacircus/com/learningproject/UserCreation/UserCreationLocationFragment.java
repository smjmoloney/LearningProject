package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;

public class UserCreationLocationFragment extends Fragment {
    UserCreationActivity userCreationActivity;

    public UserCreationLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_fragment, container, false);

        TextView questionView = (TextView) rootView.findViewById(R.id.question_view);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        Spinner spinner = UserCreationHelper.createSpinner(userCreationActivity);

        locationFragment(questionView, linearLayout, spinner);

        return rootView;
    }

    private void locationFragment(TextView questionView, final LinearLayout linearLayout, final Spinner spinner) {
        String temp = getString(R.string.user_creation_location_question, User.getInstance().getCollegeSchool());
        UserCreationHelper.updateQuestionText(questionView, temp);

        CustomDatabaseUtils.read("user_creation","colleges", new OnGetDataListener() {
            @Override
            public void onStart() {
                Log.e("MESSAGE", "START");
            }

            @Override
            public void onSuccess(DocumentSnapshot data) {
                @SuppressWarnings("unchecked")
                List<String> temp = (List<String>) CustomDatabaseUtils.getArrayFromDocument(data, "names");
                for (String t : temp)
                    Log.e("MESSAGE", t);

                linearLayout.addView(UserCreationHelper.updateSpinnerWithValues(
                        userCreationActivity, spinner, temp));
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("MESSAGE", databaseError.toString());
            }
        });

        linearLayout.addView(
                UserCreationHelper.createAnswerButton(
                        getActivity(),
                        R.string.continue_button,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                User.getInstance().setCollegeSchoolLocation(spinner.getSelectedItem().toString());

                                String temp = User.getInstance().getCollegeSchool();
                                if (temp.equals(getString(R.string.user_creation_college)))
                                    CustomDatabaseUtils.addObject(User.getInstance(), "user_creation", "users");
                                else
                                    userCreationActivity.getViewPager().setCurrentItem(
                                            userCreationActivity.getViewPager().getCurrentItem() + 1
                                    );
                            }
                        }
                )
        );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}