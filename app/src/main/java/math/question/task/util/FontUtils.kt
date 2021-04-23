package math.question.task.util

import android.text.Spannable
import android.text.style.ForegroundColorSpan


class FontUtils {

    fun setColorForPath(spannable: Spannable, paths: Array<String>, color: Int) {
        for (i in paths.indices) {
            val indexOfPath = spannable.toString().indexOf(paths[i])
            if (indexOfPath == -1) {
                continue
            }
            spannable.setSpan(
                ForegroundColorSpan(color), indexOfPath,
                indexOfPath + paths[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}