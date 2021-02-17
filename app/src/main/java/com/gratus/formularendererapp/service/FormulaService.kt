package com.gratus.formularendererapp.service

import androidx.lifecycle.LiveData
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.model.request.CheckRequest
import com.gratus.formularendererapp.model.response.CheckResponse
import com.gratus.formularendererapp.util.constants.ServiceConstants
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FormulaService {
    fun getRecentFormula10(): LiveData<List<Formula>>

    //repo for recent result from room of formulas to show the main page
    fun getSearchFormula(formulaText: String): LiveData<List<Formula>>

    //repo for check formula is there in room db or not and then insert or update accordingly
    fun insertOrUpdate(formula: Formula)
}