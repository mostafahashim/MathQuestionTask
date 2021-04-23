package math.question.task.view.activity.addNewTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import math.question.task.MyApplication

class AddNewTaskViewModelFactory(
    var application: MyApplication
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewTaskViewModel(application) as T
    }
}