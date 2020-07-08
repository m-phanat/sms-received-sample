package com.panat.mvvm.retrofit.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestSms(
    @SerializedName("collection")
    var collection: String? = "smsTexts",
    @SerializedName("db")
    var db: String? = "alpha",
    @SerializedName("options")
    var options: Options? = Options(),
    @SerializedName("schema")
    var schema: String? = "smsSchema",
    @SerializedName("index")
    var index: Index = Index(),
    @SerializedName("value")
    var value: Value = Value()
) : Parcelable

@Parcelize
data class Index(
    @SerializedName("code")
    var code: String? = null
) : Parcelable

@Parcelize
data class Options(
    @SerializedName("setDefaultsOnInsert")
    var setDefaultsOnInsert: Boolean? = true,
    @SerializedName("strict")
    var strict: Boolean? = false,
    @SerializedName("upsert")
    var upsert: Boolean? = true
) : Parcelable

@Parcelize
data class Value(
    @SerializedName("\$set")
    var set: Set = Set()
) : Parcelable

@Parcelize
data class Set(
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("text")
    var text: String? = ""
) : Parcelable
