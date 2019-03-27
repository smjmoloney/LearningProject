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

        String status = CustomUser.getInstance().getTeacherStudent();
        String education = CustomUser.getInstance().getCollegeSchool();
        String teacher = getString(R.string.answer_teacher);
        String school = getString(R.string.answer_school);
        boolean isTeacher = StringUtils.hasMatch(status, teacher);
        boolean isSchool = StringUtils.hasMatch(education, school);

        int process = 1;
        if (isTeacher || isSchool)
            process = 2;

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), process);
    }

    private void populateSpinner() {
        if (CustomUser.getInstance().getTeacherStudent() == null)
            return;

        int questionLocation = R.string.question_location;
        int questionLocationTeacher = R.string.question_location_teacher;

        String education = CustomUser.getInstance().getCollegeSchool();
        String status = CustomUser.getInstance().getTeacherStudent();
        String answer = getString(R.string.answer_teacher);
        boolean match = StringUtils.hasMatch(status, answer);
        if (!match)
            question.setText(getString(questionLocation, StringUtils.toUpperCase(education)));
        else
            question.setText(getString(questionLocationTeacher, StringUtils.toUpperCase(education)));

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
        if (CustomUser.getInstance().getCollegeSchool() == null)
            return view;

        ButterKnife.bind(this, view);
        populateSpinner();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}