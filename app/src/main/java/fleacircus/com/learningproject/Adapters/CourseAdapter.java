package fleacircus.com.learningproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.ColorUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Holder> {

    private List<CustomCourse> courses;
    private String uid;
    private boolean hasClick;

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }

    public CourseAdapter(List<CustomCourse> courses, boolean hasClick) {
        this.courses = courses;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //noinspection ConstantConditions
        this.uid = auth.getCurrentUser().getUid();
        this.hasClick = hasClick;
    }

    private void onClick(Context context, CustomCourse customCourse) {
        String creatorID = customCourse.getCreatorID();
        String courseID = customCourse.getCourseID();
        String[] from =  new String[]{"users", creatorID, "courses", courseID};
        String[] to =  new String[]{"users", uid, "courses"};

        CustomDatabaseUtils.copyDocument(from, to, "Classes.CustomCourse");
        Toast.makeText(context, R.string.courses_message_addition, Toast.LENGTH_SHORT).show();
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

        View view = holder.itemView;
        if (hasClick)
            view.setOnClickListener(v -> onClick(view.getContext(), course));

        String name = StringUtils.capitaliseEach(course.getName());
        String description = course.getDescription();

        TextView n = view.findViewById(R.id.textViewCourse);
        TextView d = view.findViewById(R.id.textViewDescription);
        n.setText(name);
        d.setText(description);

        boolean uidMatch = StringUtils.hasMatch(course.getCreatorID(), uid);
        if (uidMatch)
            return;

        String creator = course.getCreatorID();
        TextView c = view.findViewById(R.id.textViewCreator);
        c.setText(creator);

        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayout);
        ColorUtils.setBackgroundColor(constraintLayout, R.color.blue_off_white);

        int bob = view.getResources().getColor(R.color.blue_off_black);
        n.setTextColor(bob);
        d.setTextColor(bob);
        c.setTextColor(bob);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

