package fleacircus.com.learningproject.FlashCard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import fleacircus.com.learningproject.FlashCardCreateActivity;
import fleacircus.com.learningproject.FlashCardReviewActivity;
import fleacircus.com.learningproject.Listeners.FragmentBackListener;
import fleacircus.com.learningproject.R;

/**
 * A fragment representing the back of the card.
 */
public class CardBackFragment extends Fragment {

    private static final String BACK_DATA = "back_data";
    private static final String IS_CREATE = "is_create";

    private FragmentBackListener fragmentBackListener;

    private void generate(int direction) {
        if (getActivity() != null) {
            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
                if (reviewActivity != null)
                    reviewActivity.generateNextFlashCard(direction);
        }
    }

    private void setBackData(View view) {
        if (getArguments() != null) {
            if (getArguments().getBoolean(IS_CREATE))
                return;

            View v = view.findViewById(R.id.editTextBack);
            TextView cardBackText = (TextView) v;

            String data = getArguments().getString(BACK_DATA);
            if (data != null)
                cardBackText.setText(data);
        }
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        if (getActivity() != null) {
            EditText editText;
            TextView textView;
            String textBack;

            View v = getActivity().findViewById(R.id.editTextBack);
            if (v instanceof EditText) {
                editText = (EditText) v;
                textBack = editText.getText().toString();
            } else {
                textView = (TextView) v;
                textBack = textView.getText().toString();
            }

            fragmentBackListener.userInputBackSent(textBack);
            if (getActivity() instanceof FlashCardCreateActivity) {
                FlashCardCreateActivity createCards = (FlashCardCreateActivity) getActivity();
                if (createCards != null)
                    createCards.flipCard(false);

                return;
            }

            Bundle bundle = getArguments();
//            if (bundle != null) {
//                if (getActivity().findViewById(R.id.editTextBack) instanceof EditText) {
//                    if (editText != null) {
//                        editText.setText(bundle.getString(FRONT_DATA));
//                    }
//                } else {
//                    if (textView != null) {
//                        textView.setText(bundle.getString(FRONT_DATA));
//                    }
//                }
//            }
//
//            FlashCardReviewActivity reviewActivity = (FlashCardReviewActivity) getActivity();
//            reviewActivity.flipCard(true);
        }
    }

    @Optional
    @OnClick(R.id.fabViewPrevious)
    void fabViewPrevious() {
        generate(-1);
    }

    @Optional
    @OnClick(R.id.fabViewNext)
    void fabViewNext() {
        generate(1);
    }

    public static CardBackFragment newInstance(String text, boolean isCreate) {
        CardBackFragment cardBackFragment = new CardBackFragment();
        Bundle args = new Bundle();
        args.putString(BACK_DATA, text);
        args.putBoolean(IS_CREATE, isCreate);
        cardBackFragment.setArguments(args);
        return cardBackFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if (getArguments() != null) {
            if (getArguments().getBoolean(IS_CREATE)) {
                view = inflater.inflate(R.layout.fragment_flashcard_create_back, container, false);
            } else {
                view = inflater.inflate(R.layout.fragment_flashcard_review_back, container, false);
                setBackData(view);
            }
        }

        if (getActivity() != null)
            ButterKnife.bind(this, getActivity());

        setBackData(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBackListener) {
            fragmentBackListener = (FragmentBackListener) context;
        } else {
            throw new RuntimeException(context.toString() + "missing FragmentBackListener");
        }
    }

    // remove fragment from activity
    @Override
    public void onDetach() {
        super.onDetach();
        fragmentBackListener = null;
    }
}