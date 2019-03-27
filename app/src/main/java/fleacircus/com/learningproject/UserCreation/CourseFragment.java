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

public class CourseFragment extends Fragment {
    @BindView(R.id.spinner)
    Spinner spinner;

    private ViewPager viewPager;

    public CourseFragment() {
        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        viewPager = userCreationActivity.getViewPager();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.button_submit)
    void courseClick() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setCourse(spinner.getSelectedItem().toString());

        FragmentHelper.progressFragment(viewPager, 1);
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

                        String location = CustomUser.getInstance().getLocation();
                        Object names = documentSnapshot.get(location);
                        if (!(names instanceof ArrayList))
                            return;

                        List<String> courses = new ArrayList<>();
                        for (Object o : (ArrayList) names)
                            if (o instanceof String)
                                courses.add((String) o);

                        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
                        //noinspection ConstantConditions
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                userCreationActivity,
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
        if (CustomUser.getInstance().getLocation() == null)
            return view;

        ButterKnife.bind(this, view);
        selectCourse();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}