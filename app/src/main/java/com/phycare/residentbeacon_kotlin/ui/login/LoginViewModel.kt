package com.phycare.residentbeacon_kotlin.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phycare.residentbeacon_kotlin.apiservice.RetrofitService
import com.phycare.residentbeacon_kotlin.model.Response_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(var responseListener: LoginResponseListener) : ViewModel() {
    val loginUser = UserLogin("","")

    val data: MutableLiveData<Response_Model> = MutableLiveData<Response_Model>()

    fun onClick(view: View?) {
        val loginCode: Int = loginUser.isValidUser()
        if (loginCode == 0) {
            responseListener.onError(loginCode)
        } else if (loginCode == 1) {
            responseListener.onError(loginCode)
        } else if (loginCode == 2) {
            responseListener.onError(loginCode)
        } else if (loginCode == 3) {
            responseListener.onError(loginCode)
        } else {
            responseListener.onSuccess(loginUser)
        }
    }

    fun getEmailWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
              loginUser.email = s.toString().trim()
                responseListener.onRefreshError(-1)
            }
            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun getPasswordWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
               loginUser.password = s.toString().trim()
                responseListener.onRefreshError(-2)
            }
            override fun afterTextChanged(s: Editable) {}
        }
    }

    val loginList: MutableLiveData<List<UserData>?> = MutableLiveData<List<UserData>?>()

    fun getLoginDetails(email: String, password: String) {
        val call: Call<List<UserData>> = RetrofitService.getRetrofitInstance().getLoginUserDetails(email, password)
        call.enqueue(object : Callback<List<UserData>?> {
            override fun onResponse(
                call: Call<List<UserData>?>,
                response: Response<List<UserData>?>,
            ) {
                if (response.isSuccessful) {
                    loginList.postValue(response.body())
                    Log.e("LoginViewModel", "Response>>>>>: " + response.message())
                    Log.e("LoginViewModel", "Response>>>>>: " + response.code())
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                } else {
                    data.postValue(Response_Model(false, response.code(), response.message()))
                }
            }
            override fun onFailure(call: Call<List<UserData>?>, t: Throwable) {
                loginList.postValue(null)
                Log.e("LoginViewModel", "Response>>>>>: " + t.message)
            }
        })
    }

    fun getUserLogin(): MutableLiveData<List<UserData>?>? {
        return loginList
    }

}