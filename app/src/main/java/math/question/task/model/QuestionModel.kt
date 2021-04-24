package math.question.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Comparator

@Entity
data class QuestionModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var firstNumber: String? = "",
    var secondNumber: String? = "",
    var answer: String? = "",
    var operator: String? = "",
    var operatorText: String? = "",
    var delayTime: Int = 0,
    var holderType: String? = ""
) : Serializable
