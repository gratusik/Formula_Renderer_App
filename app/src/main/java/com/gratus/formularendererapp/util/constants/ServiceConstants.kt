package com.gratus.formularendererapp.util.constants

interface ServiceConstants {
    companion object {
        const val BASE_URL = "https://en.wikipedia.org/"
        const val CHECK_URL = "api/rest_v1/media/math/check/tex"
        const val IMAGE_URL = "api/rest_v1/media/math/render/png/{imageCache}"
        const val IMAGE_URL_GLIDE = "api/rest_v1/media/math/render/png/"
        const val SEARCH_URL = "w/api.php"
    }
}