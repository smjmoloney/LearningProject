package fleacircus.com.learningproject.Quiz;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import fleacircus.com.learningproject.R;

public class Quiz_ListAdapter extends FirestoreRecyclerAdapter<Quiz_Listing, Quiz_ListAdapter.QuizHolder> {

    private OnItemClickListener listener;

    public Quiz_ListAdapter(@NonNull FirestoreRecyclerOptions<Quiz_Listing> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuizHolder qHolder, int position, @NonNull Quiz_Listing model) {
        qHolder.quizNameText.setText(model.getName());
    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item,
                parent, false);
        return new QuizHolder(v);
    }

    class QuizHolder extends RecyclerView.ViewHolder {
        TextView quizNameText;

        public QuizHolder(View itemView) {
            super(itemView);
            quizNameText = itemView.findViewById(R.id.quizNameListing);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}