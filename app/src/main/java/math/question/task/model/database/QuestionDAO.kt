package math.question.task.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import math.question.task.model.QuestionModel

@Dao
interface QuestionDAO {
    @Query("SELECT * FROM QuestionModel ORDER BY id DESC")
    fun getQuestionModels(): Flowable<List<QuestionModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg questionModel: QuestionModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionModel(questionModel: QuestionModel): Completable
}