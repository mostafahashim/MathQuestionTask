package math.question.task.view.fragments.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import math.question.task.R
import math.question.task.databinding.FragmentMenuBinding
import math.question.task.observer.OnAskUserAction
import math.question.task.util.showMessage
import math.question.task.view.activity.baseActivity.BaseActivity

class MenuFragment : Fragment(), MenuViewModel.MenuObserver {
    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_menu, container, false)
        binding.viewModel = ViewModelProvider(
            this,
            MenuViewModelFactory(activity.application)
        )
            .get(MenuViewModel::class.java)
        binding.viewModel!!.baseViewModelObserver = activity.baseViewModelObserver
        binding.viewModel!!.menuObserver = this

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            activity = context
        }
    }

    lateinit var activity: BaseActivity


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity() as BaseActivity

        initializeView()
        setListener()
    }

    fun initializeView() {
    }

    fun setListener() {
    }

    override fun onAskToLogout() {
        activity.closeMenu()
        showMessage(
            activity,
            getString(R.string.log_out),
            getString(R.string.are_you_sure_you_want_to_logout_and_clear_all_data_),
            object : OnAskUserAction {
                override fun onPositiveAction() {
                    binding.viewModel!!.clearAppPreferencesAndDB()
                }

                override fun onNegativeAction() {
                }

            },
            true,
            getString(R.string.cancel),
            getString(R.string.ok),
            true
        )
    }
}