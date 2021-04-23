package math.question.task.view.fragments.menu

import androidx.lifecycle.MutableLiveData
import math.question.task.MyApplication
import math.question.task.util.Preferences
import math.question.task.view.activity.baseActivity.BaseActivityViewModel
import io.reactivex.disposables.CompositeDisposable

class MenuViewModel(
    application: MyApplication
) : BaseActivityViewModel(application) {
    lateinit var menuObserver: MenuObserver

    var facebookLink = MutableLiveData<String>()
    var youTubeLink = MutableLiveData<String>()
    var instaLink = MutableLiveData<String>()

    var isShowSocialLinks = MutableLiveData<Boolean>()

    var imageURl = MutableLiveData<String>()
    var name = MutableLiveData<String>()

    init {
        isShowSocialLinks.value = false
        imageURl.value = Preferences.getUserImage()
        name.value = Preferences.getUserName()

        facebookLink.value = Preferences.getFacebookUrl()
        youTubeLink.value = Preferences.getYoutubeUrl()
        instaLink.value = Preferences.getInstagramUrl()

    }

    override fun onCleared() {
        super.onCleared()
    }

    interface MenuObserver {
        fun onAskToLogout()
    }

}