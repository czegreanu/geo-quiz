package zegreanu.cristi.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {
    private val KEY_ANSWER_SHOWN = "answer_shown"

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView

    private var isAnswerShown: Boolean = false
    private var answerIsTrue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
        savedInstanceState?.let {
            isAnswerShown = it.getBoolean(KEY_ANSWER_SHOWN, isAnswerShown)
            setAnswerShownResult()
        }

        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            if (answerIsTrue)
                answerTextView.setText(R.string.true_button)
            else
                answerTextView.setText(R.string.false_button)
            isAnswerShown = true
            setAnswerShownResult()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(KEY_ANSWER_SHOWN, isAnswerShown)
    }

    private fun setAnswerShownResult() {
        setResult(RESULT_OK, Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown))
    }

    companion object {

        val EXTRA_ANSWER_IS_TRUE = "zegreanu.cristi.geoquiz.answer_is_true"
        val EXTRA_ANSWER_SHOWN = "zegreanu.cristi.geoquiz.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            var intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)

            return intent
        }
    }
}
