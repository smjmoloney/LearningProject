package fleacircus.com.learningproject.Adapters;

import android.app.Activity;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.FoundUserActivity;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.StringUtils;

public class FindUserAdapter extends RecyclerView.Adapter<FindUserAdapter.Holder> implements Filterable {

    private List<CustomUser> mUsers;
    private List<CustomUser> users = new ArrayList<>();

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onClick(View view) {
            Activity activity = (Activity) view.getContext();

            Intent intent = new Intent(activity, FoundUserActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ImageView image = view.findViewById(R.id.image_profile);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity, image, "image_profile");

                activity.startActivity(intent, options.toBundle());
            } else
                activity.startActivity(intent);
        }
    }

    public FindUserAdapter(List<CustomUser> users) {
        mUsers = users;
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
                List<CustomUser> filteredResults = mUsers;
                if (constraint.length() > 0)
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<CustomUser> getFilteredResults(String constraint) {
        List<CustomUser> results = new ArrayList<>();

        constraint = constraint.toLowerCase();
        for (CustomUser item : mUsers) {
            boolean name = item.getName() != null && item.getName().contains(constraint);
            boolean course = item.getCourse() != null && item.getCourse().contains(constraint);
            boolean email = item.getEmail() != null && item.getEmail().contains(constraint);
            boolean location = item.getLocation() != null && item.getLocation().contains(constraint);

            if (name || course || email || location)
                results.add(item);
        }

        return results;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_find, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CustomUser user = users.get(position);
        View view = holder.itemView;

        ImageView image = view.findViewById(R.id.image_profile);

        int imageID = user.getImageID();
        if (imageID != 0)
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));

        String n = StringUtils.capitliseEach(user.getName());
        TextView name = view.findViewById(R.id.name);
        name.setText(n);

        String l = StringUtils.capitliseEach(user.getLocation());
        TextView location = view.findViewById(R.id.location);
        location.setText(l);
    }

    @Override
    public int getItemCount() {
        return (users == null) ? 0 : users.size();
    }
}

