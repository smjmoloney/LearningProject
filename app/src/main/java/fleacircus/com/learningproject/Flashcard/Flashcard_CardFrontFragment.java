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
import fleacircus.com.learningproject.Flashcard_mainActivity_college;
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

        // if parent activity is FLASHCARD CREATE CARDS
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
                    // call method from parent Activity
                    // flip flashcard to reverse side of card
                    ((Flashcard_create_Cards)getActivity()).flipToReverseSideCard();
                }

            });

            // use Floating Action Button to add flashcard details
            FloatingActionButton save = view.findViewById(R.id.saveFlashcardBtn);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // call method from parent Activity
                    ((Flashcard_create_Cards)getActivity()).saveFlashcard();
                }
            });

            return view;
        }

        // if parent activity is FLASHCARD MAIN_ACTIVITY COLLEGE
        if(getActivity() instanceof Flashcard_mainActivity_college) {

            View view = inflater.inflate(R.layout.flashcard_card_front_main, container, false);

            cardFrontTxt = view.findViewById(R.id.card_front_text_main);

            // get data passed from Flashcard_mainActivity
            String getArgument = getArguments().getString(FRONT_DATA);
            cardFrontTxt.setText(getArgument);

            // button to flip flashcard to reverse side of card
            btnFront = view.findViewById(R.id.buttonFrontMain);
            btnFront.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // call method from parent Activity
                    ((Flashcard_mainActivity_college) getActivity()).flipToReverseSideCard();
                }
            });

            // use Floating Action Button to go to next Flashcard
            FloatingActionButton next = view.findViewById(R.id.nextFlashcardBtn);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // call method from parent Activity
                    ((Flashcard_mainActivity_college)getActivity()).generateNextFlashcards();
                }
            });

            // use Floating Action Button to go to previous Flashcard
            FloatingActionButton prev = view.findViewById(R.id.beforeFlashcardBtn);
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // call method from parent Activity
                    ((Flashcard_mainActivity_college)getActivity()).generatePrevFlashcards();
                }
            });

            return view;
        }

        // if parent activity is FLASHCARD MAIN_ACTIVITY
        View view = inflater.inflate(R.layout.flashcard_card_front_main, container, false);

        cardFrontTxt = view.findViewById(R.id.card_front_text_main);

        // get data passed from Flashcard_mainActivity
        String getArgument = getArguments().getString(FRONT_DATA);
        cardFrontTxt.setText(getArgument);

        // button to flip flashcard to reverse side of card
        btnFront = view.findViewById(R.id.buttonFrontMain);
        btnFront.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // call method from parent Activity
                ((Flashcard_mainActivity) getActivity()).flipToReverseSideCard();
            }
        });

        // use Floating Action Button to go to next Flashcard
        FloatingActionButton next = view.findViewById(R.id.nextFlashcardBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call method from parent Activity
                ((Flashcard_mainActivity)getActivity()).generateNextFlashcards();
            }
        });

        // use Floating Action Button to go to previous Flashcard
        FloatingActionButton prev = view.findViewById(R.id.beforeFlashcardBtn);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call method from parent Activity
                ((Flashcard_mainActivity)getActivity()).generatePrevFlashcards();
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