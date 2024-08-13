package com.phycare.residentbeacon_kotlin.ui.residents

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView

import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.adapter.ResidentsPGYFilterAdapter
import com.phycare.residentbeacon_kotlin.databinding.FragmentResidents2Binding
import com.phycare.residentbeacon_kotlin.listeners.ItemClickListener
import com.phycare.residentbeacon_kotlin.utils.CustomOnClickerListener
import com.phycare.residentbeacon_kotlin.utils.CustomOnScrollListener


class ResidentFragments2 : Fragment() , ItemClickListener {
   lateinit var binding: FragmentResidents2Binding
    lateinit var residentsViewModel: ResidentsViewModel
    lateinit var searchAdapter: ResidentCompleteSearchAdapter

     var searchString = ""
     var locationString = ""
     var pgyString = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        residentsViewModel = ViewModelProvider(this)[ResidentsViewModel::class.java]
        binding = FragmentResidents2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ----------- Load Program Data -------//
        LoadProgramData()

        // Residents Display
        residentsViewModel.getAllStates()

        binding.locationspinner.setText("All")
        val adapterFilter = ResidentsAdapterFilter(requireContext(),R.layout.item_row, residentsViewModel.stateList)
        binding.locationspinner.setAdapter(adapterFilter)


        // get PGY data
         residentsViewModel.getAllPgy()
        binding.pgyspinner.setText("All")
        val filterAdapter = ResidentsPGYFilterAdapter(requireContext(),R.layout.item_row,residentsViewModel.pgyList)
        binding.pgyspinner.setAdapter(filterAdapter)



        // progress visible or invisible
        binding.progressBarResidents.visibility = View.VISIBLE
        residentsViewModel.data.observe(requireActivity()) { (isSuccessful, code, message) ->
            binding.progressBarResidents.visibility = View.GONE
            //Toast.makeText(getContext(),responseModel.getMessage(),Toast.LENGTH_LONG).show();
        }

       /* binding.autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchString = s.toString()
                LoadProgramData()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        binding.locationspinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
               // Log.e("<><><><><><><><><><>", "<><><><>$s")
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
                LoadProgramData()
            }
            override fun afterTextChanged(s: Editable) {}
        })
        binding.pgyspinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.e("<><><><><><><><><><>", "<><><><>$s")
                pgyString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
                LoadProgramData()
            }
            override fun afterTextChanged(s: Editable) {}
        }) */

        binding.autoCompleteTextView.addTextChangedListener(CustomTextWatcher())
        binding.locationspinner.addTextChangedListener(CustomTextWatcher())
        binding.pgyspinner.addTextChangedListener(CustomTextWatcher())

        //  -----   Key Board close fun when click the TextInputLayout  ---- //
        binding.recyclerView2.addOnScrollListener(CustomOnScrollListener())
        binding.locationspinner.setOnClickListener(CustomOnClickerListener())
        binding.pgyspinner.setOnClickListener(CustomOnClickerListener())
        ResidentCompleteSearchAdapter.OnItemClickListener(this)
    }

    fun LoadProgramData(){
        residentsViewModel.getCompleteSearch(searchString, locationString, pgyString)
        residentsViewModel.getCompleteSearchDataObserver()!!
            .observe(requireActivity()
            ) { residentCompList ->
                if (residentCompList.size > 0) {
                    searchAdapter = ResidentCompleteSearchAdapter(requireContext(), residentCompList)
                    binding.recyclerView2.adapter = searchAdapter
                    binding.recyclerView2.visibility = View.VISIBLE
                    //  binding.textViewNoRecards.setVisibility(View.GONE);
                    binding.textviewNoRecords.visibility = View.GONE
                } else {
                    //Toast.makeText(getContext(),"No records found!",Toast.LENGTH_LONG).show();
                    binding.recyclerView2.visibility = View.GONE
                    //  binding.textViewNoRecards.setVisibility(View.VISIBLE);
                    binding.textviewNoRecords.visibility = View.VISIBLE
                }
            }

    }

    override fun onItemClick(residentCompleteSearch: ResidentCompleteSearch) {
        val bundle = Bundle()
       // bundle.putString("NAME","Android")
       bundle.putSerializable("SEARCH", residentCompleteSearch)
       // findNavController(requireView()).navigate(R.id.nav_residentSearchDetailsFragment,bundle)
        findNavController().navigate(R.id.nav_residentSearchDetailsFragment,bundle)
    }

   inner class CustomTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.hashCode() == binding.autoCompleteTextView.text.hashCode()){
                searchString = s.toString()
            }
            else if (s.hashCode() == binding.locationspinner.text.hashCode()){
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.pgyspinner.text.hashCode()){
                pgyString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }
            val handler = Handler(Looper.getMainLooper())
            val runnable = Runnable {
                binding.progressBarResidents.visibility = View.VISIBLE
                LoadProgramData()
            }
            handler.postDelayed(runnable,1000)

        }
        override fun afterTextChanged(s: Editable?) {
        }

   }
} // main class