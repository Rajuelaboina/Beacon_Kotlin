package com.phycare.residentbeacon_kotlin.ui.programs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.databinding.ProgramsearchitemRowBinding
import com.phycare.residentbeacon_kotlin.listeners.ProgramItemClickListener
import java.lang.String
import kotlin.Boolean
import kotlin.Int

class ProgramSearchAdapter(context: Context?, var programCompSearchList: List<ProgramCompSearch>) : RecyclerView.Adapter<ProgramSearchAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        //binding = ProgramsearchitemRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        //return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.programsearchitem_row,parent,false));
        return MyViewHolder( ProgramsearchitemRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
       return programCompSearchList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(programCompSearchList[position])
         holder.itemView.setOnClickListener {
             programItemClickListener.onItemClick(position,programCompSearchList[position])

         }
        /*holder.binding.myImg.setOnClickListener { v ->
            val expanded: Boolean = programCompSearchList.get(position).expanded
            programCompSearchList.get(position).expanded = !expanded
            notifyItemChanged(position)
        }*/
    }


    class MyViewHolder(val binding: ProgramsearchitemRowBinding) : ViewHolder(binding.root){
        fun bind(programSearch: ProgramCompSearch) {
            binding.programNametextViewtextView2.setText(programSearch.programName)
            binding.SpecialityNametextView4.setText(programSearch.speciality)
            binding.programLocationtextView6.setText(programSearch.location)

            /*binding.textView3.text = programSearch.programName
            binding.textView55.text = programSearch.speciality
            binding.textView7.text = programSearch.location
            binding.textView9.text = programSearch.adminInfo
            binding.textView11.text = programSearch.contactInfo
            binding.textView13.text = programSearch.programLink
            binding.textView15.text = programSearch.residencyLink
            binding.textView17.text = programSearch.lastUpdated
            binding.textView19.text = programSearch.surveyReceived
            binding.textView21.text = programSearch.locationInfo
            binding.textView23.text = programSearch.sponsor
            binding.textView25.text = programSearch.participant1
            binding.textView27.text = programSearch.participant2
            binding.textView29.text = programSearch.participant3
            binding.textView31.text = programSearch.programType
            binding.textView33.text = programSearch.programAffiliation
            binding.textView35.text = String.valueOf(programSearch.accreditedYears)
            binding.textView37.text = programSearch.acceptingApplications
            binding.textView39.text = programSearch.acceptingNextYear
            binding.textView41.text = programSearch.startingDate
            binding.textView43.text = programSearch.eRASParticipant
            binding.textView45.text = programSearch.governmentAffiliated
            binding.textView47.text = programSearch.additionalComments
            binding.textView49.text = String.valueOf(programSearch.requiredYears)
            binding.textView51.text = String.valueOf(programSearch.programID)*/


            val expanded: Boolean = programSearch.expanded
           /* binding.subLayout.visibility = if (expanded) View.VISIBLE else View.GONE
            if (expanded) {
                binding.myImg.setImageResource(R.drawable.baseline_expand_less_24)
                binding.titleLinearLayout.setVisibility(View.GONE)
            } else {
                binding.myImg.setImageResource(R.drawable.baseline_expand_more_24)
                binding.titleLinearLayout.setVisibility(View.VISIBLE)
            }*/

        }

    }

    companion object {
        private lateinit var programItemClickListener: ProgramItemClickListener
        fun programOnItemClickListener(programItemClickListener: ProgramItemClickListener) {
               this.programItemClickListener = programItemClickListener

        }
    }
}