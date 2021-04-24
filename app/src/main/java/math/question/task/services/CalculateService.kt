package math.question.task.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import math.question.task.R
import math.question.task.model.QuestionModel
import math.question.task.model.database.AppDatabase
import math.question.task.model.database.QuestionDAO
import math.question.task.util.*

class CalculateService : Service() {

    private val TAG = CalculateService::class.java.simpleName
    var compositeDisposable = CompositeDisposable()
    lateinit var db: AppDatabase
    lateinit var questionDAO: QuestionDAO

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            startForeground(
                5000,
                makeStatusNotification(
                    true,
                    "answering a Question",
                    this
                ),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            startForeground(
                5000,
                makeStatusNotification(
                    true,
                    "answering a Question",
                    this
                )
            )
        }
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "MathQuestionsDB"
        ).build()
        questionDAO = db.questionDao()
    }

    private fun startCalculation(questionModel: QuestionModel) {
        makeStatusNotification(
            false,
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
                            false,
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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        try {
            val bundle = intent!!.extras
            var questionModel = QuestionModel()
            questionModel.firstNumber = bundle?.getString("firstNumber")
            questionModel.secondNumber = bundle?.getString("secondNumber")
            questionModel.operatorText = bundle?.getString("operatorText")
            questionModel.delayTime = bundle?.getString("delayTime")?.toInt() ?: 0
            questionModel.isShowLocation = bundle?.getBoolean("isShowLocation", false)
            if (questionModel.isShowLocation!!) {
                questionModel.latitude = bundle?.getString("latitude")?.toDouble() ?: 0.0
                questionModel.longitude = bundle?.getString("longitude")?.toDouble() ?: 0.0
            }
            startCalculation(questionModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return START_STICKY
    }

}