package com.gratus.formularendererapp.service

import com.gratus.formularendererapp.model.request.CheckRequest
import com.gratus.formularendererapp.model.response.CheckResponse
import com.gratus.formularendererapp.util.constants.ServiceConstants
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckService {
    @POST(ServiceConstants.CHECK_URL)
    fun checkFormula(@Body checkRequest: CheckRequest): Single<Response<CheckResponse>>
}