package com.felipeycamila.notbored

import com.google.gson.annotations.SerializedName

data class ActivityModel(
    @SerializedName("activity")
    val activity: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("type")
    val type: String,

    @SerializedName("participants")
    val participants: Int
)
