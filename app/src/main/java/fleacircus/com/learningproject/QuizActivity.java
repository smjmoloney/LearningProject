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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

    public class QuizActivity extends AppCompatActivity {

        Button opt1, opt2, opt3, opt4;
        TextView questionTxt;
        int total = 0;
        int correct = 0;
        int incorrect = 0;
        int questionNo = 0;
        DatabaseReference reference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);

            opt1 = findViewById(R.id.buttonOpt1);
            opt2 = findViewById(R.id.buttonOpt2);
            opt3 = findViewById(R.id.buttonOpt3);
            opt4 = findViewById(R.id.buttonOpt4);

            questionTxt = findViewById(R.id.questionsTxt);

            generateQuestions();

        }

        // create quiz menu
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.quiz_menu, menu);
            return true;
        }

        // menu options for quiz
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
            // return to Home page when exiting
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        }

        private void generateQuestions() {

            questionNo = questionNo + 1;

            if (questionNo > 4) {
                // when number of questions reaches 4 end quiz and store info from variables for future use in Results page
                Intent startResult = new Intent(QuizActivity.this, ResultActivity.class);
                startResult.putExtra("total", String.valueOf(total));
                startResult.putExtra("correct", String.valueOf(correct));
                startResult.putExtra("incorrect", String.valueOf(incorrect));
                startActivity(startResult);
            } else {
                // points towards first question
                reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(questionNo));
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Question question = dataSnapshot.getValue(Question.class);
                        questionTxt.setText(question.getQuestion());
                        opt1.setText(question.getOption1());
                        opt2.setText(question.getOption2());
                        opt3.setText(question.getOption3());
                        opt4.setText(question.getOption4());

                        opt1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (opt1.getText().toString().equals(question.getAnswer())) {
                                    // button = correct answer
                                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
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
                                    // set button background color to Red
                                    opt1.setBackgroundColor(Color.RED);
                                    Toast.makeText(QuizActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();

                                    if (opt2.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt2.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt3.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt3.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt4.getText().toString().equals(question.getAnswer())) {
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
                                if (opt2.getText().toString().equals(question.getAnswer())) {
                                    // button = correct answer
                                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
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
                                    // set button background color to Red
                                    opt2.setBackgroundColor(Color.RED);
                                    Toast.makeText(QuizActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();

                                    if (opt1.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt1.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt3.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt3.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt4.getText().toString().equals(question.getAnswer())) {
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
                                if (opt3.getText().toString().equals(question.getAnswer())) {
                                    // button = correct answer
                                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
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
                                    // set button background color to Red
                                    Toast.makeText(QuizActivity.this, "You Are Incorrect", Toast.LENGTH_SHORT).show();
                                    opt3.setBackgroundColor(Color.RED);

                                    if (opt1.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt1.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt2.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt2.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt4.getText().toString().equals(question.getAnswer())) {
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
                                if (opt4.getText().toString().equals(question.getAnswer())) {
                                    // button = correct answer
                                    Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
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
                                    // set button background color to Red
                                    opt4.setBackgroundColor(Color.RED);

                                    if (opt1.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt1.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt2.getText().toString().equals(question.getAnswer())) {
                                        // change button color to Green if correct answer
                                        opt2.setBackgroundColor(Color.GREEN);
                                    }

                                    if (opt3.getText().toString().equals(question.getAnswer())) {
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // empty unused method
                    }
                });
            }
        }
}