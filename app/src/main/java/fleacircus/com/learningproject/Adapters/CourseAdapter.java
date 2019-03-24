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

        TextView crs = view.findViewById(R.id.course);
        crs.setText(course.getName());

        TextView dstn = view.findViewById(R.id.description);
        dstn.setText(course.getDescription());

        if (!course.getUserID().equals(uid)) {
            ConstraintLayout constraintLayout = view.findViewById(R.id.constraint);
            constraintLayout.setBackgroundColor(
                    view.getResources().getColor(R.color.blue_off_white));

            TextView ctr = view.findViewById(R.id.creator);

            String temp = course.getUserID() + " - " + course.getEmail();
            ctr.setText(temp);

            int bob = view.getResources().getColor(R.color.blue_off_black);
            crs.setTextColor(bob);
            dstn.setTextColor(bob);
            ctr.setTextColor(bob);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

