package com.panat.mvvm.retrofit.model


import com.google.gson.annotations.SerializedName


typealias smsList = ArrayList<SmsItems>

data class SmsItems(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("datetime")
    var datetime: String? = "",
    @SerializedName("_id")
    var id: String? = "",
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("sender")
    var sender: String? = "",
    @SerializedName("text")
    var text: String? = ""
)
