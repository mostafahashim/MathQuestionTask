package math.question.task.view.activity.main

import math.question.task.MyApplication
import math.question.task.view.activity.baseActivity.BaseActivityViewModel

class MainViewModel(
    application: MyApplication
) : BaseActivityViewModel(application) {
    lateinit var observer: Observer

    init {
    }

    override fun onCleared() {
        super.onCleared()
    }

    interface Observer {
    }

}