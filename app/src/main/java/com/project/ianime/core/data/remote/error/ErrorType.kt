package com.project.ianime.core.data.remote.error

import androidx.annotation.StringRes
import com.project.ianime.R

enum class ErrorType(@StringRes val label: Int, @StringRes val message: Int) {
    UNAUTHORIZED(R.string.generic_error_label, R.string.unauthorized_error_message),
    NOT_FOUND(R.string.generic_error_label, R.string.not_found_error_message),
    CONNECTION(R.string.connection_error_label, R.string.connection_error_message),
    BAD_REQUEST(R.string.generic_error_label, R.string.bad_request_error_message),
    CONTENT_OVERSIZE(R.string.generic_error_label, R.string.content_oversize_error_message),
    GENERIC(R.string.generic_error_label, R.string.generic_error_message);
}