package com.example.oasis_fourth_task_quiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Quiz extends AppCompatActivity {
    private TextView questionTextView, results;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;
    private ProgressBar progressBar;

    private RelativeLayout mainQuiz;
    private CardView resultsScreen;
    private int questionNo = 0;  // Initialize question number to 0
    private int selectedAnswer;

    private String[] questions;
    private String[][] options;
    private int[] answers;

    private int score = 0;
    private int totalQuestions;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mainQuiz = findViewById(R.id.layoutRadio);

        questionTextView = findViewById(R.id.question);
        optionsRadioGroup = findViewById(R.id.answers);
        submitButton = findViewById(R.id.btns);
        progressBar = findViewById(R.id.progressBar);

        questions = getResources().getStringArray(R.array.questions);
        options = new String[][]{
                getResources().getStringArray(R.array.option1),
                getResources().getStringArray(R.array.option2),
                getResources().getStringArray(R.array.option3),
                getResources().getStringArray(R.array.option4),
                getResources().getStringArray(R.array.option5),
                getResources().getStringArray(R.array.option6),
        };
        mainQuiz.setVisibility(View.VISIBLE);
        answers = getResources().getIntArray(R.array.answers);
        totalQuestions = questions.length;
        ShowQuestionAndAnswers(questionNo);

        resultsScreen = findViewById(R.id.resultsTable);

        results = findViewById(R.id.viewResult);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswers();
            }
        });
    }

    private void ShowQuestionAndAnswers(int index) {
        questionTextView.setText(questions[index]);
        RadioButton[] checkbtn = new RadioButton[]{
                findViewById(R.id.option1),
                findViewById(R.id.option2),
                findViewById(R.id.option3),
                findViewById(R.id.option4),
        };

        for (int i = 0; i < checkbtn.length; i++) {
            checkbtn[i].setText(options[index][i]);
        }

        int progress = (int) (((double) (questionNo + 1) / totalQuestions) * 100);
        progressBar.setProgress(progress);
    }

    private void CheckAnswers() {
        try {
            int checkId = optionsRadioGroup.getCheckedRadioButtonId();
            if (checkId != -1) {  // Corrected to check if any option is selected
                RadioButton selectedbutton = findViewById(checkId);
                int selectedOptionIndex = optionsRadioGroup.indexOfChild(selectedbutton);
                if (selectedOptionIndex == answers[questionNo]) {
                    score++;
                }

                if (questionNo < totalQuestions - 1) {
                    questionNo++;
                    optionsRadioGroup.clearCheck();
                    ShowQuestionAndAnswers(questionNo);
                } else {
                    mainQuiz.setVisibility(View.GONE);
                    resultsScreen.setVisibility(View.VISIBLE);
                    // Show total result
                    String values = "Result: " + finalResult()+"/"+questions.length;
                    results.setText(values);
                }
            } else {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int finalResult() {
        return score;
    }
}