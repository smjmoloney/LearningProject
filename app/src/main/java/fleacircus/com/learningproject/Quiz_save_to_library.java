package fleacircus.com.learningproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Quiz_save_to_library extends AppCompatDialogFragment {

    private EditText newQName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userQuizName;
    private int count;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.quiz_create_namedialogbox, null);

        // retrieve intent from previous activity (Quiz_mainActivity_College) and store data in variables
        Bundle mArgs = getArguments();
        String collegeQuizName = mArgs.getString("quiz_Name");
        String collegeLocation = mArgs.getString("college_location");


        // retrieve quiz from college library
        final DocumentReference collegeRef = db.collection("Quizzes").document(collegeLocation)
                .collection(collegeQuizName + " - " + collegeLocation).document(collegeQuizName);

        // set up the User's library
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DocumentReference userRef = db.collection("Quizzes").document(uid);

        builder.setView(view)
                .setTitle("Save Quiz")
                .setMessage("Do you wish to save to your personal library?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();

                    }
                });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                collegeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {

                        // get the Flashcard Name from the input String
                        userQuizName = newQName.getText().toString();
                        count = snapshot.getDouble("count").intValue();

                        // add the Flashcard to the user's FlashcardSets using Flashcard Name
                        Map<String, Object> newQuiz = new HashMap<>();
                        newQuiz.put("count", count);

                        int i;
                        for (i = 1; i <=count; i++) {

                            // get Quiz questions/answers/options from FireStore
                            // use i as counter for quiz number
                            String question = snapshot.getString("question_" + i);
                            String answer = snapshot.getString("answer_" + i);
                            String option_1 = snapshot.getString("option1_" + i);
                            String option_2 = snapshot.getString("option2_" + i);
                            String option_3 = snapshot.getString("option3_" + i);
                            String option_4 = snapshot.getString("option4_" + i);

                            // add Card Front and Back from firestore to HashMap
                            // use i as counter for flashcard number
                            newQuiz.put("question" + "_" + i, question);
                            newQuiz.put("answer" + "_" + i, answer);
                            newQuiz.put("option1_" + i, option_1);
                            newQuiz.put("option2_" + i, option_2);
                            newQuiz.put("option3_" + i, option_3);
                            newQuiz.put("option4_" + i, option_4);
                        }

                        // add the Flashcard to the user's FlashcardListing using new Flashcard Name
                        Map<String, Object> quizList = new HashMap<>();
                        quizList.put("name", userQuizName);
                        db.collection("QuizList").document(uid)
                                .collection("quizList_"+uid).document(userQuizName)
                                .set(quizList);

                        // add the Flashcard to the user's FlashcardSet using new Flashcard Name
                        userRef.collection(userQuizName + "_" + uid).document(userQuizName)
                                .set(newQuiz)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Log.d("Save_to_Library", "Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Save_to_Library", "Error!");
                                    }
                                });
                    }
                });
            }
        });


        newQName = view.findViewById(R.id.newQuizName);


        return builder.create();
    }
}
