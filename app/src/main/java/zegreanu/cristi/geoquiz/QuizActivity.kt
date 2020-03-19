package zegreanu.cristi.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val KEY_CORRECT_ANSWERS = "correctAnswers"
    private val KEY_TOTAL_ANSWERS = "totalAnswers"

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
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
    private var correctAnswers: Int = 0
    private var totalAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate(Bundle) called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS, 0)
            totalAnswers = savedInstanceState.getInt(KEY_TOTAL_ANSWERS, 0)
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

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            showButtons()
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
            putInt(KEY_CORRECT_ANSWERS, correctAnswers)
            putInt(KEY_TOTAL_ANSWERS, totalAnswers)
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

    private fun updateQuestion() {
        val question = questionBank[currentIndex].textResId
        questionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        questionBank[currentIndex].userAnswered = true
        totalAnswers += 1
        hideButtons()

        val answerIsTrue = questionBank[currentIndex].answerTrue
        var messageResId = 0
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast
            correctAnswers += 1
        } else
            messageResId = R.string.incorrect_toast

        if (totalAnswers == questionBank.size) {
            Toast.makeText(
                this@QuizActivity,
                String.format("FINISH! Score: ${100 * correctAnswers / totalAnswers}%%"),
                Toast.LENGTH_SHORT
            ).show()
            // Reset Game
            correctAnswers = 0
            totalAnswers = 0
        } else
            Toast.makeText(
                this@QuizActivity,
                messageResId,
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun hideButtons() {
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
    }

    private fun showButtons() {
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }
}
