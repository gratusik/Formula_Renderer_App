package com.gratus.formularendererapp.viewModel.adapter


import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.util.Interfaces.SearchListListerner
import javax.inject.Inject

class SearchItemViewModel @Inject constructor(
    private var formula: Formula,
    private var mListener: SearchListListerner
) {
    fun getFormula(): Formula {
        return formula
    }

    fun onItemClick() {
        mListener.onItemClick(formula)
    }
}
