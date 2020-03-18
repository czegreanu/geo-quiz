package zegreanu.cristi.geoquiz

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            Toast.makeText(
                this@QuizActivity,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).run {
                setGravity(Gravity.TOP, 0, 0)
                show()
            }
        }

        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
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
