package fleacircus.com.learningproject.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Holder> {

    private List<CustomCourse> courses;
    private String uid, foundUid = "";

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }

    public CourseAdapter(List<CustomCourse> courses) {
        this.courses = courses;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            this.uid = user.getUid();
    }

    public CourseAdapter(List<CustomCourse> courses, String foundUid) {
        this.courses = courses;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            this.uid = user.getUid();

        this.foundUid = foundUid;
    }

    private void editCourseAnimate(List<View> views, long duration, long delay, boolean isEntry) {
        int d = 0;
        for (View v : views) {
            if (!isEntry) {
                CustomAnimationUtils.alphaAnimation(v, 1, 0, delay, true);
            } else {
                Animation fabPress = CustomAnimationUtils.loadAnimation(v.getContext(), R.anim.animation_pop_entry);
                fabPress.setInterpolator(new CustomBounceInterpolator(0.2, 10));
                fabPress.setDuration(duration);
                fabPress.setStartOffset(d);
                fabPress.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        CustomAnimationUtils.alphaAnimation(v, 0, 1, delay, true);
                        v.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                v.startAnimation(fabPress);
                d += delay;
            }
        }
    }

    private void editCourseOnClickListeners(View view,
                                            CustomCourse customCourse,
                                            ConstraintLayout constraintLayoutDelete,
                                            ConstraintLayout constraintLayoutCancel) {
        View.OnClickListener onClickListenerDelete = v -> {
            String courseID = customCourse.getCourseID();
            String[] courseDelete = new String[]{"users", uid, "courses", courseID};

            CustomDatabaseUtils.deleteDocument(courseDelete);
            Toast.makeText(view.getContext(), R.string.courses_message_addition, Toast.LENGTH_SHORT).show();

            notifyDataSetChanged();
        };

        View.OnClickListener onClickListenerCancel = v -> {
            long duration = view.getResources().getInteger(R.integer.duration_default);
            List<View> views = new ArrayList<>();
            views.add(constraintLayoutDelete);
            views.add(constraintLayoutCancel);
            editCourseAnimate(views, duration, (long) (duration * .75), false);
        };

        constraintLayoutDelete.setOnClickListener(onClickListenerDelete);
        constraintLayoutCancel.setOnClickListener(onClickListenerCancel);

        Log.e("TEST", "TEST");
    }

    private void editCourse(View view, CustomCourse customCourse) {
        ConstraintLayout constraintLayoutDelete = ((View) view.getParent()).findViewById(R.id.constraintLayoutDelete);
        ConstraintLayout constraintLayoutCancel = ((View) view.getParent()).findViewById(R.id.constraintLayoutCancel);

        if (constraintLayoutDelete.getVisibility() == View.VISIBLE
                || constraintLayoutDelete.getVisibility() == View.VISIBLE)
            return;

        editCourseOnClickListeners(view, customCourse, constraintLayoutDelete, constraintLayoutCancel);

        long duration = view.getResources().getInteger(R.integer.duration_default);

        List<View> views = new ArrayList<>();
        views.add(constraintLayoutDelete);
        views.add(constraintLayoutCancel);
        editCourseAnimate(views, duration, (long) (duration * .75), true);
    }

    private void copyCourse(View view, CustomCourse customCourse) {
        String courseID = customCourse.getCourseID();
        String[] from = new String[]{"users", foundUid, "courses", courseID};
        String[] to = new String[]{"users", uid, "courses"};

        CustomDatabaseUtils.copyDocument(from, to, "Classes.CustomCourse");
        Toast.makeText(view.getContext(), R.string.courses_message_addition, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CustomCourse course = courses.get(position);

        View view = holder.itemView.findViewById(R.id.materialRippleLayout);
        if (!foundUid.equals("")) {
            view.setOnLongClickListener(v -> {
                copyCourse(view, course);
                return false;
            });
        } else {
            view.setOnLongClickListener(v -> {
                editCourse(view, course);
                return false;
            });
        }

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgress(course.getCurrentScore());

        String courseName = StringUtils.toUpperCase(course.getName());
        String courseCreator = course.getCreatorID();

        TextView textViewCourseName = view.findViewById(R.id.textViewCourseName);
        TextView textViewCreator = view.findViewById(R.id.textViewCourseCreator);
        textViewCourseName.setText(courseName);
        textViewCreator.setText(courseCreator);

//        String name = StringUtils.capitaliseEach(course.getName());
//        String description = course.getDescription();
//
//        TextView n = view.findViewById(R.id.textViewCourse);
//        TextView d = view.findViewById(R.id.textViewDescription);
//        n.setText(name);
//        d.setText(description);
//
//        boolean uidMatch = StringUtils.hasMatch(course.getCreatorID(), uid);
//        if (uidMatch)
//            return;
//
//        String creator = course.getCreatorID();
//        TextView c = view.findViewById(R.id.textViewCreator);
//        c.setText(creator);
//
//        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayout);
//        ColorUtils.setBackgroundColor(constraintLayout, R.color.blue_off_white);
//
//        int bob = view.getResources().getColor(R.color.blue_off_black);
//        n.setTextColor(bob);
//        d.setTextColor(bob);
//        c.setTextColor(bob);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}