package zegreanu.cristi.geoquiz

data class Question(val textResId: Int, val answerTrue: Boolean, var userAnswered: Boolean = false)
