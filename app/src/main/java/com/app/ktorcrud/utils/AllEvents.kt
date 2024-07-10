package com.app.ktorcrud.utils

import android.app.Activity
import androidx.annotation.StringRes

/**
 * Created by Priyanka.
 */
sealed class AllEvents {
    data class SuccessBool(val message: Boolean, val code: Int) : AllEvents()
    data class Success<T>(val data: T) : AllEvents()
    object Loading: AllEvents()
    data class Progress(val progress: Int) : AllEvents()
    data class DynamicError(val error: String) : AllEvents()
    class StringResource(@StringRes val resId: Int, vararg val args: Any?) : AllEvents()

    fun asString(context: Activity): Any {
        return when (this) {
            is StringResource -> {
                context.getString(resId, args)
            }
            is DynamicError -> {
                error
            }
            else -> {
                DialogUtils.hideProgressBar()
            }
        }
    }
}
