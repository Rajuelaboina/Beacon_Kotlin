package com.phycare.residentbeacon_kotlin.ui.programs

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProgramCompSearch(
    @SerializedName("ProgramID")
     var programID: Long?=0,

    @SerializedName("ProgramName")
     val programName: String?=null,

    @SerializedName("Speciality")
     val speciality: String?=null,

    @SerializedName("Location")
     val location: String?=null,

    @SerializedName("AdminInfo")
     val adminInfo: String?=null,

    @SerializedName("ContactInfo")
     val contactInfo: String?=null,

    @SerializedName("ProgramLink")
     val programLink: String?=null,

    @SerializedName("ResidencyLink")
     val residencyLink: String?=null,

    @SerializedName("LastUpdated")
     val lastUpdated: String?=null,

    @SerializedName("SurveyReceived")
     val surveyReceived: String?=null,

    @SerializedName("LocationInfo")
     val locationInfo: String?=null,

    @SerializedName("Sponsor")
     val sponsor: String?=null,

    @SerializedName("Participant1")
     val participant1: String?=null,

    @SerializedName("Participant2")
     val participant2: String?=null,

    @SerializedName("Participant3")
     val participant3: String?=null,

    @SerializedName("ProgramType")
     val programType: String?=null,

    @SerializedName("ProgramAffiliation")
     val programAffiliation: String?=null,

    @SerializedName("AccreditedYears")
     val accreditedYears: Int?=0,

    @SerializedName("RequiredYears")
     val requiredYears: Int?=0,

    @SerializedName("AcceptingApplications")
     val acceptingApplications: String?=null,

    @SerializedName("AcceptingNextYear")
     val acceptingNextYear: String?=null,

    @SerializedName("StartingDate")
     val startingDate: String?=null,

    @SerializedName("ERASParticipant")
     val eRASParticipant: String?=null,

    @SerializedName("GovernmentAffiliated")
     val governmentAffiliated: String?=null,

    @SerializedName("AdditionalComments")
     val additionalComments: String?=null,

    // State of the item
    var expanded : Boolean = false
) : Serializable {
    constructor(pName: String) : this(null ,pName)
}
