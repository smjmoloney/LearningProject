package fleacircus.com.learningproject.Flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

    private static final String FRONT_DATA = "front_data";

    private EditText cardFrontTxt;
    private String frontTxt;
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
                    // flip flashcard to reverse side of card
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

        // get data passed from Flashcard_mainActivity
        String getArgument = getArguments().getString(FRONT_DATA);
        cardFrontTxt.setText(getArgument);

        // button to flip flashcard to reverse side of card
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

    public static Flashcard_CardFrontFragment newInstance(String text) {
        Flashcard_CardFrontFragment cardFront = new Flashcard_CardFrontFragment();
        Bundle args = new Bundle();
        args.putString(FRONT_DATA, text);
        cardFront.setArguments(args);
        return cardFront;
    }
}