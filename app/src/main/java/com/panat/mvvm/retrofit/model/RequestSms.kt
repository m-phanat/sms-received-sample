package com.panat.mvvm.retrofit.model


import com.google.gson.annotations.SerializedName

data class RequestSms(
    @SerializedName("collection")
    var collection: String? = "smsTexts",
    @SerializedName("db")
    var db: String? = "alpha",
    @SerializedName("options")
    var options: Options? = Options(),
    @SerializedName("schema")
    var schema: String? = "smsSchema",
    @SerializedName("value")
    var value: Value? = Value()
)

data class Options(
    @SerializedName("setDefaultsOnInsert")
    var setDefaultsOnInsert: Boolean? = true,
    @SerializedName("strict")
    var strict: Boolean? = false,
    @SerializedName("upsert")
    var upsert: Boolean? = true
)

data class Value(
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("text")
    var text: String? = ""
)
