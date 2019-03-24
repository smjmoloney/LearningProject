package fleacircus.com.learningproject.UserCreation;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class CourseFragment extends Fragment {
    @BindView(R.id.spinner)
    Spinner spinner;

    public CourseFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.button_submit)
    void courseClick() {
        CustomUser.getInstance().setCourse(spinner.getSelectedItem().toString());

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    private void selectCourse() {
        CustomDatabaseUtils.read(new String[]{"user_creation", "courses"}, new OnGetDataListener() {
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

                        String loc = CustomUser.getInstance().getLocation();
                        Object names = documentSnapshot.get(loc);
                        if (!(names instanceof ArrayList))
                            return;

                        List<String> courses = new ArrayList<>();
                        for (Object obj : (ArrayList) names)
                            if (obj instanceof String)
                                courses.add((String) obj);

                        //noinspection ConstantConditions
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                getActivity(),
                                R.layout.item_spinner,
                                courses);
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
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, view);

        if (CustomUser.getInstance().getLocation() == null)
            return view;

        selectCourse();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}