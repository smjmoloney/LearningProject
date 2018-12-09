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

import fleacircus.com.learningproject.CustomClasses.CustomAnswer;
import fleacircus.com.learningproject.CustomClasses.CustomQuestion;
import fleacircus.com.learningproject.LearnCourseActivity;
import fleacircus.com.learningproject.R;

public class CustomListLearnAnswerItemAdapter extends RecyclerView.Adapter<CustomListLearnAnswerItemAdapter.Holder> {

    private ArrayList<CustomAnswer> list = new ArrayList<>();

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView answerDetails;

        private Holder(View itemView) {
            super(itemView);

            answerDetails = itemView.findViewById(R.id.details);
        }
    }

    public CustomListLearnAnswerItemAdapter(ArrayList<CustomAnswer> list) {
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
        holder.answerDetails.setText(list.get(position).getTitle());

        CardView card = (CardView) holder.itemView;
        if (position % 2 == 1)
            card.setCardBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(R.color.bg_row_background));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

