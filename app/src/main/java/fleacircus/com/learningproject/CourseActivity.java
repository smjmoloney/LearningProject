package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import fleacircus.com.learningproject.Utils.MenuUtils;
import fleacircus.com.learningproject.Utils.OnboardingUtils;

/**
 * This class is the foundation of tools given to the user
 * when creating or finding a course for their account.
 */
public class CourseActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    /**
     * @return The viewPager variable that stores our
     * fragment container (view pager) that will present
     * various user creation tools to the user.
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        findViewById(R.id.create_layout).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_right));
        findViewById(R.id.learn_layout).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_left));

//        questionTest("example", "topic");
    }

//    private void questionTest(String course, String topic) {
//        final String question = "example question";
//
//        CustomDatabaseUtils.testRead(course, topic, question, new OnGetDataListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(Object object, boolean isQuery) {
//                TextView questionView = findViewById(R.id.question);
//                questionView.setText(question);
//
//                Map<String, Object> map = ((DocumentSnapshot) object).getData();
//                LinearLayout answers = findViewById(R.id.answers);
//
//                List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
//                Collections.shuffle(list);
//
//                int i = 0;
//                for (Map.Entry<String, Object> entry : list) {
//                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
//
//                    TextView answer = (TextView) answers.getChildAt(i);
//                    answer.setText((String) values.get("answer"));
//
//                    if ((boolean) values.get("isCorrect"))
//                        answer.setTextColor(getResources().getColor(R.color.green));
//
//                    i++;
//                }
//            }
//
//            @Override
//            public void onFailed(FirebaseFirestoreException databaseError) {
//                Log.e("FirebaseFirestoreEx", databaseError.toString());
//            }
//        });
//    }

    /**
     * Initialises our viewPager and attaches an adapter
     * that stores a number of user creation fragments.
     */
    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new UserCreationTeacherStudentFragment());
//        adapter.addFragment(new UserCreationCollegeSchoolFragment());
//        adapter.addFragment(new UserCreationLocationFragment());
//        adapter.addFragment(new UserCreationCourseFragment());
//        adapter.addFragment(new UserCreationNameFragment());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        OnboardingUtils.noDragOnlyOnboarding(viewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        private void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
