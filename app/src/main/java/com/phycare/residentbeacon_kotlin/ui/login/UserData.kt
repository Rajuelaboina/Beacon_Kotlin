package com.phycare.residentbeacon_kotlin.ui.login

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("UserName")
    var userName: String?,
    @SerializedName("UserFullName")
    var userFullName: String?
)
