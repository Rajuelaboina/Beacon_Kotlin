package com.phycare.residentbeacon_kotlin.ui.manageprovides


import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.adapter.ProgramIDFilterAdapter
import com.phycare.residentbeacon_kotlin.adapter.ResidentsPGYFilterAdapter
import com.phycare.residentbeacon_kotlin.databinding.FragmentAddUserBinding
import com.phycare.residentbeacon_kotlin.ui.programs.ProgramsViewModel
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentCompleteSearch
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentsViewModel
import com.phycare.residentbeacon_kotlin.utils.Utility


class AddUserFragment : Fragment() {
    val IMAGE_CAPTURE_ID = 100
    val CAMERA_PERMISSION = 105
    val IMAGEGALLERY_PERMISSION = 107
    val IMAGE_GALLERY_ID = 109
    lateinit var binding: FragmentAddUserBinding
    private lateinit var programsViewModel: ProgramsViewModel
    private lateinit var residentsViewModel: ResidentsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_add_user, container, false)
        programsViewModel = ViewModelProvider(this)[ProgramsViewModel::class.java]
        residentsViewModel = ViewModelProvider(this)[ResidentsViewModel::class.java]
        binding = FragmentAddUserBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         loadProgramData()
         if (arguments != null){
             var residentCompleteSearch = arguments?.getSerializable("MANAGE") as ResidentCompleteSearch
               binding.residentNameAutoCompleteTextView.setText(residentCompleteSearch.residentName.toString())
             binding.PGYSpinner.setText(residentCompleteSearch.pgy)
             binding.ProgramIdAutoCompleteTextView.setText(residentCompleteSearch.programID.toString() + "_" + residentCompleteSearch.programName)
             val _imgUrl: String = Utility.ImageURL + residentCompleteSearch.photo
             //Log.e("Image url", "ImageUrl: $_imgUrl")
             Glide.with(requireContext())
                 .load(_imgUrl) // image url
                 .placeholder(R.drawable.doctor) // any placeholder to load at start
                 // .error(R.drawable.imagenotfound)  // any image in case of error
                 //.override(200, 200) // resizing
                 .centerCrop()
                 .circleCrop()
                 .into(binding.addUserImageView)
         }else{
             binding.PGYSpinner.setText("Select PGY")
             binding.ProgramIdAutoCompleteTextView.setText("Select Program")
         }


        binding.cancelUserButton.setOnClickListener {
            findNavController().navigate(R.id.nav_manageproviders)
        }

        // load PGY
        residentsViewModel.getAllPgy()
      //  binding.PGYSpinner.setText("Select Pgy")
        residentsViewModel.pgyList.removeAt(0)
        val filterAdapter = ResidentsPGYFilterAdapter(requireContext(),R.layout.item_row,residentsViewModel.pgyList)
        binding.PGYSpinner.setAdapter(filterAdapter)

        // ----  Choice Image From gallery Or Camera
        binding.imageView.setOnClickListener { selectImage() }

        extInputLayoutActions()

        binding.createUserButton.setOnClickListener {
            if (isValidUser()) {
            }
        }

    }

    private fun extInputLayoutActions() {
        binding.residentNameAutoCompleteTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.personNameTLayout.error = ""
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.ProgramIdAutoCompleteTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.programIdTLayout.error = ""
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.PGYSpinner.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.pgyTLayout.error = ""
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun isValidUser(): Boolean {
        var valid = true
        if (binding.residentNameAutoCompleteTextView.getText().toString().isEmpty()) {
            binding.personNameTLayout.setError("Provider Name required")
            valid = false
        } else if (binding.ProgramIdAutoCompleteTextView.getText().toString().equals("Select Program")) {
            binding.programIdTLayout.setError("please select Program ID")
            valid = false
        } else if (binding.PGYSpinner.getText().toString().equals("Select PGY")) {
            binding.pgyTLayout.setError("Please Select Provider PGY")
            valid = false
        }
        return valid
    }

    private fun loadProgramData() {
        programsViewModel.getAddUserSpecialityCompleteSearch("","","")
        val filterIDAdapter = ProgramIDFilterAdapter(requireContext(),R.layout.item_row,programsViewModel.addUserComList)
        binding.ProgramIdAutoCompleteTextView.setAdapter(filterIDAdapter)
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Take / Add Photo!")
        builder.setItems(options
        ) { dialog, which ->
           if (options[which] == "Take Photo"){
               if (ContextCompat.checkSelfPermission(requireActivity(),CAMERA) == PackageManager.PERMISSION_DENIED){
                   ActivityCompat.requestPermissions(requireActivity(),arrayOf(CAMERA),CAMERA_PERMISSION)
               }else{
                   launchCamera()
               }

           }else if(options[which] == "Choose from Gallery"){

              /* if (ContextCompat.checkSelfPermission(requireContext(), permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                   ActivityCompat.requestPermissions(requireActivity(),arrayOf(permission.WRITE_EXTERNAL_STORAGE),IMAGEGALLERY_PERMISSION)
               }
               else {
                   launchGallery()
               }*/
               launchGallery()
           }else{
            dialog.dismiss()
           }
        }
        builder.show()
    }

    private fun launchCamera() {
           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
          try {
              startActivityForResult(intent,IMAGE_CAPTURE_ID)
          }catch (e: ActivityNotFoundException){
              println(e.message)
          }

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT) //
        startActivityForResult(Intent.createChooser(intent, "Select File"),IMAGE_GALLERY_ID)
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                launchGallery()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }else if (requestCode == IMAGEGALLERY_PERMISSION){
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                launchGallery()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_ID && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            //val imageBitmap = data?.data as Bitmap
            binding.addUserImageView.setImageBitmap(imageBitmap)
        }else if (requestCode == IMAGE_GALLERY_ID && resultCode == RESULT_OK){
           // val imageBitmap = data?.data as Bitmap
            val uri = data?.data
            Log.e("Image Url","Url: $uri")
            val imageBitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(uri!!))
           binding.addUserImageView.setImageBitmap(imageBitmap)

        }else if (resultCode == Activity.RESULT_CANCELED){

        }

    }
}