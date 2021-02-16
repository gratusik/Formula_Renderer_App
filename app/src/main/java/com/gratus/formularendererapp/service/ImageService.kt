package com.gratus.formularendererapp.service

import com.gratus.formularendererapp.util.constants.ServiceConstants
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path


interface ImageService {
    @GET(ServiceConstants.IMAGE_URL)
    fun getImage(@Path("imageCache") imageName: String?): Single<ResponseBody>
}