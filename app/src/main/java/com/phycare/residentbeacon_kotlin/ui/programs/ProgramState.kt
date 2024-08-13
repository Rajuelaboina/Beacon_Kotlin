package com.phycare.residentbeacon_kotlin.ui.programs

import com.google.gson.annotations.SerializedName

data class ProgramState(
    @SerializedName("Location")
    var location: String
)
