package fleacircus.com.learningproject.Flashcard;

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

public class Flashcard_ListAdapter extends FirestoreRecyclerAdapter<Flashcard_Listing, Flashcard_ListAdapter.FlashcardHolder> {

        private OnItemClickListener listener;

        public Flashcard_ListAdapter(@NonNull FirestoreRecyclerOptions<Flashcard_Listing> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Flashcard_ListAdapter.FlashcardHolder fHolder, int position, @NonNull Flashcard_Listing model) {
            fHolder.flashcardNameText.setText(model.getName());
        }

        @NonNull
        @Override
        public FlashcardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item,
                    parent, false);
            return new FlashcardHolder(v);
        }

        class FlashcardHolder extends RecyclerView.ViewHolder {
            TextView flashcardNameText;

            public FlashcardHolder(View itemView) {
                super(itemView);
                flashcardNameText = itemView.findViewById(R.id.flashcardNameListing);


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