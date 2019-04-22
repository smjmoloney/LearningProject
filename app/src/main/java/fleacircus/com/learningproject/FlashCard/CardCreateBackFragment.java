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
import fleacircus.com.learningproject.Listeners.FragmentBackListener;
import fleacircus.com.learningproject.R;

/**
 * A fragment representing the back of the card.
 */
public class CardCreateBackFragment extends Fragment {

    private String textBack;

    private FragmentBackListener fragmentBackListener;

    @OnClick(R.id.fabViewCheckmark)
    void fabViewCheckmark() {
        if (getActivity() != null) {
            EditText editText = getActivity().findViewById(R.id.editTextBack);
            textBack = editText.getText().toString();

            FlashCardCreateActivity createCards = (FlashCardCreateActivity) getActivity();
            if (createCards != null) {
                fragmentBackListener.userInputBackSent(textBack);
                createCards.saveFlashcard();
            }
        }
    }

    @OnClick(R.id.fabViewSwap)
    void fabViewSwap() {
        if (getActivity() != null) {
            EditText editText = getActivity().findViewById(R.id.editTextBack);
            textBack = editText.getText().toString();

            FlashCardCreateActivity createCards = (FlashCardCreateActivity) getActivity();
            if (createCards != null) {
                fragmentBackListener.userInputBackSent(textBack);
                createCards.flipCard(true);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard_create_back, container, false);
        if (getActivity() != null) ButterKnife.bind(this, getActivity());

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

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentBackListener = null;
    }
}