package fleacircus.com.learningproject.Flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fleacircus.com.learningproject.Flashcard_create_Cards;
import fleacircus.com.learningproject.Flashcard_mainActivity;
import fleacircus.com.learningproject.R;

/**
 * A fragment representing the back of the card.
 */
@SuppressLint("ValidFragment")
public class Flashcard_CardBackFragment extends Fragment {

    private EditText cardBackTxt;
    private String backTxt;
    private FragmentBackListener listener;

    // method to be used in connection with user inputted and passing into flashcard activity
    public interface FragmentBackListener {
        void userInputBackSent(String input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.flashcard_card_back, container, false);

        cardBackTxt = view.findViewById(R.id.card_back_text);
        cardBackTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if(getActivity() instanceof Flashcard_mainActivity) {

                    String string1= "Card Back";
                    cardBackTxt.setText(string1);

                ((Flashcard_mainActivity)getActivity()).flipToFrontSideCard();
                }

                if(getActivity() instanceof Flashcard_create_Cards) {
                    // assign user input text to variable
                    backTxt = cardBackTxt.getText().toString();
                    // attach it with method to flashcard activity
                    listener.userInputBackSent(backTxt);
                    // flip flashcard method
                    ((Flashcard_create_Cards)getActivity()).flipToFrontSideCard();
                }
            }
        });

        return view;
    }

    // method called when fragment attached to activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // check if activity implements this interface
        if(context instanceof FragmentBackListener) {
            // take listener variable and assign it to activity
            listener = (FragmentBackListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "missing FragmentBackListener");
        }
    }

    // remove fragment from activity
    @Override
    public void onDetach() {
        super.onDetach();
        // reference not required so make it null
        listener = null;
    }
}
