package com.gratus.formularendererapp.repository

import com.gratus.formularendererapp.model.request.CheckRequest
import com.gratus.formularendererapp.model.response.CheckResponse
import com.gratus.formularendererapp.service.CheckService
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CheckRepo @Inject constructor(private var checkService: CheckService) {
    fun checkFormulaResponse(checkRequest: CheckRequest): Single<Response<CheckResponse>> {
        return checkService.checkFormula(checkRequest)
    }
}