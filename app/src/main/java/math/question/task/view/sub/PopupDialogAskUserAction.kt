package math.question.task.view.sub

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import math.question.task.R
import math.question.task.databinding.PopupDialogSureBinding
import math.question.task.observer.OnAskUserAction
import math.question.task.view.activity.baseActivity.BaseActivity

class PopupDialogAskUserAction : BaseDialogFragment() {

    internal var activity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            activity = context
        }
    }

    lateinit var binding: PopupDialogSureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity == null) activity = getActivity() as BaseActivity?
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.popup_dialog_sure, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    lateinit var onAskUserAction: OnAskUserAction

    fun setOnAskUserActionObserver(onAskUserAction: OnAskUserAction) {
        this.onAskUserAction = onAskUserAction
    }

    internal lateinit var dialog: Dialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null)
            activity = requireActivity() as BaseActivity?
        dialog = Dialog(requireActivity())
        dialog = Dialog(requireActivity(), R.style.FullWidthDialogTheme)
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
        setListener()
    }


    var title = ""
    var body = ""
    var negativeButtonText = ""
    var positiveButtonText = ""
    var isShowTitle = false
    var isShowNegativeButton = false
    var isShowPositiveButton = false

    fun initializeViews() {
        getArgumentsData()
        setData()
    }

    fun getArgumentsData() {
        if (arguments != null) {
            title = requireArguments().getString("title", "")
            body = requireArguments().getString("body", "")
            negativeButtonText = requireArguments().getString("negativeButtonText", "")
            positiveButtonText = requireArguments().getString("positiveButtonText", "")
            isShowTitle = requireArguments().getBoolean("isShowTitle", false)
            isShowNegativeButton = requireArguments().getBoolean("isShowNegativeButton", false)
            isShowPositiveButton = requireArguments().getBoolean("isShowPositiveButton", false)
        }
    }

    fun setData() {
        binding.txtviewDialogSureTitle.text = title
        binding.txtviewDialogSureBody.text = body
        binding.btnDialogSureCancel.text = negativeButtonText
        binding.btnDialogSureSubmit.text = positiveButtonText
        binding.btnDialogSureOk.text = positiveButtonText

        binding.txtviewDialogSureTitle.visibility = if (isShowTitle) View.VISIBLE else View.GONE
        binding.layoutActionsDialog.visibility =
            if (isShowNegativeButton) View.VISIBLE else View.GONE
        binding.btnDialogSureOk.visibility = if (isShowNegativeButton) View.GONE else View.VISIBLE
    }

    fun setListener() {
        binding.btnDialogSureSubmit.setOnClickListener {
            if (::onAskUserAction.isInitialized)
                onAskUserAction.onPositiveAction()
            dismissAllowingStateLoss()
        }
        binding.btnDialogSureOk.setOnClickListener {
            if (::onAskUserAction.isInitialized)
                onAskUserAction.onPositiveAction()
            dismissAllowingStateLoss()
        }

        binding.btnDialogSureCancel.setOnClickListener {
            if (::onAskUserAction.isInitialized)
                onAskUserAction.onNegativeAction()
            dismissAllowingStateLoss()
        }

    }

}