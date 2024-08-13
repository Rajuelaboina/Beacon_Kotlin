package com.phycare.residentbeacon_kotlin.ui.programs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phycare.residentbeacon_kotlin.apiservice.RetrofitService
import com.phycare.residentbeacon_kotlin.model.Response_Model
import com.phycare.residentbeacon_kotlin.model.Speciality
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramsViewModel : ViewModel() {
    // private final MutableLiveData<List<Speciality>> specialityList = new MutableLiveData<>();

    var data: MutableLiveData<Response_Model> = MutableLiveData<Response_Model>()



    var specialityList: MutableList<Speciality> = ArrayList<Speciality>()
    fun getSpecialityDetails() {
        specialityList.clear()
       specialityList!!.add(Speciality("All"))
        val call: Call<List<Speciality>> = RetrofitService.getRetrofitInstance().getSpecialityDetails()
        call.enqueue(object : Callback<List<Speciality>?> {
            override fun onResponse(
                call: Call<List<Speciality>?>,
                response: Response<List<Speciality>?>,
            ) {
                if (response.isSuccessful) {
                    specialityList!!.addAll(response.body()!!)
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<Speciality>?>, t: Throwable) {
                Log.e("Error", "Response Error: " + t.message)
            }
        })
    }

    var compList = MutableLiveData<List<ProgramCompSearch>>()
    fun getSpecialityCompleteSearch(searchString: String,locationString: String,specialityString: String,) {
        val call: Call<List<ProgramCompSearch>> =
            RetrofitService.getRetrofitInstance().getProgramCompSearch(searchString, locationString, specialityString)
        call.enqueue(object : Callback<List<ProgramCompSearch>> {
            override fun onResponse(
                call: Call<List<ProgramCompSearch>>,
                response: Response<List<ProgramCompSearch>>,
            ) {
                Log.e("Error", "Response Error: " + response.body()!!.size)
                if (response.isSuccessful) {
                    compList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ProgramCompSearch>>, t: Throwable) {
                //compList.postValue("")
                Log.e("Error", "Response Error: " + t.message)
            }
        })
    }

    fun getProgramCompSearchObservers(): LiveData<List<ProgramCompSearch>> {
        return compList
    }

    // add User program
     var addUserComList :  MutableList<ProgramCompSearch> = ArrayList<ProgramCompSearch>()
    fun getAddUserSpecialityCompleteSearch(searchString: String,locationString: String,specialityString: String,) {
        addUserComList.clear()
        addUserComList!!.add(ProgramCompSearch("Select Program"))
        val call: Call<List<ProgramCompSearch>> =
            RetrofitService.getRetrofitInstance().getProgramCompSearch(searchString, locationString, specialityString)
        call.enqueue(object : Callback<List<ProgramCompSearch>> {
            override fun onResponse(
                call: Call<List<ProgramCompSearch>>,
                response: Response<List<ProgramCompSearch>>,
            ) {
                Log.e("Error", "Response Error: " + response.body()!!.size)
                if (response.isSuccessful) {
                    addUserComList.addAll(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<ProgramCompSearch>>, t: Throwable) {
                //compList.postValue("")
                Log.e("Error", "Response Error: " + t.message)
            }
        })
    }

    var programStateList: MutableList<ProgramState> = ArrayList()
    fun getAllStates() {
        programStateList.clear()
        programStateList.add(ProgramState("All"))
        val call: Call<List<ProgramState>> = RetrofitService.getRetrofitInstance().getProgramState()
        call.enqueue(object : Callback<List<ProgramState>?> {
            override fun onResponse(
                call: Call<List<ProgramState>?>,
                response: Response<List<ProgramState>?>,
            ) {
                if (response.isSuccessful) {
                    // stateList.postValue(response.body());
                    programStateList.addAll(response.body()!!)
                    data.postValue(
                        Response_Model(
                            response.isSuccessful,
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<ProgramState>?>, t: Throwable) {
                //stateList.postValue(null);
                Log.e("Error", "Response Error: " + t.message)
            }
        })
    }


}