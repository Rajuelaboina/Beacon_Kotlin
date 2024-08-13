package com.phycare.residentbeacon_kotlin.ui.manageprovides

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.SwipeToDeleteCallback
import com.phycare.residentbeacon_kotlin.adapter.ResidentsPGYFilterAdapter
import com.phycare.residentbeacon_kotlin.adapter.StatusFilterAdapter
import com.phycare.residentbeacon_kotlin.databinding.FragmentManageProvidesBinding
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentCompleteSearch
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentsAdapterFilter
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentsViewModel
import com.phycare.residentbeacon_kotlin.utils.CustomOnClickerListener
import com.phycare.residentbeacon_kotlin.utils.KeyBoardClose


class ManageProvidesFragment : Fragment() {
    private lateinit var binding:FragmentManageProvidesBinding
    private lateinit var residentsViewModel: ResidentsViewModel
    private var searchString = ""
    private var locationString = ""
    private var pgyString = ""
    private var statusString = ""
    lateinit var arrayList: ArrayList<ResidentCompleteSearch>
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_manage_provides, container, false)
        residentsViewModel = ViewModelProvider(this)[ResidentsViewModel::class.java]
        binding = FragmentManageProvidesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        residentsViewModel.getAllStates()
        binding.manageProviderLocationSpinner.setText("All")
        val adapterFilter = ResidentsAdapterFilter(requireContext(),R.layout.item_row, residentsViewModel.stateList)
        binding.manageProviderLocationSpinner.setAdapter(adapterFilter)

        // get PGY data
        residentsViewModel.getAllPgy()
        binding.manageProviderPGYSpinner.setText("All")
        val filterAdapter = ResidentsPGYFilterAdapter(requireContext(),R.layout.item_row,residentsViewModel.pgyList)
        binding.manageProviderPGYSpinner.setAdapter(filterAdapter)

        // --  load data from status ---- //
        binding.manageProviderStatusSpinner.setText("Active")
        val statusList = resources.getStringArray(R.array.status)
        val statusAdapter = StatusFilterAdapter(requireContext(),R.layout.item_row,statusList)
        binding.manageProviderStatusSpinner.setAdapter(statusAdapter)

        loadProgramData() // load data from recyclerView by default loading

       /*binding.autoCompleteTextViewManageProvider.addTextChangedListener(object : TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
           }
           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               searchString =s.toString()
           }
           override fun afterTextChanged(s: Editable?) {
           }
       })
        binding.manageProviderLocationSpinner.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.manageProviderPGYSpinner.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pgyString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.manageProviderStatusSpinner.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                statusString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })*/

        binding.manageProviderLocationSpinner.setOnClickListener(CustomOnClickerListener())
        binding.manageProviderPGYSpinner.setOnClickListener(CustomOnClickerListener())
        binding.manageProviderStatusSpinner.setOnClickListener(CustomOnClickerListener())
        binding.manageProviderRecyclerView.setOnClickListener(CustomOnClickerListener())

        binding.autoCompleteTextViewManageProvider.addTextChangedListener(CustomTextWatcher())
        binding.manageProviderLocationSpinner.addTextChangedListener(CustomTextWatcher())
        binding.manageProviderPGYSpinner.addTextChangedListener(CustomTextWatcher())
        binding.manageProviderStatusSpinner.addTextChangedListener(CustomTextWatcher())
        binding.manageProviderRecyclerView.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        binding.manageProviderRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // if the recycler view is scrolled
                // above hide the FAB
                if (dy > 10 && binding.addFab.isExtended) {
                    binding.addFab.shrink()
                    // fab.hide()
                     KeyBoardClose().closeKeyboard(recyclerView.rootView)
                }
                // if the recycler view is scrolled
                // above extend the FAB
                // if the recycler view is scrolled
                // above extend the FAB
                if (dy < -10 && !binding.addFab.isExtended) {
                    binding.addFab.extend()
                    // fab.show()
                    KeyBoardClose().closeKeyboard(recyclerView.rootView)
                }
                // of the recycler view is at the first
                // item always extend the FAB
                // of the recycler view is at the first
                // item always extend the FAB
                if (!recyclerView.canScrollVertically(-1)) {
                    binding.addFab.extend()
                    // fab.show()
                }
            }
        })
        // fab
        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.nav_addUserFragment)
        }
        // swipe to Edit and Delete
        swipeToEditAndDelete()

    }

    private fun loadProgramData() {
        // data load from recyclerView
        residentsViewModel.getCompleteSearch(searchString,locationString,pgyString)
        residentsViewModel.getCompleteSearchDataObserver()!!.observe(requireActivity()) { residentCompList ->
            if (residentCompList.size > 0) {
                arrayList = residentCompList as ArrayList<ResidentCompleteSearch>
                val  adapter = ManageProvidersRecyclerViewAdapter(residentCompList)
                binding.manageProviderRecyclerView.adapter = adapter
            }
        }
    }

    private fun swipeToEditAndDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position :Int = viewHolder.adapterPosition
                when(direction){
                    ItemTouchHelper.LEFT->{
                        MaterialAlertDialogBuilder(requireActivity(), R.style.RoundShapeTheme)
                            .setTitle("Are you sure, you want to DeActive the record?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, which ->
                                //SharedPrefManager.getInstance(applicationContext).isLogedout()
                                arrayList.removeAt(position)
                                val  adapter = ManageProvidersRecyclerViewAdapter(arrayList)
                                binding.manageProviderRecyclerView.adapter = adapter
                            }
                            .setNegativeButton("Cancel") { dialog, which ->
                                dialog.dismiss()
                                // DisplayAllUsers()
                                val  adapter = ManageProvidersRecyclerViewAdapter(arrayList)
                                binding.manageProviderRecyclerView.adapter = adapter
                            }
                            .show()
                    }
                    ItemTouchHelper.RIGHT->{
                        val completeSearch: ResidentCompleteSearch = arrayList[position]
                        val bundle = Bundle()
                        bundle.putSerializable("MANAGE", completeSearch)
                        findNavController(view!!).navigate(R.id.nav_addUserFragment, bundle)
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.manageProviderRecyclerView)
    }

    inner class CustomTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.hashCode() == binding.autoCompleteTextViewManageProvider.text.hashCode()){
                searchString = s.toString()
            }
            else if (s.hashCode() == binding.manageProviderLocationSpinner.text.hashCode()){
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.manageProviderPGYSpinner.text.hashCode()){
                pgyString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.manageProviderStatusSpinner.text.hashCode()){
                statusString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }
            loadProgramData()
        }
        override fun afterTextChanged(s: Editable?) {
        }

    }

}