package math.question.task.view.activity.addNewTask

import androidx.appcompat.app.AppCompatActivity
import math.question.task.R
import math.question.task.observer.OnAskUserAction
import math.question.task.util.showMessage

fun turnGPSOn(activity: AppCompatActivity, onAskUserAction: OnAskUserAction) {
    showMessage(
        activity,
        activity.getString(R.string.location_required),
        activity.getString(R.string.you_must_enable_device_location_to_get_data_based_on_your_location),
        object : OnAskUserAction {
            override fun onPositiveAction() {
                onAskUserAction.onPositiveAction()
            }

            override fun onNegativeAction() {
            }
        },
        false,
        activity.getString(R.string.cancel),
        activity.getString(R.string.ok),
        false
    )
}