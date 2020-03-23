package zegreanu.cristi.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd

class CheatActivity : AppCompatActivity() {
    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView

    private var answerIsTrue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            if (answerIsTrue)
                answerTextView.setText(R.string.true_button)
            else
                answerTextView.setText(R.string.false_button)

            setAnswerShownResult(true)
            showAnimation()
        }
    }

    private fun showAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx: Int = showAnswerButton.width / 2
            val cy: Int = showAnswerButton.height / 2
            val radius: Float = showAnswerButton.width.toFloat()
            val anim =
                ViewAnimationUtils.createCircularReveal(
                    showAnswerButton,
                    cx,
                    cy,
                    radius,
                    0f
                )
            anim.apply {
                doOnEnd { showAnswerButton.visibility = View.INVISIBLE }
                start()
            }
        } else
            showAnswerButton.visibility = View.INVISIBLE
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        setResult(RESULT_OK, Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown))
    }

    companion object {

        val EXTRA_ANSWER_IS_TRUE = "zegreanu.cristi.geoquiz.answer_is_true"
        val EXTRA_ANSWER_SHOWN = "zegreanu.cristi.geoquiz.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)

            return intent
        }
    }
}
