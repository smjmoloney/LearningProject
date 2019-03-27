package fleacircus.com.learningproject.UserCreation;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CollegeSchoolFragment extends Fragment {

    private ViewPager viewPager;

    public CollegeSchoolFragment() {
        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        viewPager = userCreationActivity.getViewPager();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.college_layout)
    void collegeClick() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setCollegeSchool(getString(R.string.answer_college));

        FragmentHelper.progressFragment(viewPager, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.school_layout)
    void schoolLayout() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setCollegeSchool(getString(R.string.answer_school));

        FragmentHelper.progressFragment(viewPager, 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_school, container, false);

        CustomUser customUser = CustomUser.getInstance();
        if (customUser.getTeacherStudent() == null)
            return view;

        ButterKnife.bind(this, view);

        String status = CustomUser.getInstance().getTeacherStudent();
        String answer = getString(R.string.answer_teacher);
        boolean match = StringUtils.hasMatch(status, answer);
        if (!match)
            return view;

        String teacher = getString(R.string.question_school_teacher);

        TextView question = viewPager.findViewById(R.id.question_text);
        question.setText(teacher);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}