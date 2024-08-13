package com.phycare.residentbeacon_kotlin.model

import com.google.gson.annotations.SerializedName

data class States(
    @SerializedName("Location")
    var location: String
)
