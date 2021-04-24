package math.question.task.view.activity.addNewTask

import android.location.Location
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import math.question.task.MyApplication
import math.question.task.R
import math.question.task.model.QuestionModel
import math.question.task.util.AddOperator
import math.question.task.util.DivideOperator
import math.question.task.util.MultiplyOperator
import math.question.task.util.SubOperator
import math.question.task.view.activity.baseActivity.BaseActivityViewModel

class AddNewTaskViewModel(
    application: MyApplication
) : BaseActivityViewModel(application) {
    lateinit var observer: Observer
    var firstNumber = MutableLiveData<String>()
    var secondNumber = MutableLiveData<String>()
    var delayTime = MutableLiveData<String>()
    var isShowFirstNumberError = MutableLiveData<Boolean>()
    var isShowSecondNumberError = MutableLiveData<Boolean>()
    var isShowDelayTimeError = MutableLiveData<Boolean>()

    var methodOperationList: ArrayList<String> = ArrayList()
    var isShowOperatorError = MutableLiveData<Boolean>()
    var operator = MutableLiveData<String>()
    var selectedOperatorPosition = -1

    var isGetMyLocation = MutableLiveData<Boolean>()
    var mLastKnownLocation: Location? = null
    var latitude = 0.0
    var longitude = 0.0

    init {
        isGetMyLocation.value = false
        firstNumber.value = ""
        secondNumber.value = ""
        delayTime.value = ""
        operator.value = ""
        isShowFirstNumberError.value = false
        isShowSecondNumberError.value = false
        isShowDelayTimeError.value = false

        initMethodOperationList()
    }

    val firstNumberTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            isShowFirstNumberError.value =
                firstNumber.value!!.isEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    val secondNumberTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            isShowSecondNumberError.value =
                secondNumber.value!!.isEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    val delayTimeTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            isShowDelayTimeError.value =
                delayTime.value!!.isEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    fun initMethodOperationList() {
        methodOperationList = ArrayList()
        methodOperationList.add(AddOperator)
        methodOperationList.add(SubOperator)
        methodOperationList.add(MultiplyOperator)
        methodOperationList.add(DivideOperator)
    }

    fun onButtonCalculateClicked() {
        if (firstNumber.value?.isEmpty()!!) {
            isShowFirstNumberError.value = true
        } else if (secondNumber.value?.isEmpty()!!) {
            isShowSecondNumberError.value = true
        } else if (selectedOperatorPosition == -1) {
            isShowOperatorError.value = true
        } else if (selectedOperatorPosition == 3 && secondNumber.value?.toInt() == 0) {
            isShowSecondNumberError.value = true
        } else if (delayTime.value?.isEmpty()!!) {
            isShowDelayTimeError.value = true
        } else if (isGetMyLocation.value!! && latitude == 0.0) {
            observer.onShowHideMessageDialog(
                application.context.getString(R.string.location_required),
            context.getString(R.string.please_open_location_or_wait_to_get_your_location),
                true
            )
        } else {
            var questionModel = QuestionModel()
            questionModel.firstNumber = firstNumber.value
            questionModel.secondNumber = secondNumber.value
            questionModel.operatorText = operator.value
            questionModel.delayTime = delayTime.value?.toInt()!!
            observer.setQuestionAlarm(questionModel)
        }
    }


    interface Observer {
        fun onShowHideMessageDialog(title: String, message: String, isShow: Boolean)
        fun selectOperator()
        fun setQuestionAlarm(questionModel: QuestionModel)
    }

}