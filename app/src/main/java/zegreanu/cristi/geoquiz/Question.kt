package zegreanu.cristi.geoquiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val textResId: Int,
    val answerTrue: Boolean, var isCheated: Boolean = false
) : Parcelable
