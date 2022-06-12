package com.skinconnect.userapps.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.skinconnect.userapps.R

class EmailEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttribute: Int) : super(
        context,
        attributeSet,
        defStyleAttribute
    ) {
        init()
    }

    /**
     * Validation real-time on editText
     * is invalid show error, else hide error
     * */
    private fun init() {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = if (isEmailValid(s.toString())) {
                    null
                } else {
                    resources.getString(R.string.input_error_email)
                }
            }
        })
    }

    /**
     * Validation email address client
     * */
    private fun isEmailValid(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}