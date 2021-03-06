package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CollegeSchoolFragment extends Fragment {

    @OnClick(R.id.constrainLayoutCollege)
    void collegeClick() {
        CustomUser.getInstance().setCollegeSchool(getString(R.string.answer_college));

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    @OnClick(R.id.constrainLayoutSchool)
    void schoolLayout() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setCollegeSchool(getString(R.string.answer_school));

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

        CustomUser customUser = CustomUser.getInstance();
        if (customUser.getTeacherStudent() == null) return view;

        ButterKnife.bind(this, view);

        String status = CustomUser.getInstance().getTeacherStudent();
        String answer = getString(R.string.answer_teacher);
        boolean match = StringUtils.hasMatch(status, answer);
        if (!match) return view;

        TextView question = view.findViewById(R.id.textViewQuestion);
        question.setText(getString(R.string.question_school_teacher));

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}