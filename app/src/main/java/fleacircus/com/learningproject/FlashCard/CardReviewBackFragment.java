package fleacircus.com.learningproject.FlashCard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.FlashCardReviewActivity;
import fleacircus.com.learningproject.R;

/**
 * A fragment representing the back of the card.
 */
public class CardReviewBackFragment extends Fragment {

    private static final String BACK_DATA = "back_data";

    private void generate(int direction) {
        if (getActivity() != null) {
            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
            if (reviewActivity != null) reviewActivity.generateNextFlashCard(direction);
        }
    }

    private void setBackData(View view) {
        if (getArguments() != null) {
            View v = view.findViewById(R.id.textViewBack);
            TextView cardBackText = (TextView) v;

            String data = getArguments().getString(BACK_DATA);
            if (data != null) cardBackText.setText(data);
        }
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        if (getActivity() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                TextView textView = getActivity().findViewById(R.id.textViewBack);
                textView.setText(bundle.getString(BACK_DATA));
            }

            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
            reviewActivity.flipCard(true);
        }
    }

    @OnClick(R.id.fabViewPrevious)
    void fabViewPrevious() {
        generate(-1);
    }

    @OnClick(R.id.fabViewNext)
    void fabViewNext() {
        generate(1);
    }

    public static CardReviewBackFragment newInstance(String text) {
        CardReviewBackFragment cardBackFragment = new CardReviewBackFragment();
        Bundle args = new Bundle();
        args.putString(BACK_DATA, text);
        cardBackFragment.setArguments(args);
        return cardBackFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard_review_back, container, false);
        if (getActivity() != null) ButterKnife.bind(this, getActivity());

        setBackData(view);
        return view;
    }
}