package fleacircus.com.learningproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.FoundUserActivity;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FindUserAdapter extends RecyclerView.Adapter<FindUserAdapter.Holder> implements Filterable {

    private List<CustomUser> users;

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }

    public FindUserAdapter(List<CustomUser> users) {
        this.users = users;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                users = (List<CustomUser>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<CustomUser> filteredResults = users;
                if (constraint.length() > 0)
                    filteredResults = getFilteredResults(StringUtils.toLowerCase(constraint.toString()));

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<CustomUser> getFilteredResults(String constraint) {
        List<CustomUser> results = new ArrayList<>();
        for (CustomUser item : users) {
            boolean name = item.getName() != null && item.getName().contains(constraint);
            boolean course = item.getCourse() != null && item.getCourse().contains(constraint);
            boolean email = item.getEmail() != null && item.getEmail().contains(constraint);
            boolean location = item.getLocation() != null && item.getLocation().contains(constraint);

            if (name || course || email || location) results.add(item);
        }

        return results;
    }

    private void onClick(CustomUser user, View view) {
        Activity activity = (Activity) view.getContext();
        Intent intent = new Intent(activity, FoundUserActivity.class);
        intent.putExtra("user", user);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NavigationUtils.startActivity(activity, intent);
            return;
        }

        ImageView image = view.findViewById(R.id.imageViewProfile);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, image, "imageViewProfile");

        activity.startActivity(intent, options.toBundle());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_find, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CustomUser user = users.get(position);
        if (user == null) return;
        if (user.getName() == null) return;

        View view = holder.itemView;
        view.setOnClickListener(v -> onClick(user, view));

        String name = StringUtils.capitaliseEach(user.getName());
        String location = StringUtils.capitaliseEach(user.getLocation());
        TextView n = view.findViewById(R.id.textViewName);
        TextView l = view.findViewById(R.id.textViewLocation);
        n.setText(name);
        l.setText(location);

        Context context = holder.itemView.getContext();
        String college = context.getString(R.string.answer_college);
        String student = context.getString(R.string.answer_student);
        boolean cMatch = StringUtils.hasMatch(user.getCollegeSchool(), college);
        boolean sMatch = StringUtils.hasMatch(user.getTeacherStudent(), student);
        if (cMatch && sMatch) {
            String course = StringUtils.capitaliseEach(user.getCourse());
            TextView c = view.findViewById(R.id.textViewCourse);
            c.setText(course);
        }

        int imageID = user.getImageID();
        if (imageID != 0) {
            ImageView image = view.findViewById(R.id.imageViewProfile);
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));
        }
    }

    @Override
    public int getItemCount() {
        return (users == null) ? 0 : users.size();
    }
}

