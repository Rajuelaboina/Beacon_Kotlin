package com.phycare.residentbeacon_kotlin.ui.residents

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResidentSearch (
    @SerializedName("Provider_Name")
    private var providerName: String,
    @SerializedName("Photo")
    private var photo: String): Serializable