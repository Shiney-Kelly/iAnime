package com.project.ianime.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.project.ianime.R
import io.reactivex.rxjava3.core.Observable


class AnimeEditText : TextInputLayout {

    private val text: String
        get() = editText?.text.toString()

    var dropdownOption: Any? = null
        get() {
            return if (field == null) {
                error = resources.getString(R.string.required_warning_msg)
            } else field
        }
        set(value) {
            field = value
            (editText as AutoCompleteTextView).setText(field.toString().trim(), false)
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    fun setInitialText(value: String) {
        if (value.isEmpty()) {
            return
        }
        editText?.setText(value)
    }

    fun setTextChangeListener(): Observable<Boolean> {
        return Observable.create { emitter ->
            editText?.addTextChangedListener {
                emitter.onNext(isValid())
            }
        }
    }

    fun isValid(optional: Boolean = false): Boolean {
        // Add optional choice for edit text
        if (optional && text == "") {
            return true
        }
        return validateIsRequired(text)
    }

    private fun validateIsRequired(input: String): Boolean {
        return if (input.isEmpty()) {
            error = resources.getString(R.string.required_warning_msg)
            false
        } else {
            error = null
            true
        }
    }
}
