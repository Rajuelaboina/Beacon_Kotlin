package com.phycare.residentbeacon_kotlin.ui.communication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.adapter.ResidentsPGYFilterAdapter
import com.phycare.residentbeacon_kotlin.adapter.StatusFilterAdapter
import com.phycare.residentbeacon_kotlin.databinding.FragmentCommunicationBinding
import com.phycare.residentbeacon_kotlin.ui.programs.ProgramsViewModel
import com.phycare.residentbeacon_kotlin.ui.programs.SpecialityFilterAdapter
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentsAdapterFilter
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentsViewModel


class CommunicationFragment : Fragment() {
   lateinit var binding: FragmentCommunicationBinding
   lateinit var residentsViewModel: ResidentsViewModel
    private lateinit var programsViewModel: ProgramsViewModel
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private val emailIntent = Intent(Intent.ACTION_SEND)
    lateinit var aa: Array<String?>
    private var specialityString = ""
    private var locationString = ""
    private var pgyString = ""
    private var statusString = ""
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        programsViewModel = ViewModelProvider(this)[ProgramsViewModel::class.java]
        residentsViewModel = ViewModelProvider(this)[ResidentsViewModel::class.java]
        binding = FragmentCommunicationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Residents Display
        residentsViewModel.getAllStates()
        binding.communicationLocationSpinner.setText("All")
        val adapterFilter = ResidentsAdapterFilter(requireContext(),R.layout.item_row, residentsViewModel.stateList)
        binding.communicationLocationSpinner.setAdapter(adapterFilter)

        // get PGY data
        residentsViewModel.getAllPgy()
        binding.communicationPGYSpinner.setText("All")
        val filterAdapter = ResidentsPGYFilterAdapter(requireContext(),R.layout.item_row,residentsViewModel.pgyList)
        binding.communicationPGYSpinner.setAdapter(filterAdapter)

        // --  load data from status ---- //
        binding.communicationStatusSpinner.setText("Active")
        val statusList = resources.getStringArray(R.array.status)
        val statusAdapter = StatusFilterAdapter(requireContext(),R.layout.item_row,statusList)
        binding.communicationStatusSpinner.setAdapter(statusAdapter)

        // --------  get the  speciality Data --------------------------//
        programsViewModel.getSpecialityDetails()
        binding.communicationspecialitySpinner.setText("All")
        val specialityFilterAdapter = SpecialityFilterAdapter(requireContext(),R.layout.item_row, programsViewModel.specialityList)
        binding.communicationspecialitySpinner.setAdapter(specialityFilterAdapter)

        // --------  addTestChangeListener  ------------- //
        binding.communicationLocationSpinner.addTextChangedListener(CustomTextWatcher())
        binding.communicationPGYSpinner.addTextChangedListener(CustomTextWatcher())
        binding.communicationspecialitySpinner.addTextChangedListener(CustomTextWatcher())
        binding.communicationStatusSpinner.addTextChangedListener(CustomTextWatcher())

        // choose image
        binding.chooseImageView.setOnClickListener {
            val intent = Intent()
            intent.setType("*/*")
            //intent.setType("text/plain");
            //intent.setType("text/plain");
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.putExtra("return-data", true)
            // startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
            // startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
            launcher.launch(intent)
        }

        binding.sendEmailbutton.setOnClickListener {
            emailIntent.setType("text/plain")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, aa)
            emailIntent.putExtra( Intent.EXTRA_SUBJECT, binding.editTextTextSubject.getText().toString())
            emailIntent.putExtra(Intent.EXTRA_TEXT, binding.editTextTextBody.getText().toString())
            startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"))
        }

        binding.checkBoxAll.setOnClickListener {
            aa = arrayOfNulls(30)
            aa[0] = "demotesting@gmail.com"
            aa[1] = "demo@gmail.com"
            aa[2] = "test@gmail.com"
        }

        launcher = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                emailIntent.putExtra(Intent.EXTRA_STREAM, data!!.data)
                val uriSound = data!!.data
                val cursor = requireActivity().contentResolver
                    .query(uriSound!!, null, null, null, null)
                val nameIndex = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor!!.getColumnIndex(OpenableColumns.SIZE)
                cursor!!.moveToFirst()
                val fileName = cursor!!.getString(nameIndex)
                binding.fileNameTextView.text = fileName.toString()
            }
        }


    }

    inner class CustomTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.hashCode() == binding.communicationLocationSpinner.text.hashCode()){
                locationString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }
            else if (s.hashCode() == binding.communicationPGYSpinner.text.hashCode()){
                pgyString = if (s.toString() == "All") {
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.communicationspecialitySpinner.text.hashCode()){
                specialityString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }else if (s.hashCode() == binding.communicationStatusSpinner.text.hashCode()){
                statusString = if (s.toString() == "All"){
                    ""
                } else {
                    s.toString()
                }
            }
            //LoadProgramData()
        }
        override fun afterTextChanged(s: Editable?) {
        }

    }
   
}