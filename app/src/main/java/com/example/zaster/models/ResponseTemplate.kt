package com.example.zaster.models

import com.google.gson.annotations.SerializedName

data class ResponseTemplate (@SerializedName("token") val token: String,
                     @SerializedName("error ") val error: String)