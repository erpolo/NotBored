package com.felipeycamila.notbored.model

import com.google.gson.annotations.SerializedName

data class ActivityModel(
    var activity: String,

    var price: Double,

    var type: String,

    var participants: Int,

    var error: String?
)
