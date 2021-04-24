package math.question.task.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import math.question.task.model.QuestionModel

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "Calculate") {
            val bundle = intent!!.extras
            var questionModel = bundle!!.getParcelable("QuestionModel") as QuestionModel?
            var test = bundle.getString("test")
            ContextCompat.startForegroundService(context!!, intent)
        }
    }
}