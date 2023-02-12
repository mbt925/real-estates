package com.mbt925.realestate.feature.ui.models

import androidx.annotation.StringRes
import com.mbt925.realestate.feature.ui.R

sealed interface FailureParam {
    val message: Int

    data class NoInternet(
        @StringRes override val message: Int = R.string.error_no_internet
    ) : FailureParam

    data class UnknownError(
        @StringRes override val message: Int = R.string.error_unknown,
        val reason: String,
    ) : FailureParam
}
