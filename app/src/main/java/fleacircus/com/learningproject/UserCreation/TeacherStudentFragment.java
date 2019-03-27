package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;

public class TeacherStudentFragment extends Fragment {

    private ViewPager viewPager;

    public TeacherStudentFragment() {
        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        viewPager = userCreationActivity.getViewPager();
    }

    @OnClick(R.id.student_layout)
    void studentClick() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setTeacherStudent(getString(R.string.answer_student));

        FragmentHelper.progressFragment(viewPager, 1);
    }

    @OnClick(R.id.teacher_layout)
    void teacherClick() {
        CustomUser customUser = CustomUser.getInstance();
        customUser.setTeacherStudent(getString(R.string.answer_teacher));

        FragmentHelper.progressFragment(viewPager, 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_teacher_student, container, false);
    }
}