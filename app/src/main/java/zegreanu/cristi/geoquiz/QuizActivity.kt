package zegreanu.cristi.geoquiz

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val mTrueButton = findViewById<Button>(R.id.true_button)
        mTrueButton?.setOnClickListener {
            Toast.makeText(
                this@QuizActivity,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).run {
                setGravity(Gravity.TOP, 0, 0)
                show()
            }
        }

        val mFalseButton = findViewById<Button>(R.id.false_button)
        mFalseButton?.setOnClickListener {
            Toast.makeText(
                this@QuizActivity,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).run {
                setGravity(Gravity.TOP, 0, 0)
                show()
            }
        }
    }
}
