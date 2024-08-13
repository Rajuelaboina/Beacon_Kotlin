package com.phycare.residentbeacon_kotlin.ui.residents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phycare.residentbeacon_kotlin.apiservice.RetrofitService
import com.phycare.residentbeacon_kotlin.model.Pgy_model
import com.phycare.residentbeacon_kotlin.model.Response_Model
import com.phycare.residentbeacon_kotlin.model.States
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResidentsViewModel : ViewModel() {
    var data: MutableLiveData<Response_Model> = MutableLiveData<Response_Model>()
    var residentsList: MutableLiveData<List<ResidentSearch>?> = MutableLiveData()


    fun getResidentSearchDetails(str: String) {
        val call: Call<List<ResidentSearch>> = RetrofitService.getRetrofitInstance().getResidentDetails(str)
        call.enqueue(object : Callback<List<ResidentSearch>> {
            override fun onResponse(
                call: Call<List<ResidentSearch>>,
                response: Response<List<ResidentSearch>>,
            ) {
                if (response.isSuccessful) {
                    residentsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ResidentSearch>>, t: Throwable) {
                residentsList.postValue(null)
                Log.e("Error", "Response Error: " + t.message)
            }
        })
    }
    fun getResidentsSearchObserver(): MutableLiveData<List<ResidentSearch>?> {
        return residentsList
    }

    var stateList: ArrayList<States> = ArrayList()
    fun getAllStates() {
        stateList.add(States("All"))
        CoroutineScope(IO).launch {
        val call: Call<List<States>> = RetrofitService.getRetrofitInstance().getStatesDetails()
        call.enqueue(object : Callback<List<States>> {
            override fun onResponse(call: Call<List<States>>, response: Response<List<States>>) {
                if (response.isSuccessful) {
                    // stateList.postValue(response.body());
                    stateList.addAll(response.body()!!)
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                } else {
                    Log.e("Error", "Response Error: " + response.message())
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<States>?>, t: Throwable) {
                //stateList.postValue(null);
                Log.e("Error", "Response Error: " + t.message)
                // data.postValue(false);
            }
         })
        }
    }


    var pgyList: MutableList<Pgy_model> = ArrayList<Pgy_model>()

    fun getAllPgy() {
        pgyList.add(Pgy_model("All"))
        val call: Call<List<Pgy_model>> = RetrofitService.getRetrofitInstance().getPGYDetails()
        call.enqueue(object : Callback<List<Pgy_model>?> {
            override fun onResponse(
                call: Call<List<Pgy_model>?>,
                response: Response<List<Pgy_model>?>,
            ) {
                if (response.isSuccessful) {
                    // pgyList.postValue(response.body());
                    pgyList!!.addAll(response.body()!!)
                    //  data.postValue(true);
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                } else {
                    Log.e("Error", "Response Error: " + response.message())
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<Pgy_model>?>, t: Throwable) {
                //  pgyList.postValue(null);
                Log.e("Error", "Response Error: " + t.message)
                //data.postValue(false);
            }
        })
    }

    // ------------- get the multiple all complete Search -------------------          ///
    var compList = MutableLiveData<List<ResidentCompleteSearch>>()
    fun getCompleteSearch(resident: String, location: String, pgy: String) {
        val call: Call<List<ResidentCompleteSearch>> =
            RetrofitService.getRetrofitInstance().getResidentCompleteSearch(resident, location, pgy)
        call.enqueue(object : Callback<List<ResidentCompleteSearch>> {
            override fun onResponse(
                call: Call<List<ResidentCompleteSearch>>,
                response: Response<List<ResidentCompleteSearch>>,
            ) {
                if (response.isSuccessful) {
                    compList.postValue(response.body())
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                } else {
                    Log.e("Error", "Response Error: " + response.message())
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<ResidentCompleteSearch>>, t: Throwable) {
                Log.e("TODO", "Resident Complete Search: " + t.message)
            }
        })
    }

    fun getCompleteSearchDataObserver(): LiveData<List<ResidentCompleteSearch>>? {
        return compList
    }
}