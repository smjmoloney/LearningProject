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

public class UserCreationTeacherStudentFragment extends Fragment {

    UserCreationActivity userCreationActivity;

    public UserCreationTeacherStudentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCreationActivity = (UserCreationActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_teacher_student_fragment, container, false);

        View student = rootView.findViewById(R.id.student_layout);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setTeacherStudent(getString(R.string.user_creation_student));
                userCreationActivity.getViewPager().setCurrentItem(
                        userCreationActivity.getViewPager().getCurrentItem() + 1
                );
            }
        });

        View teacher = rootView.findViewById(R.id.teacher_layout);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setTeacherStudent(getString(R.string.user_creation_teacher));
                userCreationActivity.getViewPager().setCurrentItem(
                        userCreationActivity.getViewPager().getCurrentItem() + 1
                );
            }
        });

        return rootView;
    }
}