package fleacircus.com.learningproject.FlashCard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fleacircus.com.learningproject.FlashCardCreateActivity;
import fleacircus.com.learningproject.Listeners.FragmentFrontListener;
import fleacircus.com.learningproject.R;

/**
 * A fragment representing the front of the card.
 */
public class CardCreateFrontFragment extends Fragment {

    private String textFront;

    private FragmentFrontListener fragmentFrontListener;

    private void updateString() {
        if (getActivity() != null) {
            EditText editText = getActivity().findViewById(R.id.editTextFront);
            textFront = editText.getText().toString();
        }
    }

    @OnClick(R.id.fabViewCheckmark)
    void fabViewCheckmark() {
        if (getActivity() != null) {
            updateString();

            FlashCardCreateActivity createCards = (FlashCardCreateActivity) getActivity();
            if (createCards != null) {
                fragmentFrontListener.userInputFrontSent(textFront);
                createCards.saveFlashcard();
            }
        }
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        if (getActivity() != null) {
            updateString();

            FlashCardCreateActivity createCards = (FlashCardCreateActivity) getActivity();
            if (createCards != null) {
                fragmentFrontListener.userInputFrontSent(textFront);
                createCards.flipCard(false);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard_create_front, container, false);
        if (getActivity() != null) ButterKnife.bind(this, getActivity());

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentFrontListener) {
            fragmentFrontListener = (FragmentFrontListener) context;
        } else {
            throw new RuntimeException(context.toString() + "missing FragmentFrontListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentFrontListener = null;
    }
}