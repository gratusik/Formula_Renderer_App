package com.gratus.formularendererapp.util.Interfaces

import com.gratus.formularendererapp.model.common.Formula

interface SearchListListerner {
    fun onItemClick(formula: Formula)
}