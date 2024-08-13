package com.phycare.residentbeacon_kotlin.apiservice

import com.phycare.residentbeacon_kotlin.model.Pgy_model
import com.phycare.residentbeacon_kotlin.model.Speciality
import com.phycare.residentbeacon_kotlin.model.States
import com.phycare.residentbeacon_kotlin.ui.login.UserData
import com.phycare.residentbeacon_kotlin.ui.programs.ProgramCompSearch
import com.phycare.residentbeacon_kotlin.ui.programs.ProgramState
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentCompleteSearch
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiRequest {

    @GET("ResidentServices.asmx/ValidateUser?") // login
    fun getLoginUserDetails(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<List<UserData>>

    // ResidentServices
    @GET("ResidentServices.asmx/ResidentSearch?")
    fun getResidentDetails(@Query("searchString") s: String): Call<List<ResidentSearch>>

    @POST("ResidentServices.asmx/StatesMasterData")  //get the location states
    fun getStatesDetails(): Call<List<States>>

    @POST("ResidentServices.asmx/PGYMasterData") //get the  PGY
    fun getPGYDetails(): Call<List<Pgy_model>>

    //Resident complete Search
    @GET("ResidentServices.asmx/ResidentCompleteSearch?")
    fun getResidentCompleteSearch(
        @Query("Resident") resident: String,
        @Query("Locations") location: String,
        @Query("PGY") PGY: String
    ): Call<List<ResidentCompleteSearch>>

    //Speciality of programs
    @GET("ProgramDetailsServices.asmx/ProgramSpecialityMasterData")
    fun getSpecialityDetails(): Call<List<Speciality>>

    @GET("ProgramDetailsServices.asmx/ProgramDetailsSearch?")
    fun getProgramCompSearch(
        @Query("ProgramName") ProgramName: String,
        @Query("Locations") Locations: String,
        @Query("Specialities") Specialities: String,
    ): Call<List<ProgramCompSearch>>

    @GET("ProgramDetailsServices.asmx/ProgramStatesMasterData")
    fun getProgramState(): Call<List<ProgramState>>
}