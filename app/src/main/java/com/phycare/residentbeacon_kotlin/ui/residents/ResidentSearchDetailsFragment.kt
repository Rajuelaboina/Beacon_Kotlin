package com.phycare.residentbeacon_kotlin.ui.residents

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.databinding.FragmentResidentSearchDetailsBinding
import com.phycare.residentbeacon_kotlin.utils.Utility

class ResidentSearchDetailsFragment : Fragment() {
   private lateinit var binding: FragmentResidentSearchDetailsBinding
   private lateinit var residentsViewModel: ResidentsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        // Inflate the layout for this fragment
        this.residentsViewModel = ViewModelProvider(this)[ResidentsViewModel::class.java]
        binding = FragmentResidentSearchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  ResidentSearch residentSearch = (ResidentSearch) getArguments().getSerializable("SEARCH");
        //  ResidentSearch residentSearch = (ResidentSearch) getArguments().getSerializable("SEARCH");

             val residentSearch = (arguments?.getSerializable("SEARCH")) as ResidentCompleteSearch
           //  val name = arguments?.getString("NAME")
             Log.e("Details of search","Details 222222"+residentSearch)




        // Log.e("Details of search","Details"+residentSearch.getResidentName());
        binding.textViewUserName.setText(residentSearch.residentName?.trim()?.replace("\n", " "))
        if (residentSearch.phoneNo != null && residentSearch.phoneNo.isNotEmpty()) {
            binding.textViewUserPhone.text = "Phone : " + residentSearch.phoneNo
            //  binding.textViewUserEmail.setText("Email : "+residentSearch.getMailID());
        } else {
            binding.textViewUserPhone.visibility = View.GONE
            //   binding.textViewUserEmail.setVisibility(View.GONE);
        }
        if (residentSearch.mailID != null && residentSearch.mailID.isNotEmpty()) {
            //  binding.textViewUserPhone.setText("Phone : "+residentSearch.getPhoneNo());
            binding.textViewUserEmail.text = "Email : " + residentSearch.mailID
        } else {
            //  binding.textViewUserPhone.setVisibility(View.GONE);
            binding.textViewUserEmail.visibility = View.GONE
        }
        binding.textViewPName.setText(residentSearch.programName)
        if (residentSearch.location != null && !residentSearch.location.isEmpty()) {
            binding.textViewLocation.setText(residentSearch.location)
        } else {
            binding.viewLocaion.visibility = View.GONE
            binding.linearLayoutLocation.visibility = View.GONE
        }
        if (residentSearch.pgy != null && residentSearch.pgy.isNotEmpty()) {
            binding.textViewPgy.setText(residentSearch.pgy)
        } else {
            binding.viewPgy.visibility = View.GONE
            binding.linearLayoutPgy.visibility = View.GONE
        }


        if (residentSearch.classOf != null && residentSearch.classOf.isNotEmpty()) {
            binding.textViewClass.setText(residentSearch.classOf)
        } else {
            binding.viewClassOf.visibility = View.GONE
            binding.linearLayoutClassOf.visibility = View.GONE
        }

        if (residentSearch.underGraduateCollege != null && residentSearch.underGraduateCollege.isNotEmpty()) {
            binding.textViewUndergrad.setText(residentSearch.underGraduateCollege)
        } else {
            binding.viewUndergrad.visibility = View.GONE
            binding.linearLayoutUndergrad.visibility = View.GONE
        }

        if (residentSearch.medicalSchool != null && residentSearch.medicalSchool.isNotEmpty()) {
            binding.textViewMedicalSchool.setText(residentSearch.medicalSchool)
        } else {
            binding.viewMSchool.visibility = View.GONE
            binding.linearLayoutMSchool.visibility = View.GONE
        }

        if (residentSearch.homeTown != null && residentSearch.homeTown.isNotEmpty()) {
            binding.textViewHomeTown.setText(residentSearch.homeTown)
        } else {
            binding.viewHomeTown.visibility = View.GONE
            binding.linearLayoutHomeTown.visibility = View.GONE
        }
        if (residentSearch.speciality != null && residentSearch.speciality.isNotEmpty()) {
            binding.textViewspeciality.setText(residentSearch.speciality)
        } else {
            binding.viewSpeciality.visibility = View.GONE
            binding.linearLayoutspeciality.visibility = View.GONE
        }
        if (residentSearch.location1 != null && !residentSearch.location1.isEmpty()) {
            binding.textViewLocation1.setText(residentSearch.location1)
        } else {
            binding.viewLocaion1.visibility = View.GONE
            binding.linearLayoutLocation1.visibility = View.GONE
        }


        val _imgUrl: String = Utility.ImageURL + residentSearch.photo

        Glide.with(requireContext())
            .load(_imgUrl) // image url
            .placeholder(R.drawable.doctor) // any placeholder to load at start
            // .error(R.drawable.imagenotfound)  // any image in case of error
            //.override(200, 200) // resizing
            .centerCrop() // .circleCrop()
            .into(binding.imageViewResident)
    }
}