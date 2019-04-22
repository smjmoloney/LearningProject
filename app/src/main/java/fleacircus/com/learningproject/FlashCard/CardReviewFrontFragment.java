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
public class CardReviewFrontFragment extends Fragment {

    private static final String FRONT_DATA = "front_data";

    private void generate(int direction) {
        if (getActivity() != null) {
            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
            if (reviewActivity != null) reviewActivity.generateNextFlashCard(direction);
        }
    }

    private void setFrontData(View view) {
        if (getArguments() != null) {
            View v = view.findViewById(R.id.textViewFront);
            TextView cardFrontText = (TextView) v;

            String data = getArguments().getString(FRONT_DATA);
            if (data != null) cardFrontText.setText(data);
        }
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        if (getActivity() != null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                TextView textView = getActivity().findViewById(R.id.textViewFront);
                textView.setText(bundle.getString(FRONT_DATA));
            }

            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
            reviewActivity.flipCard(false);
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

    public static CardReviewFrontFragment newInstance(String text) {
        CardReviewFrontFragment cardFrontFragment = new CardReviewFrontFragment();
        Bundle args = new Bundle();
        args.putString(FRONT_DATA, text);
        cardFrontFragment.setArguments(args);
        return cardFrontFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard_review_front, container, false);
        if (getActivity() != null) ButterKnife.bind(this, getActivity());

        setFrontData(view);
        return view;
    }
}