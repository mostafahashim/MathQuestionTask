package math.question.task.view.activity.addNewTask

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import math.question.task.R
import math.question.task.databinding.ActivityAddNewTaskBinding
import math.question.task.databinding.ActivityMainBinding
import math.question.task.observer.OnBottomSheetItemClickListener
import math.question.task.view.activity.baseActivity.BaseActivity
import math.question.task.view.sub.BottomSheetStringsFragment

class AddNewTaskActivity : BaseActivity(
    R.string.add_new_task, false, true, true,
    true, false, false, false, false,
), AddNewTaskViewModel.Observer {

    lateinit var binding: ActivityAddNewTaskBinding
    override fun doOnCreate(arg0: Bundle?) {
        binding = putContentView(R.layout.activity_add_new_task) as ActivityAddNewTaskBinding
        binding.viewModel =
            ViewModelProvider(
                this,
                AddNewTaskViewModelFactory(application)
            )
                .get(AddNewTaskViewModel::class.java)
        binding.viewModel!!.baseViewModelObserver = baseViewModelObserver
        binding.viewModel!!.observer = this
        binding.lifecycleOwner = this
        initializeViews()
        setListener()
    }

    override fun initializeViews() {
    }

    override fun setListener() {
        binding.viewModel!!.isShowFirstNumberError.observe(this, Observer {
            if (it && lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.edttextFirstNumber.error = getString(R.string.required_field)
            } else {
                binding.edttextFirstNumber.error = null
            }
        })

        binding.viewModel!!.isShowSecondNumberError.observe(this, Observer {
            if (it && lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (binding.viewModel?.secondNumber?.value?.isEmpty()!!)
                    binding.edttextSecondNumber.error = getString(R.string.required_field)
                else if (binding.viewModel?.selectedOperatorPosition == 3)
                    binding.edttextSecondNumber.error = getString(R.string.you_cant_divide_on_zero)
            } else {
                binding.edttextSecondNumber.error = null
            }
        })

        binding.viewModel!!.isShowOperatorError.observe(this, Observer {
            if (it && lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.edttextOperator.error = getString(R.string.select_math_operator)
            } else {
                binding.edttextOperator.error = null
            }
        })

        binding.viewModel!!.isShowDelayTimeError.observe(this, Observer {
            if (it && lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.edttextDelayTime.error = getString(R.string.required_field)
            } else {
                binding.edttextDelayTime.error = null
            }
        })
    }

    override fun selectOperator() {
        val bottomSheetFragment = BottomSheetStringsFragment()
        var bundle = Bundle()

        bundle.putSerializable(
            "list",
            binding.viewModel!!.methodOperationList
        )
        bundle.putSerializable("title", getString(R.string.select_math_operator))
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.setOnBottomSheetItemClickObserver(object :
            OnBottomSheetItemClickListener {
            override fun onBottomSheetItemClickListener(position: Int) {
                binding.viewModel!!.selectedOperatorPosition = position
                binding.viewModel!!.operator.value =
                    binding.viewModel!!.methodOperationList[position]
                binding.viewModel!!.isShowOperatorError.value = false
            }
        })
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}