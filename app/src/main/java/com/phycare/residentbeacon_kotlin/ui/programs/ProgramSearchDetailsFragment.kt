package com.phycare.residentbeacon_kotlin.ui.programs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.phycare.residentbeacon_kotlin.databinding.FragmentProgramSearchDetailsBinding
import java.lang.String
import kotlin.Exception
import kotlin.toString

class ProgramSearchDetailsFragment: Fragment() {
    lateinit var binding: FragmentProgramSearchDetailsBinding
    private lateinit var programsViewModel: ProgramsViewModel
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        programsViewModel = ViewModelProvider(this)[ProgramsViewModel::class.java]
        binding = FragmentProgramSearchDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pro = (requireArguments().getSerializable("DATA")) as ProgramCompSearch

         // Log.e("DDDDDDDDDDD","proSearch<> "+ programSearch.programName.toString())

        binding.textView3.setText(pro.programName)
        binding.textView5.setText(pro.speciality)
        binding.textView7.setText(pro.location)
        binding.textView9.setText(pro.adminInfo)
        binding.textView11.setText(pro.contactInfo)
        binding.textView13.setText(pro.programLink)
        binding.textView15.setText(pro.residencyLink)
        binding.textView17.setText(pro.lastUpdated)
        binding.textView19.setText(pro.surveyReceived)
        binding.textView21.setText(pro.locationInfo)
        binding.textView23.setText(pro.sponsor)
        binding.textView25.setText(pro.participant1)
        binding.textView27.setText(pro.participant2)
        binding.textView29.setText(pro.participant3)
        binding.textView31.setText(pro.programType)
        binding.textView33.setText(pro.programAffiliation)
        binding.textView35.setText(String.valueOf(pro.acceptingNextYear))
        binding.textView37.setText(pro.acceptingApplications)
        binding.textView39.setText(pro.acceptingNextYear)
        binding.textView41.setText(pro.startingDate)
        binding.textView43.setText(pro.eRASParticipant)
        binding.textView45.setText(pro.governmentAffiliated)
        binding.textView47.setText(pro.additionalComments)
        binding.textView49.setText(String.valueOf(pro.requiredYears))
        binding.textView51.setText(String.valueOf(pro.programID))

        /*Log.e("DDDDDDDDDDD","proSearch<> "+ arguments?.getString("programNameString"))

        Log.e("DDDDDDDDDDD","proSearch<> "+ arguments?.getString("locationString"))
        Log.e("DDDDDDDDDDD","proSearch<> "+ arguments?.getString("specialityString"))
        var programNameString = arguments?.getString("programNameString")
        var locationString = arguments?.getString("locationString")
        var specialityString = arguments?.getString("specialityString")
        var position = arguments?.getInt("position")

        this.programsViewModel.getSpecialityCompleteSearch(programNameString.toString(), locationString.toString(), specialityString.toString() )
        programsViewModel.getProgramCompSearchObservers().observe(requireActivity())
        { programCompSearchList ->

            try {
                if (programCompSearchList.size > 0) {
                    val pro = programCompSearchList[position!!]
                    binding.textView3.setText(pro.programName)
                    binding.textView5.setText(pro.speciality)
                    binding.textView7.setText(pro.location)
                    binding.textView9.setText(pro.adminInfo)
                    binding.textView11.setText(pro.contactInfo)
                    binding.textView13.setText(pro.programLink)
                    binding.textView15.setText(pro.residencyLink)
                    binding.textView17.setText(pro.lastUpdated)
                    binding.textView19.setText(pro.surveyReceived)
                    binding.textView21.setText(pro.locationInfo)
                    binding.textView23.setText(pro.sponsor)
                    binding.textView25.setText(pro.participant1)
                    binding.textView27.setText(pro.participant2)
                    binding.textView29.setText(pro.participant3)
                    binding.textView31.setText(pro.programType)
                    binding.textView33.setText(pro.programAffiliation)
                    binding.textView35.setText(String.valueOf(pro.acceptingNextYear))
                    binding.textView37.setText(pro.acceptingApplications)
                    binding.textView39.setText(pro.acceptingNextYear)
                    binding.textView41.setText(pro.startingDate)
                    binding.textView43.setText(pro.eRASParticipant)
                    binding.textView45.setText(pro.governmentAffiliated)
                    binding.textView47.setText(pro.additionalComments)
                    binding.textView49.setText(String.valueOf(pro.requiredYears))
                    binding.textView51.setText(String.valueOf(pro.programID))
                } else {
                    Toast.makeText(context, "No records found!", Toast.LENGTH_LONG)
                        .show()

                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }*/
    }
}