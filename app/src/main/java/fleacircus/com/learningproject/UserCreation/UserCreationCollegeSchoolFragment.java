package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fleacircus.com.learningproject.Helpers.UserCreationHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;

public class UserCreationCollegeSchoolFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationCollegeSchoolFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_college_school_fragment, container, false);

        View college = rootView.findViewById(R.id.college_layout);
        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setCollegeSchool(getString(R.string.user_creation_college));
                userCreationActivity.getViewPager().setCurrentItem(
                        userCreationActivity.getViewPager().getCurrentItem() + 1
                );
            }
        });

        View school = rootView.findViewById(R.id.school_layout);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setCollegeSchool(getString(R.string.user_creation_school));
                userCreationActivity.getViewPager().setCurrentItem(
                        userCreationActivity.getViewPager().getCurrentItem() + 1
                );
            }
        });

        return rootView;
    }
}