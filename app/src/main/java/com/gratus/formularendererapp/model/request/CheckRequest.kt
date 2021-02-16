package com.gratus.formularendererapp.model.request

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.gratus.formularendererapp.R
import javax.inject.Inject

class CheckRequest : BaseObservable {
    @SerializedName("q")
    var formula: String? = null
    var formulaError: Int = R.string.formula_none
    var formulaChange = false

    @Inject
    constructor()
    constructor(formula: String?) : super() {
        this.formula = formula
    }

    fun isFormulaValid(): Boolean {
        return if (formula != null) {
            val formualPattern: String =
                "[a-zA-Z0-9\\+\\-\\*/\\(\\)\\^\\{\\}\\[\\]\\t\\.\\,\\;\\?\\:\\!\\=\\%\\$\\>\\<\\~\\/\\|\\\\begin\\\\begin{\\_\\ ]*"
            if (formula!! matches (formualPattern.toRegex()) && formula!!.length >= 1) {
                formulaChange = false
                formulaError = R.string.formula_none
                notifyChange()
                true
            } else {
                formulaChange = true
                formulaError = R.string.formula_error
                notifyChange()
                false
            }
        } else {
            formulaChange = true
            formulaError = R.string.formula_error
            notifyChange()
            false
        }
    }

    @Bindable
    fun getFormulaTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Do nothing.
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable) {
                formula = s.toString()
                isFormulaValid()
            }
        }
    }
}