package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.FragmentHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;

public class TeacherStudentFragment extends Fragment {

    public TeacherStudentFragment() {
    }

    @OnClick(R.id.constraintLayoutStudent)
    void studentClick() {
        CustomUser.getInstance().setTeacherStudent(getString(R.string.answer_student));

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    @OnClick(R.id.constraintLayoutTeacher)
    void teacherClick() {
        CustomUser.getInstance().setTeacherStudent(getString(R.string.answer_teacher));

        UserCreationActivity userCreationActivity = (UserCreationActivity) getActivity();
        //noinspection ConstantConditions
        FragmentHelper.progressFragment(userCreationActivity.getViewPager(), 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_student, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}