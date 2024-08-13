package com.phycare.residentbeacon_kotlin.ui.programs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.databinding.FragmentPrograms2Binding
import com.phycare.residentbeacon_kotlin.listeners.ProgramItemClickListener
import com.phycare.residentbeacon_kotlin.model.Response_Model
import com.phycare.residentbeacon_kotlin.utils.CustomOnClickerListener

class ProgramsFragment2 : Fragment() , ProgramItemClickListener {
    private var _binding: FragmentPrograms2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var programNameString = ""
    private var specialityString = ""
    private var locationString = ""
    private lateinit var programsViewModel: ProgramsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        programsViewModel = ViewModelProvider(this)[ProgramsViewModel::class.java]

        _binding = FragmentPrograms2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        // ----------- Load Program Data -------//
        loadProgramData()

        binding.progressBarPrograms.visibility = View.VISIBLE
        programsViewModel.data.observe(requireActivity(),object : Observer<Response_Model>{
            override fun onChanged(value: Response_Model) {
                binding.progressBarPrograms.visibility = View.GONE
            }
        })

        //  ------ get the  Residents Data ------------------  //
        programsViewModel.getAllStates()
        binding.locationspinner.setText("All")
        val programStateAdapter = ProgramStatesAdapterFilter(requireContext(), R.layout.item_row, programsViewModel.programStateList)
        binding.locationspinner.setAdapter(programStateAdapter)

        // --------  get the  speciality Data --------------------------//
        programsViewModel.getSpecialityDetails()
        binding.programSpecialitySpinner.setText("All")
        val specialityFilterAdapter = SpecialityFilterAdapter(requireContext(),R.layout.item_row, programsViewModel.specialityList)
        binding.programSpecialitySpinner.setAdapter(specialityFilterAdapter)

        // ------ spinner of  textInputLayout Listeners ------------------//

        /*binding.programEditTextTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                programNameString = s.toString()
                loadProgramData()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.locationspinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                locationString = s.toString()
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
                loadProgramData()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.programSpecialitySpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                specialityString = s.toString()
                specialityString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
                loadProgramData()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })*/


        binding.programEditTextTextName.addTextChangedListener(CustomTextWatcher())
        binding.locationspinner.addTextChangedListener(CustomTextWatcher())
        binding.programSpecialitySpinner.addTextChangedListener(CustomTextWatcher())

        binding.locationspinner.setOnClickListener(CustomOnClickerListener())
        binding.programSpecialitySpinner.setOnClickListener(CustomOnClickerListener())
        ProgramSearchAdapter.programOnItemClickListener(this)
        binding.programRecyclerViewPF.addItemDecoration( DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        return root
    }

    private fun loadProgramData() {
        this.programsViewModel.getSpecialityCompleteSearch( programNameString, locationString, specialityString )
        programsViewModel.getProgramCompSearchObservers().observe(requireActivity())
        { programCompSearchList ->
            try {
                if (programCompSearchList.size > 0) {
                    val adapter = ProgramSearchAdapter(context, programCompSearchList)
                    binding.programRecyclerViewPF.adapter = adapter
                    binding.programRecyclerViewPF.visibility = View.VISIBLE
                    binding.textViewProgramNoRecards.visibility = View.GONE
                } else {
                    Toast.makeText(context, "No records found!", Toast.LENGTH_LONG)
                        .show()
                    binding.programRecyclerViewPF.visibility = View.GONE
                    binding.textViewProgramNoRecards.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
    override fun onItemClick(position: Int, programCompSearch: ProgramCompSearch) {
           val bundle = Bundle()
        /*  bundle.putString("programNameString",programNameString)
        bundle.putString("locationString",locationString)
        bundle.putString("specialityString",specialityString)
        bundle.putInt("position",position)*/
         bundle.putSerializable("DATA",programCompSearch)
        findNavController().navigate(R.id.nav_programSearchDetailsFragment,bundle)
    }
    inner class CustomTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.hashCode() == binding.programEditTextTextName.text.hashCode()){
                programNameString = s.toString()
            }
            else if (s.hashCode() == binding.locationspinner.text.hashCode()){
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.programSpecialitySpinner.text.hashCode()){
                specialityString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }
            loadProgramData()
           /* val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable {
                binding.progressBarResidents.visibility = View.VISIBLE
                LoadProgramData()
            }
            handler.postDelayed(runnable,1000)*/

        }
        override fun afterTextChanged(s: Editable?) {
        }

    }
}