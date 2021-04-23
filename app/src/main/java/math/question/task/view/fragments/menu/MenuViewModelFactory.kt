package math.question.task.view.fragments.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import math.question.task.MyApplication

class MenuViewModelFactory(
    var application: MyApplication
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuViewModel(application) as T
    }
}