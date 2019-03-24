package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class LocationFragment extends Fragment {
    @BindView(R.id.question_text)
    TextView question;
    @BindView(R.id.spinner)
    Spinner spinner;

    public LocationFragment() {
    }

    @OnClick(R.id.button_submit)
    void locationClick() {
        CustomUser.getInstance().setLocation(spinner.getSelectedItem().toString());

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();

        int temp = 1;
        if (CustomUser.getInstance().getTeacherStudent().equals("teacher"))
            temp = 2;

        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), temp);
    }

    private void populateSpinner() {
        String education = CustomUser.getInstance().getCollegeSchool();
        String temp = getString(R.string.question_location, StringUtils.toUpperCase(education));

        if (CustomUser.getInstance().getTeacherStudent() != null) {
            String teacher = StringUtils.toLowerCase(getString(R.string.answer_teacher));
            if (CustomUser.getInstance().getTeacherStudent().equals(teacher))
                temp = getString(R.string.question_location_teacher, StringUtils.toUpperCase(education));
        }

        question.setText(temp);

        CustomDatabaseUtils.read(new String[]{"user_creation", education}, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (!isQuery) {
                        DocumentSnapshot documentSnapshot = (DocumentSnapshot) object;
                        if (!documentSnapshot.exists())
                            return;

                        Object names = documentSnapshot.get("names");
                        if (!(names instanceof ArrayList))
                            return;

                        List<String> locations = new ArrayList<>();
                        for (Object obj : (ArrayList) names)
                            if (obj instanceof String)
                                locations.add((String) obj);

                        //noinspection ConstantConditions
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getActivity(),
                                R.layout.item_spinner,
                                locations);
                        spinner.setAdapter(adapter);
                    } else
                        Log.e("OnSuccess", object + " must be a query.");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);

        if (CustomUser.getInstance().getCollegeSchool() == null)
            return view;

        populateSpinner();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}