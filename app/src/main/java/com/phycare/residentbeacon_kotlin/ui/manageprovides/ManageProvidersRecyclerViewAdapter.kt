package com.phycare.residentbeacon_kotlin.ui.manageprovides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.phycare.residentbeacon_kotlin.databinding.ManageprovidesRowItemBinding
import com.phycare.residentbeacon_kotlin.ui.residents.ResidentCompleteSearch

class ManageProvidersRecyclerViewAdapter(val residentCompList: List<ResidentCompleteSearch>) : RecyclerView.Adapter<ManageProvidersRecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ManageprovidesRowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return residentCompList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(residentCompList[position])
    }


    class MyViewHolder(val binding: ManageprovidesRowItemBinding) : ViewHolder(binding.root){
        fun bind(residentCompleteSearch: ResidentCompleteSearch) {
                binding.textViewManageName.text = "Resident Name : "+residentCompleteSearch.residentName?.trim()?.replace("\n", " ")
            if (residentCompleteSearch.location != null && !residentCompleteSearch.location.isEmpty()) {
                binding.textViewManageLocation.text = "Location : "+residentCompleteSearch.location
                binding.textViewManageLocation.visibility = View.VISIBLE
            }else{
                binding.textViewManageLocation.visibility = View.GONE
            }

            if (residentCompleteSearch.pgy != null && residentCompleteSearch.pgy.isNotEmpty()) {
                binding.textViewManagePgy.text = "PGY : "+residentCompleteSearch.pgy
                binding.textViewManagePgy.visibility = View.VISIBLE
            }else{
                binding.textViewManagePgy.visibility = View.GONE
            }
            if (residentCompleteSearch.classOf != null && residentCompleteSearch.classOf.isNotEmpty()) {
                binding.textViewClassOf.text = "ClassOf : "+residentCompleteSearch. classOf
                binding.textViewClassOf.visibility = View.VISIBLE
            }else{
                binding.textViewClassOf.visibility = View.GONE
            }
            if (residentCompleteSearch.mailID != null && residentCompleteSearch.mailID.isNotEmpty()) {
                binding.textViewMailID.text = "Mail ID : "+residentCompleteSearch.mailID
                binding.textViewMailID.visibility = View.VISIBLE
            }else{
                binding.textViewMailID.visibility = View.GONE
            }

                binding.textViewManageStatus.text = "Status : "+"Active"
        }
    }
}