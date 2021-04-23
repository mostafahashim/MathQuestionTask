package math.question.task.view.activity.baseActivity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import math.question.task.MyApplication
import math.question.task.R
import math.question.task.util.Preferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivityViewModel(
    var application: MyApplication
) : AndroidViewModel(application) {
    lateinit var baseViewModelObserver: BaseViewModelObserver
    var keyWord = MutableLiveData<String>()

    var baseCompositeDisposable = CompositeDisposable()

    init {
        keyWord.value = ""
    }

    fun onButtonBackClicked() {
        baseViewModelObserver.onBackButtonClicked()
    }

    fun onButtonMenuClicked() {
        baseViewModelObserver.onMenuButtonClicked()
    }

    fun onButtonAny1Clicked() {
        baseViewModelObserver.onAny1ButtonClicked()
    }

    fun onButtonAny2Clicked() {
        baseViewModelObserver.onAny2ButtonClicked()
    }

    fun onButtonSearchClicked() {
        baseViewModelObserver.onSearchClicked()
    }

    fun onLoginAgain() {
        baseViewModelObserver.onLoginAgain()
    }

    fun openLoginToUseFeature(){
        baseViewModelObserver.openLoginToUseFeature()
    }

    fun clearAppPreferencesAndDB() {
        baseCompositeDisposable.add(Observable.fromCallable {
            Preferences.clearUserData()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }
            .subscribe(
                {
                    baseViewModelObserver.onRestartApp(R.string.logout_successfully_completed)
                },
                { }
            ))
    }

    interface BaseViewModelObserver {
        fun onBackButtonClicked()
        fun onMenuButtonClicked()
        fun onAny1ButtonClicked()
        fun onAny2ButtonClicked()
        fun onSearchClicked()
        fun onLoginAgain()
        fun onRestartApp(message: Int)
        fun openLoginToUseFeature()
    }
}