package math.question.task

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import math.question.task.util.Preferences

class CustomViewBindings {
    companion object {
        @BindingAdapter("htmlText")
        @JvmStatic
        fun setHtmlTextValue(textView: TextView, htmlText: String?) {
            if (htmlText == null) return
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlText)
            }
            textView.text = result
        }

        @BindingAdapter("adapter")
        @JvmStatic
        fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            recyclerView.adapter = adapter
        }

        @BindingAdapter("bind:layoutHeight")
        @JvmStatic
        fun setHeight(view: View, height: Double) {
            view.layoutParams.height = height.toInt()
            view.requestLayout()
        }

        @BindingAdapter("bind:layoutWidth")
        @JvmStatic
        fun setWidth(view: View, width: Double) {
            view.layoutParams.width = width.toInt()
            view.requestLayout()
        }

    }
}