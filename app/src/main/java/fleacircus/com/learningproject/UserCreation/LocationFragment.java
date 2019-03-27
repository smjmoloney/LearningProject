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
import androidx.viewpager.widget.ViewPager;
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

    @BindView(R.id.spinner)
    Spinner spinner;

    private ViewPager viewPager;

    public LocationFragment() {
        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        viewPager = userCreationActivity.getViewPager();
    }

    @OnClick(R.id.button_submit)
    void locationClick() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setLocation(spinner.getSelectedItem().toString());

        String status = CustomUser.getInstance().getTeacherStudent();
        String answer = getString(R.string.answer_teacher);
        boolean match = StringUtils.hasMatch(status, answer);

        int process = 1;
        if (match)
            process = 2;

        FragmentHelper.progressFragment(viewPager, process);
    }

    private void populateSpinner() {
        if (CustomUser.getInstance().getTeacherStudent() == null)
            return;

        TextView question = viewPager.findViewById(R.id.question_text);

        int questionLocation = R.string.question_location;
        int questionLocationTeacher = R.string.question_location_teacher;

        CustomUser customUser = CustomUser.getInstance();

        String education = StringUtils.toUpperCase(customUser.getCollegeSchool());
        String status = CustomUser.getInstance().getTeacherStudent();
        String answer = getString(R.string.answer_teacher);
        boolean match = StringUtils.hasMatch(status, answer);
        if (!match)
            question.setText(getString(questionLocation, education));
        else
            question.setText(getString(questionLocationTeacher, education));

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

                        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
                        //noinspection ConstantConditions
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                userCreationActivity,
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