package com.gratus.formularendererapp.util

import com.gratus.formularendererapp.R

object FormulaValid {
    fun isFormulaValid(formula: String): Boolean {
        val formualPattern: String =
            "[a-zA-Z0-9\\+\\-\\*/\\(\\)\\^\\{\\}\\[\\]\\t\\.\\,\\;\\?\\:\\!\\=\\%\\$\\>\\<\\~\\/\\|\\\\begin\\\\begin{\\_\\ ]*"
        return if (formula!! matches (formualPattern.toRegex()) && formula!!.length >= 1) {
            true
        } else {
            false
        }
    }
}