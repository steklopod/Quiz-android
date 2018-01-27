package ru.example.me.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 127 стр
 */
public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPreviosButton;
    private TextView mQuestionTextView;

    private boolean isPodskazka;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CODE_CHEAT = 0;
    private static int countOfRightAnswers = 0;
    private static int countOfPodskazok = 0;
    private static int mCurrentIndex = 0;
    private static int countOfAnswer = 0;

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
//            Log.d("onCreate", "mCurrentIndex " + String.valueOf(mCurrentIndex));
//            Log.d("onCreate", "countOfRightAnswers " +String.valueOf(countOfRightAnswers));
//            Log.d("onCreate","countOfAnswer " + String.valueOf(countOfAnswer));

            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                countOfAnswer--;
                updateQuestion();
            }
        });

//      Новая активность - подсказка.
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
//              startActivity(intent);
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        countOfAnswer++;
        isPodskazka = false;
        updateQuestion();
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
//        Log.d(TAG, "Проблема с update", new Exception("ЮЮЮЮЮЮЮЮЮЮЮЮЮ"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(boolean userPressTrue) {
        boolean isAnswerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (isPodskazka) {
            countOfPodskazok++;
            if (countOfPodskazok > 2) {
                CheatActivity.hideButton(mCheatButton);
                messageResId = R.string.end_of_podskazok;
            } else {
                messageResId = R.string.judgement_toast;
            }
            nextQuestion();
        } else {
            if (isAnswerTrue == userPressTrue) {
                messageResId = R.string.correct_answer;
                countOfRightAnswers++;
                nextQuestion();
            } else {
                messageResId = R.string.incorrect_answer;
                nextQuestion();
            }
        }
        if (countOfAnswer == mQuestions.length) {
            int procentcOfRecognize = (int) (countOfRightAnswers * 100.0f / mQuestions.length);
            Toast.makeText(this, "Процент правильных ответов: " + procentcOfRecognize + "%",
                    Toast.LENGTH_SHORT).show();
            nextQuestion();
            countOfRightAnswers = 0;
            mCurrentIndex = 0;
            countOfAnswer = 0;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            isPodskazka = CheatActivity.wasAnswerShown(data);
            Log.d(TAG, "isPodskazka = " + isPodskazka);
        }
    }
}
