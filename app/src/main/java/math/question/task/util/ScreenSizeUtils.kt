package math.question.task.util

import android.content.res.Resources
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity

class ScreenSizeUtils {
    fun getItemWidth(activity: AppCompatActivity, numOfColumn: Double, isPadding: Boolean): Double {
        var columnWidth = 0.0
        val res = getScreenResolution(activity)
        val width = res[0]
        var padding = 0.0
        if (isPadding)
            padding = convertDpToPixel(25f).toDouble()
        columnWidth = (width - padding) / numOfColumn
        return columnWidth
    }

    fun getResolutionDensity(activity: AppCompatActivity): Float {
        return activity.resources.displayMetrics.density
    }

    fun getScreenResolution(activity: AppCompatActivity): IntArray {
        val sizeInpixels = intArrayOf(500, 960)
        return try {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            activity.windowManager.defaultDisplay
            display.getSize(size)
            sizeInpixels[0] = size.x
            sizeInpixels[1] = size.y
            sizeInpixels
        } catch (e: Exception) {
            sizeInpixels
        }
    }

    fun getScreenWidth(activity: AppCompatActivity):Int{
        return try {

            val display = activity.windowManager.defaultDisplay
            val size = Point()
            activity.windowManager.defaultDisplay
            display.getSize(size)
            size.x
        } catch (e: Exception) {
            0
        }
    }

    fun convertPixelsToDp(px: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return Math.round(dp).toFloat()
    }

    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }
}