package math.question.task.view.activity.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import math.question.task.R
import math.question.task.databinding.ActivityMainBinding
import math.question.task.view.activity.baseActivity.BaseActivity

class MainActivity : BaseActivity(
    R.string.app_name, true, false, true,
    false, false, true, false, true,
), MainViewModel.Observer {

    lateinit var binding: ActivityMainBinding
    override fun doOnCreate(arg0: Bundle?) {
        binding = putContentView(R.layout.activity_main) as ActivityMainBinding
        binding.viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application)
            )
                .get(MainViewModel::class.java)
        binding.viewModel!!.baseViewModelObserver = baseViewModelObserver
        binding.viewModel!!.observer = this
        binding.lifecycleOwner = this
        initializeViews()
        setListener()
    }

    override fun initializeViews() {
    }

    override fun setListener() {
    }
}