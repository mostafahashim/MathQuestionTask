package math.question.task.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import math.question.task.observer.OnAskUserAction
import math.question.task.view.sub.PopupDialogAskUserAction

fun showMessage(
    activity: AppCompatActivity,
    title: String,
    msg: String,
    onAskUserAction: OnAskUserAction?,
    isShowNegativeButton: Boolean,
    negativeText: String,
    positiveText: String,
    isCancelable: Boolean
) {
    if (activity.isFinishing)
        return
    var popupDialogAskUserAction = PopupDialogAskUserAction()
    popupDialogAskUserAction.setOnAskUserActionObserver(object : OnAskUserAction {
        override fun onPositiveAction() {
            popupDialogAskUserAction.dismissAllowingStateLoss()
            onAskUserAction?.onPositiveAction()
        }

        override fun onNegativeAction() {
            onAskUserAction?.onNegativeAction()
        }
    })
    var bundle = Bundle()
    bundle.putString("title", title)
    bundle.putString("body", msg)
    bundle.putString("negativeButtonText", negativeText)
    bundle.putString("positiveButtonText", positiveText)
    bundle.putBoolean("isShowTitle", true)
    bundle.putBoolean("isShowNegativeButton", isShowNegativeButton)
    bundle.putBoolean("isShowPositiveButton", true)
    popupDialogAskUserAction.arguments = bundle
    popupDialogAskUserAction.isCancelable = isCancelable
    popupDialogAskUserAction.show(activity.supportFragmentManager, "PopupDialogAskUserAction")
}