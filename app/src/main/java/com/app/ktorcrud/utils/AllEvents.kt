package com.app.ktorcrud.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Created by Priyanka.
 */
sealed class AllEvents {
    data class Success<T>(val data: T) : AllEvents()
    class Loading(val loading: Boolean) : AllEvents()
    object Nothing : AllEvents()
    data class DynamicError(val error: String) : AllEvents()
    class StringResource(@StringRes val resId: Int, vararg val args: Any?) : AllEvents()

    @Composable
    fun asString(): Any {
        return when (this) {
            is StringResource -> {
                stringResource(resId, args)
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
