package ru.example.me.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviosButton;
    private TextView mQuestionTextView;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static int countOfRightAnswers = 0;
    private int mCurrentIndex = 0;
    private int countOfAnswer = 0;

    private Question[] mQuestions = new Question[]{
            new Question(R.string.q_afraid, false),
            new Question(R.string.q_bel, true),
            new Question(R.string.q_duma, true),
            new Question(R.string.q_riv, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
//            logger.info("mCurrentIndex = " + String.valueOf(mCurrentIndex));
            countOfRightAnswers = savedInstanceState.getInt("countOfRightAnswers", 0);
            countOfAnswer = savedInstanceState.getInt("countOfAnswer", 0);
        }
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

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

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });
        mPreviosButton = (ImageButton) findViewById(R.id.prev_button);
        mPreviosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex != 0) {
                    mCurrentIndex = (mCurrentIndex - 1);
                } else {
                    mCurrentIndex = mQuestions.length - 1;
                }
                updateQuestion();
            }
        });
    }

    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        countOfAnswer++;
        Log.i("nextQuestion", String.valueOf(countOfAnswer));
        updateQuestion();
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
            countOfRightAnswers++;
            Log.i("countOfRightAnswers", String.valueOf(countOfRightAnswers));
            nextQuestion();
        } else {
            messageResId = R.string.incorrect_answer;
            nextQuestion();
        }
        if (countOfAnswer == mQuestions.length) {
            int procentcOfRecognize = (int)(countOfRightAnswers * 100.0f / mQuestions.length);

            Toast.makeText(this, "Процент правильных ответов: " + procentcOfRecognize + "%",
                    Toast.LENGTH_SHORT).show();
            countOfRightAnswers = 0;
            countOfRightAnswers = 0;
            countOfRightAnswers = 0;
            nextQuestion();
            return;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "savedInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt("countOfRightAnswers", countOfRightAnswers);
        savedInstanceState.putInt("countOfAnswer", countOfAnswer);
    }

    /**
     * 89 стр
     */
}
