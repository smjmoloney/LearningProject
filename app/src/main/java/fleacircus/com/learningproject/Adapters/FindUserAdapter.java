package fleacircus.com.learningproject.Adapters;

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
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.R;

public class FindUserAdapter extends RecyclerView.Adapter<FindUserAdapter.Holder> implements Filterable {

    private List<CustomUser> users = new ArrayList<>();
    private List<CustomUser> temp;

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }


    public FindUserAdapter(List<CustomUser> users) {
        temp = users;
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
                List<CustomUser> filteredResults = temp;
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
        for (CustomUser item : temp) {
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

        TextView name = view.findViewById(R.id.name);
        name.setText(user.getName());

        TextView location = view.findViewById(R.id.location);
        location.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return (users == null) ? 0 : users.size();
    }
}

