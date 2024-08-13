package com.phycare.residentbeacon_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.ui.programs.ProgramCompSearch

class ProgramIDFilterAdapter(context: Context, mLayoutResourceId: Int, var programList: MutableList<ProgramCompSearch>) : ArrayAdapter<ProgramCompSearch>(context,mLayoutResourceId) {
    val mListAll : ArrayList<ProgramCompSearch> = ArrayList(programList)
    val filteredPeople: ArrayList<ProgramCompSearch> = ArrayList(programList)

    override fun getCount(): Int {
        return programList.size
    }

    override fun getItem(position: Int): ProgramCompSearch? {
        return programList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_row,null)
        val tv1 = view.findViewById<TextView>(R.id.textViewRow)
        if (programList[position].programID !=null){
            tv1.text = programList[position].programID.toString() + "_" + programList[position].programName
        }else{
            tv1.text =  programList[position].programName
        }


        return view
    }

    override fun getFilter(): Filter {
        return object : Filter(){

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val stateNamesSuggestion: MutableList<ProgramCompSearch> = ArrayList()
                    for (country in filteredPeople) {
                        if (country.programName!!.lowercase().startsWith(constraint.toString().lowercase())
                        ) {
                            stateNamesSuggestion.add(country)
                        }
                    }
                    filterResults.values = stateNamesSuggestion
                    filterResults.count = stateNamesSuggestion.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                mListAll.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is ProgramCompSearch) {
                            mListAll.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    mListAll.addAll(filteredPeople)
                    notifyDataSetInvalidated()
                }
            }
            override fun convertResultToString(resultValue: Any?): CharSequence {
               // return (resultValue as ProgramCompSearch).programName
                return if ((resultValue as ProgramCompSearch).programID != null) {
                    resultValue.programID.toString() + "_" + resultValue.programName
                } else ({
                    resultValue.programName
                }).toString()
            }

        }
    }
}