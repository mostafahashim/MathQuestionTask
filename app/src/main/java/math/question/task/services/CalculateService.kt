package math.question.task.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import math.question.task.model.QuestionModel
import math.question.task.model.database.AppDatabase
import math.question.task.model.database.QuestionDAO
import math.question.task.util.*


class CalculateService : JobIntentService() {

    private val TAG = CalculateService::class.java.simpleName
    var compositeDisposable = CompositeDisposable()
    lateinit var db: AppDatabase
    lateinit var questionDAO: QuestionDAO

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "MathQuestionsDB"
        ).build()
        questionDAO = db.questionDao()
    }

    /*override fun onHandleIntent(intent: Intent?) {
        try {
            var questionModel = intent?.extras?.get("QuestionModel") as QuestionModel?
            startForeground(questionModel!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    private fun startForeground(questionModel: QuestionModel) {
        makeStatusNotification(
            "calculating ${questionModel.firstNumber} ${questionModel.operatorText} ${questionModel.secondNumber}",
            this
        )

        when (questionModel.operatorText) {
            AddOperator -> {
                questionModel.operator = "+"
                questionModel.answer =
                    (questionModel.firstNumber?.toInt()!! + questionModel.secondNumber?.toInt()!!).toString()
            }
            SubOperator -> {
                questionModel.operator = "-"
                questionModel.answer =
                    (questionModel.firstNumber?.toInt()!! - questionModel.secondNumber?.toInt()!!).toString()
            }
            MultiplyOperator -> {
                questionModel.operator = "*"
                questionModel.answer =
                    (questionModel.firstNumber?.toInt()!! * questionModel.secondNumber?.toInt()!!).toString()
            }
            DivideOperator -> {
                questionModel.operator = "/"
                questionModel.answer =
                    (questionModel.firstNumber?.toInt()!! / questionModel.secondNumber?.toInt()!!).toString()
            }
        }

        saveQuestionInDB(questionModel)
    }

    fun saveQuestionInDB(questionModel: QuestionModel) {
        compositeDisposable.add(questionDAO.insertQuestionModel(questionModel)
            .doOnError {
                Log.i("DBError", it?.message!!)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Handler(Looper.getMainLooper()).postDelayed({
                        var requestBroadcastRequestAnswered = Intent(
                            LOCAL_PRODCAST_RECIEVER_UpdateQuestions
                        )
                        LocalBroadcastManager.getInstance(application)
                            .sendBroadcast(requestBroadcastRequestAnswered)
                        makeStatusNotification(
                            "${questionModel.firstNumber} ${questionModel.operator} ${questionModel.secondNumber} = ${questionModel.secondNumber}",
                            this
                        )
                        stopSelf()
                    }, 1000)
                },
                { }
            ))

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onHandleWork(intent: Intent) {
        try {
            val bundle = intent!!.extras
            var questionModel = QuestionModel()
            questionModel.firstNumber = bundle?.getString("firstNumber")
            questionModel.secondNumber = bundle?.getString("secondNumber")
            questionModel.operatorText = bundle?.getString("operatorText")
            questionModel.delayTime = bundle?.getString("delayTime")?.toInt()!!
            startForeground(questionModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId);
        try {
            val bundle = intent!!.extras
            var questionModel = QuestionModel()
            questionModel.firstNumber = bundle?.getString("firstNumber")
            questionModel.secondNumber = bundle?.getString("secondNumber")
            questionModel.operatorText = bundle?.getString("operatorText")
            questionModel.delayTime = bundle?.getString("delayTime")?.toInt()!!
            startForeground(questionModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Service.START_STICKY
    }

}