package math.question.task.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import math.question.task.model.QuestionModel

class Converters {
    @TypeConverter
    fun areasModelsToJson(value: List<QuestionModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToAreasModels(value: String) = Gson().fromJson(value, Array<QuestionModel>::class.java).toList().toCollection(ArrayList())

    @TypeConverter
    fun areaModelToJson(value: QuestionModel?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToAreaModel(value: String) = Gson().fromJson(value, QuestionModel::class.java)
}