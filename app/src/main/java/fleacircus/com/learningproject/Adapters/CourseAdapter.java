package fleacircus.com.learningproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.ColorUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Holder> {

    private List<CustomCourse> courses;
    private String uid;

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }

    public CourseAdapter(List<CustomCourse> courses) {
        this.courses = courses;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //noinspection ConstantConditions
        uid = auth.getCurrentUser().getUid();
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

        String name = StringUtils.capitliseEach(course.getName());
        String description = course.getDescription();

        TextView n = view.findViewById(R.id.course);
        TextView d = view.findViewById(R.id.description);
        n.setText(name);
        d.setText(description);

        boolean uidMatch = StringUtils.hasMatch(course.getUserID(), uid);
        if (uidMatch)
            return;

        String creator = course.getUserID() + " - " + course.getEmail();

        TextView c = view.findViewById(R.id.creator);
        c.setText(creator);

        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint);
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

