package zegreanu.cristi.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val KEY_QUESTIONS = "questions"
    private val REQUEST_CODE_CHEAT = 0

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private var questionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate(Bundle) called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        savedInstanceState?.let {
            currentIndex = it.getInt(KEY_INDEX, 0)
            questionBank = it.getParcelableArray(KEY_QUESTIONS) as Array<Question>
        }

        questionTextView = findViewById(R.id.question_text_view)
        updateQuestion()

        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        cheatButton = findViewById(R.id.cheat_button)
        cheatButton.setOnClickListener {
            startActivityForResult(
                CheatActivity.newIntent(this, questionBank[currentIndex].answerTrue),
                REQUEST_CODE_CHEAT
            )
        }

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data != null) {
                questionBank[currentIndex].isCheated =
                    data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false)
            }
        }
    }

    // region Activity lifecycle
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.apply {
            putInt(KEY_INDEX, currentIndex)
            putParcelableArray(KEY_QUESTIONS, questionBank)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    // endregion

    //region Private helpers
    private fun updateQuestion() {
        val question = questionBank[currentIndex].textResId
        questionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = questionBank[currentIndex].answerTrue
        val messageResId =
            when {
                questionBank[currentIndex].isCheated -> R.string.judgment_toast
                userPressedTrue == answerIsTrue -> R.string.correct_toast
                else -> R.string.incorrect_toast
            }

        Toast.makeText(
            this@QuizActivity,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }
    // endregion
}
