package math.question.task.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object MathUtils {

    fun getDecimalFormat(number: Double): String {
        try {
            val format = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
            format.applyPattern("0.00")
            return format.format(number)
        } catch (e: Exception) {
            return "0.00"
        }
    }

    fun convertArabicNumbersToEnglish(value: String): String {
        return value.replace("١".toRegex(), "1").replace("٢".toRegex(), "2")
            .replace("٣".toRegex(), "3")
            .replace("٤".toRegex(), "4").replace("٥".toRegex(), "5").replace("٦".toRegex(), "6")
            .replace("٧".toRegex(), "7").replace("٨".toRegex(), "8").replace("٩".toRegex(), "9")
            .replace("٠".toRegex(), "0")
    }

}