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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CollegeSchoolFragment extends Fragment {
    @BindView(R.id.question_text)
    TextView question;

    public CollegeSchoolFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.college_layout)
    void collegeClick() {
        CustomUser.getInstance().setCollegeSchool(getString(R.string.answer_college));

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.school_layout)
    void schoolLayout() {
        CustomUser.getInstance().setCollegeSchool(getString(R.string.answer_college));

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_school, container, false);
        ButterKnife.bind(this, view);

        if (CustomUser.getInstance().getTeacherStudent() != null) {
            String teacher = StringUtils.toLowerCase(getString(R.string.answer_teacher));
            if (CustomUser.getInstance().getTeacherStudent().equals(teacher)) {
                String temp = getString(R.string.question_school_teacher);
                question.setText(temp);
            }
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}