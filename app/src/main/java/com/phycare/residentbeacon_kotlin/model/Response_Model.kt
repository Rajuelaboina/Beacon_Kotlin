package com.phycare.residentbeacon_kotlin.model

data class Response_Model(
    var isSuccessful: Boolean,
    var code: Int = 0,
    var message: String
)
