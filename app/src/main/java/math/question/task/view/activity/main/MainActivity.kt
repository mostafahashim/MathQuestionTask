package math.question.task.view.activity.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import math.question.task.R
import math.question.task.databinding.ActivityMainBinding
import math.question.task.util.LOCAL_PRODCAST_RECIEVER_UpdateQuestions
import math.question.task.view.activity.baseActivity.BaseActivity

class MainActivity : BaseActivity(
    R.string.app_name, true, false, true,
    false, false, false, true, true,
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        binding.viewModel?.getLocalQuestionModels()
    }

    var updateQuestionsBroadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            binding.viewModel?.getLocalQuestionModels()
        }
    }

    fun registerReceivers() {
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(
                updateQuestionsBroadCastReceiver,
                IntentFilter(
                    LOCAL_PRODCAST_RECIEVER_UpdateQuestions
                )
            )
    }

    fun unRegisterReceivers() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
            updateQuestionsBroadCastReceiver
        )
    }

    override fun onResume() {
        super.onResume()
        registerReceivers()
    }

    override fun onPause() {
        super.onPause()
        unRegisterReceivers()
    }

}