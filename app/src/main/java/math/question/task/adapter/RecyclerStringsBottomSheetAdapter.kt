package math.question.task.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import math.question.task.R
import math.question.task.databinding.RecyclerBottomSheetItemBinding
import math.question.task.observer.OnBottomSheetItemClickListener
import math.question.task.view.activity.baseActivity.BaseActivity


class RecyclerStringsBottomSheetAdapter(
    val activity: BaseActivity, val ages: ArrayList<String>,
    var onBottomSheetItemClickListener: OnBottomSheetItemClickListener
) : RecyclerView.Adapter<RecyclerStringsBottomSheetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(activity)
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.recycler_bottom_sheet_item,
            parent,
            false
        ) as RecyclerBottomSheetItemBinding
        return RecyclerStringsBottomSheetAdapter.ViewHolder(binding)
    }

    override fun getItemCount() = ages.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = ages[holder.adapterPosition]
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(item, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(item)
        }

        holder.binding.tvBottomSheetItem.text = result

        holder.itemView.setOnClickListener {
            onBottomSheetItemClickListener.onBottomSheetItemClickListener(holder.adapterPosition)
            notifyDataSetChanged()
        }

    }

    class ViewHolder(var binding: RecyclerBottomSheetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}