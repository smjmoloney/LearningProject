package fleacircus.com.learningproject.CustomList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fleacircus.com.learningproject.Course.CustomCourse;
import fleacircus.com.learningproject.Course.CustomTopic;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.R;

public class CustomListLearnTopicItemAdapter extends RecyclerView.Adapter<CustomListLearnTopicItemAdapter.Holder> {

    private ArrayList<CustomTopic> list = new ArrayList<>();

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView topicDetails;

        private Holder(View itemView) {
            super(itemView);

            topicDetails = itemView.findViewById(R.id.details);
        }
    }

    public CustomListLearnTopicItemAdapter(ArrayList<CustomTopic> list) {
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        String temp = list.get(position).getTitle();
        if (temp == null)
            holder.topicDetails.setText("N/A");

        CardView card = (CardView) holder.itemView;
        if (position % 2 == 1)
            card.setCardBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(R.color.bg_row_background));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                ((LearnCourseActivity) context).getViewPager().setCurrentItem(
                        ((LearnCourseActivity) context).getViewPager().getCurrentItem() + 1
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

