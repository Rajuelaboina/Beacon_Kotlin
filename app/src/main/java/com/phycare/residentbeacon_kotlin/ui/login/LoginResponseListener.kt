package com.phycare.residentbeacon_kotlin.ui.login

interface LoginResponseListener {

    fun onError(loginCode: Int)
    fun onSuccess(user: UserLogin?)

    fun onRefreshError(i: Int)
}