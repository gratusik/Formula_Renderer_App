package com.gratus.formularendererapp.repository

import com.gratus.formularendererapp.service.ImageService
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageRepo @Inject constructor(private var imageService: ImageService) {
    fun getImage(imageName: String?): Single<ResponseBody> {
        return imageService.getImage(imageName)
    }
}