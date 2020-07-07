package com.panat.mvvm.retrofit.model

import com.google.gson.annotations.SerializedName


data class SendSmsResult(
    @SerializedName("n")
    var n: Int? = 0,
    @SerializedName("nModified")
    var nModified: Int? = 0,
    @SerializedName("ok")
    var ok: Int? = 0
)