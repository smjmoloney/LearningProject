package fleacircus.com.learningproject.Flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
 * A fragment representing the front of the card.
 */
@SuppressLint("ValidFragment")
public class Flashcard_CardFrontFragment extends Fragment {

    private EditText cardFrontTxt;
    private String frontTxt, newText;
    private FragmentFrontListener listener;

    // method to be used in connection with user inputted and passing into flashcard activity
    public interface FragmentFrontListener {
        void userInputFrontSent(String input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Button btnFront;
        if(getActivity() instanceof Flashcard_create_Cards) {

            View view = inflater.inflate(R.layout.flashcard_card_front, container, false);

            cardFrontTxt = view.findViewById(R.id.card_front_text);

            btnFront = view.findViewById(R.id.buttonFront);
            btnFront.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    // assign user input text to variable
                    frontTxt = cardFrontTxt.getText().toString();
                    // attach it with method to flashcard activity
                    listener.userInputFrontSent(frontTxt);
                    // flip flashcard method
                    ((Flashcard_create_Cards)getActivity()).flipToReverseSideCard();
                }

            });

            // use Floating Action Button to add questions
            FloatingActionButton fab = view.findViewById(R.id.saveFlashcardBtn);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Flashcard_create_Cards)getActivity()).saveFlashcard();
                }
            });

            return view;
        }

        View view = inflater.inflate(R.layout.flashcard_card_front_main, container, false);

        cardFrontTxt = view.findViewById(R.id.card_front_text_main);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String data = bundle.getString("FRONT_DATA");//Get pass data with its key value
            cardFrontTxt.setText(data);
        }

        cardFrontTxt.setText(newText);

        btnFront = view.findViewById(R.id.buttonFrontMain);
        btnFront.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ((Flashcard_mainActivity) getActivity()).flipToReverseSideCard();
            }
        });

        // use Floating Action Button to add questions
        FloatingActionButton fab = view.findViewById(R.id.nextFlashcardBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Flashcard_mainActivity)getActivity()).generateFlashcards();
            }
        });



        return view;
    }

    // method called when fragment attached to activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // check if activity implements this interface
        if(context instanceof FragmentFrontListener) {
            // take listener variable and assign it to activity
            listener = (FragmentFrontListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "missing FragmentFrontListener");
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