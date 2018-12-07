package fleacircus.com.learningproject.CustomList;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreation.CustomUser;

public class CustomListUserItemAdapter extends RecyclerView.Adapter<CustomListUserItemAdapter.Holder> {

    private ArrayList<CustomUser> list = new ArrayList<>();

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView userDetails;

        private Holder(View itemView) {
            super(itemView);

            userDetails = itemView.findViewById(R.id.user_details);
        }
    }

    public CustomListUserItemAdapter(ArrayList<CustomUser> list) {
        String temp = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        for (CustomUser user : list) {
            if (user.getEmail() == null)
                continue;

            if (temp != null) {
                if (user.getEmail().equals(temp))
                    continue;

                this.list.add(user);
            }
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_user_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CustomUser user = list.get(position);
        if (user.getEmail() == null)
            return;

        if (position % 2 == 1)
            ((CardView) holder.itemView).setCardBackgroundColor(
                    holder.itemView.getContext().getResources().getColor(R.color.bg_row_background));

        holder.userDetails.setText(
                user.getEmail() + "\n"
                        + user.getName() + "\n"
                        + user.getCourse() + "\n"
                        + user.getCollegeSchool() + "\n"
                        + user.getTeacherStudent() + "\n"
                        + user.getLocation()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

