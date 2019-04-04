package fleacircus.com.learningproject;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Quiz_mainActivity extends AppCompatActivity {

    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_OPTION4 = "option4";

    private Button opt1, opt2, opt3, opt4;
    private TextView questionTxt;
    private int total, questionNo, questionCount, correct, incorrect;
    private String quizName;

    // get the User ID
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_mainactivity);

        opt1 = findViewById(R.id.buttonOpt1);
        opt2 = findViewById(R.id.buttonOpt2);
        opt3 = findViewById(R.id.buttonOpt3);
        opt4 = findViewById(R.id.buttonOpt4);

        questionTxt = findViewById(R.id.questionsTxt);

        Intent quizList = getIntent();
        quizName = quizList.getStringExtra("qName");

        total = 0;
        questionNo = 1;
        // use minus 1 (-1) so that the generateQuestion if statement doesn't trigger before count gotten from firestore
        questionCount = -1;
        correct = 0;
        incorrect = 0;

        generateQuestions();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_quiz:
                ExitQuiz();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ExitQuiz() {
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivity(intent);
    }

    private void generateQuestions() {

        if (total == questionCount) {
            Intent startResult = new Intent(Quiz_mainActivity.this, Quiz_resultActivity.class);
            startResult.putExtra("total", String.valueOf(total));
            startResult.putExtra("correct", String.valueOf(correct));
            startResult.putExtra("incorrect", String.valueOf(incorrect));
            startActivity(startResult);
        }

        else {
            // points towards first question
            // retrieve questions from the user's Quizzes and Quiz Name
            DocumentReference docRef = db.collection("Quizzes").document(uid)
                    .collection(quizName + "_" + uid).document(quizName);
            docRef.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot snapshot = task.getResult();

                            if (snapshot.exists()) {

                                questionCount = snapshot.getDouble("count").intValue();
                                String quizQuestion = snapshot.getString(KEY_QUESTION+"_"+questionNo);
                                questionTxt.setText(quizQuestion);
                                String quizOption1 = snapshot.getString(KEY_OPTION1+"_"+questionNo);
                                opt1.setText(quizOption1);
                                String quizOption2 = snapshot.getString(KEY_OPTION2+"_"+questionNo);
                                opt2.setText(quizOption2);
                                String quizOption3 = snapshot.getString(KEY_OPTION3+"_"+questionNo);
                                opt3.setText(quizOption3);
                                String quizOption4 = snapshot.getString(KEY_OPTION4+"_"+questionNo);
                                opt4.setText(quizOption4);

                                final String quizAnswer = snapshot.getString(KEY_ANSWER+"_"+questionNo);

                                opt1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (opt1.getText().toString().equals(quizAnswer)) {
                                            // button = correct answer
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                            // change button background color to Green
                                            opt1.setBackgroundColor(Color.GREEN);
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // increase Correct Answers by +1
                                                    correct = correct + 1;
                                                    // increase Total No. of Questions Answered by +1
                                                    total = total + 1;
                                                    questionNo = questionNo + 1;
                                                    // set button background color back to std color
                                                    opt1.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        } else {
                                            // button = incorrect answer
                                            // increase Incorrect Answers by +1
                                            incorrect = incorrect + 1;
                                            // increase Total No. of Questions Answered by +1
                                            total = total + 1;
                                            questionNo = questionNo + 1;
                                            // set button background color to Red
                                            opt1.setBackgroundColor(Color.RED);
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();

                                            if (opt2.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt2.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt3.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt3.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt4.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt4.setBackgroundColor(Color.GREEN);
                                            }

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // return all button colors back to std color
                                                    opt1.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt2.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt3.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt4.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    // generate next question
                                                    generateQuestions();

                                                }
                                            }, 1500);
                                        }
                                    }
                                });

                                opt2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (opt2.getText().toString().equals(quizAnswer)) {
                                            // button = correct answer
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                            // change button background color to Green
                                            opt2.setBackgroundColor(Color.GREEN);
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // increase Correct Answers by +1
                                                    correct = correct + 1;
                                                    // increase Total No. of Questions Answered by +1
                                                    total = total + 1;
                                                    questionNo = questionNo + 1;
                                                    // set button background color back to std color
                                                    opt2.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        } else {
                                            // button = incorrect answer
                                            // increase Incorrect Answers by +1
                                            incorrect = incorrect + 1;
                                            // increase Total No. of Questions Answered by +1
                                            total = total + 1;
                                            questionNo = questionNo + 1;
                                            // set button background color to Red
                                            opt2.setBackgroundColor(Color.RED);
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();

                                            if (opt1.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt1.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt3.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt3.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt4.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt4.setBackgroundColor(Color.GREEN);
                                            }

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // return all button colors back to std color
                                                    opt1.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt2.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt3.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt4.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    // generate next question
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        }
                                    }
                                });

                                opt3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (opt3.getText().toString().equals(quizAnswer)) {
                                            // button = correct answer
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                            // change button background color to Green
                                            opt3.setBackgroundColor(Color.GREEN);
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // increase Correct Answers by +1
                                                    correct = correct + 1;
                                                    // increase Total No. of Questions Answered by +1
                                                    total = total + 1;
                                                    questionNo = questionNo + 1;
                                                    // set button background color back to std color
                                                    opt3.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        } else {
                                            // button = incorrect answer
                                            // increase Incorrect Answers by +1
                                            incorrect = incorrect + 1;
                                            // increase Total No. of Questions Answered by +1
                                            total = total + 1;
                                            questionNo = questionNo + 1;
                                            // set button background color to Red
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();
                                            opt3.setBackgroundColor(Color.RED);

                                            if (opt1.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt1.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt2.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt2.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt4.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt4.setBackgroundColor(Color.GREEN);
                                            }

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // return all button colors back to std color
                                                    opt1.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt2.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt3.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt4.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    // generate next question
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        }
                                    }
                                });

                                opt4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (opt4.getText().toString().equals(quizAnswer)) {
                                            // button = correct answer
                                            Toast.makeText(Quiz_mainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                            // change button background color to Green
                                            opt4.setBackgroundColor(Color.GREEN);
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // increase Correct Answers by +1
                                                    correct = correct + 1;
                                                    // increase Total No. of Questions Answered by +1
                                                    total = total + 1;
                                                    questionNo = questionNo + 1;
                                                    // set button background color back to std color
                                                    opt4.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        } else {
                                            // button = incorrect answer
                                            // increase Incorrect Answers by +1
                                            incorrect = incorrect + 1;
                                            // increase Total No. of Questions Answered by +1
                                            total = total + 1;
                                            questionNo = questionNo + 1;
                                            // set button background color to Red
                                            opt4.setBackgroundColor(Color.RED);

                                            if (opt1.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt1.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt2.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt2.setBackgroundColor(Color.GREEN);
                                            }

                                            if (opt3.getText().toString().equals(quizAnswer)) {
                                                // change button color to Green if correct answer
                                                opt3.setBackgroundColor(Color.GREEN);
                                            }

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // return all button colors back to std color
                                                    opt1.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt2.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt3.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    opt4.setBackgroundColor(Color.parseColor("#4d4dff"));
                                                    // generate next question
                                                    generateQuestions();
                                                }
                                            }, 1500);
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(Quiz_mainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}