package com.phycare.residentbeacon_kotlin.ui.residents

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResidentCompleteSearch(
    @SerializedName("ID")
     var id: Int?=0,

    @SerializedName("ProgramID")
     val programID: Long?=0,

    @SerializedName("Location")
     val location: String?=null,

    @SerializedName("Provider_Name")
     val residentName: String?=null,

    @SerializedName("PGY")
     val pgy: String?=null,

    @SerializedName("ClassOf")
     val classOf: String?=null,

    @SerializedName("UnderGraduateCollege")
     val underGraduateCollege: String?=null,

    @SerializedName("MedicalSchool")
     val medicalSchool: String?=null,

    @SerializedName("Internship")
     val internship: String?=null,

    @SerializedName("Major")
     val major: String?=null,

    @SerializedName("Fellowship")
     val fellowship: String?=null,

    @SerializedName("HomeTown")
     val homeTown: String?=null,

    @SerializedName("MailID")
     val mailID: String?=null,

    @SerializedName("PhoneNo")
     val phoneNo: String?=null,

    @SerializedName("Misc")
     val mis: String?=null,

    @SerializedName("Photo")
     val photo: String?=null,

    @SerializedName("FileName")
     val fileName: String?=null,

    @SerializedName("TimeStamp")
     val timeStamp: String?=null,

    @SerializedName("ProgramName")
     val programName: String?=null,

    @SerializedName("Speciality")
     val speciality: String?=null,

    @SerializedName("ProgramLocation")
    val location1: String? = null
) : Serializable
