package math.question.task.util

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import math.question.task.R

object ProgressDialogLoading {
    private var progressDialog: Dialog? = null

    fun show(activity: AppCompatActivity) {
        try {
            if (activity.isFinishing)
                return
            if (progressDialog != null && progressDialog!!.isShowing && !activity.isFinishing)
                dismiss(activity)
            progressDialog = Dialog(activity, R.style.MyDialog)
            progressDialog!!.window!!.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(activity, R.color.transparent_black))
            )
            progressDialog!!.window!!.attributes.windowAnimations =
                android.R.anim.cycle_interpolator
            progressDialog!!.show()
            progressDialog!!.setCancelable(true)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.setContentView(R.layout.custom_progress_dialog)
            var tvMSGCustomProgressDialog =
                progressDialog!!.findViewById<TextView>(R.id.tvMSG_customProgressDialog)
            tvMSGCustomProgressDialog.text = activity.getString(R.string.please_wait)
        } catch (e: Exception) {
        }
    }

    fun updateText(activity: AppCompatActivity,msg: String) {
        try {
            if (progressDialog != null && progressDialog!!.isShowing && !activity.isFinishing) {
                val tvMSGCustomProgressDialog =
                    progressDialog!!.findViewById<TextView>(R.id.tvMSG_customProgressDialog)
                tvMSGCustomProgressDialog.text = msg
            }
        } catch (e: Exception) {
        }
    }

    fun dismiss(activity: AppCompatActivity) {
        if (progressDialog != null && progressDialog!!.isShowing && !activity.isFinishing) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }
}