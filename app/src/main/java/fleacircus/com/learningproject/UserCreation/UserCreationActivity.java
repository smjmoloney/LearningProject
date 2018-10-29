package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

public class UserCreationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        addOnboarding();
    }

    private void addOnboarding() {
        int[] colourList = new int[]{
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.green)
        };

        /*
        The backForwardOnboarding method must be used in conjunction with a tabbed activity.
        See OnboardingUtils to review this method.
         */
        OnboardingUtils.backForwardOnboarding(
                viewPager, new Button[]{findViewById(R.id.btn_back), findViewById(R.id.btn_forward)}, colourList, false);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_creation, container, false);
            ConstraintLayout constraintLayout = rootView.findViewById(R.id.constraintLayout);

            if (getArguments() != null) {
                int section = getArguments().getInt(ARG_SECTION_NUMBER);

                switch (section) {
                    case 1:
                        teacherStudentFragment(constraintLayout);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            return rootView;
        }

        private void teacherStudentFragment(ConstraintLayout constraintLayout) {
            TextView textView = constraintLayout.findViewById(R.id.question_text);
            textView.setText(R.string.teacher_student_question);

            Button answerA = constraintLayout.findViewById(R.id.answer_a);
            answerA.setText(R.string.teacher_student_answer_a);

            Button answerB = constraintLayout.findViewById(R.id.answer_b);
            answerB.setText(R.string.teacher_student_answer_b);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
