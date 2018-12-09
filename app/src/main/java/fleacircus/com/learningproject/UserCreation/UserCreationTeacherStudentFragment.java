package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import fleacircus.com.learningproject.CustomClasses.CustomUser;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;

public class UserCreationTeacherStudentFragment extends Fragment {

    public UserCreationTeacherStudentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_teacher_student_fragment, container, false);

        rootView.findViewById(R.id.question_layout).startAnimation(
                AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));

        View student = rootView.findViewById(R.id.student_layout);
        student.startAnimation(
                AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right));

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setTeacherStudent(getString(R.string.user_creation_student));
                ((UserCreationActivity) getActivity()).getViewPager().setCurrentItem(
                        ((UserCreationActivity) getActivity()).getViewPager().getCurrentItem() + 1
                );
            }
        });

        View teacher = rootView.findViewById(R.id.teacher_layout);
        teacher.startAnimation(
                AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left));

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setTeacherStudent(getString(R.string.user_creation_teacher));
                ((UserCreationActivity) getActivity()).getViewPager().setCurrentItem(
                        ((UserCreationActivity) getActivity()).getViewPager().getCurrentItem() + 1
                );
            }
        });

        return rootView;
    }
}