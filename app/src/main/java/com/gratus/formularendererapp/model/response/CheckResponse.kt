package com.gratus.formularendererapp.model.response

import okhttp3.Headers

data class CheckResponse(
    var success: Boolean,
    var checkHeader: Headers,
)