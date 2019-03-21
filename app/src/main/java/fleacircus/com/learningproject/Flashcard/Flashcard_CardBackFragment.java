package fleacircus.com.learningproject.Flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        Button btnBack;
        if(getActivity() instanceof Flashcard_create_Cards) {

            View view = inflater.inflate(R.layout.flashcard_card_back, container, false);

            cardBackTxt = view.findViewById(R.id.card_back_text);

            btnBack = view.findViewById(R.id.buttonBack);
            btnBack.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    // assign user input text to variable
                    backTxt = cardBackTxt.getText().toString();
                    // attach it with method to flashcard activity
                    listener.userInputBackSent(backTxt);
                    // flip flashcard to front side of card
                    ((Flashcard_create_Cards) getActivity()).flipToFrontSideCard();
                }

            });
            return view;
        }

            View view = inflater.inflate(R.layout.flashcard_card_back, container, false);

            cardBackTxt = view.findViewById(R.id.card_back_text);

            // get data passed from Flashcard_mainActivity
            String getArgument = getArguments().getString("back_data");
            cardBackTxt.setText(getArgument);

            // button to flip flashcard to front side of card
            btnBack = view.findViewById(R.id.buttonBack);
            btnBack.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    ((Flashcard_mainActivity)getActivity()).flipToFrontSideCard();
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
