package ru.example.me.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestions = new Question[]{
            new Question(R.string.q_afraid, false),
            new Question(R.string.q_bel, true),
            new Question(R.string.q_duma, true),
            new Question(R.string.q_riv, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                System.err.println(mCurrentIndex);
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressTrue) {
        boolean isAnswerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (isAnswerTrue == userPressTrue) {
            messageResId = R.string.correct_answer;
        } else {
            messageResId = R.string.incorrect_answer;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
