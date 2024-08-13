package com.phycare.residentbeacon_kotlin.ui.programs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.model.Speciality

class SpecialityFilterAdapter(mContext: Context,myResourceId: Int,val specialityList: MutableList<Speciality>): ArrayAdapter<Speciality>(mContext,myResourceId,specialityList) {
     val mListAll: ArrayList<Speciality> = ArrayList()
    val filteredSpeciality: ArrayList<Speciality> = ArrayList()

    override fun getCount(): Int {
        return specialityList.size
    }

    override fun getItem(position: Int): Speciality {
        return specialityList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        val textView = view.findViewById<TextView>(R.id.textViewRow)
        textView.text = specialityList[position].speciality
        return view
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint!=null){
                   val specialityNamesSuggestion : MutableList<Speciality> = ArrayList()
                    for (speciality in filteredSpeciality){
                       if (speciality.speciality.lowercase().startsWith(constraint.toString().lowercase())) {
                            specialityNamesSuggestion.add(speciality)
                       }
                    }
                    filterResults.values = specialityList
                    filterResults.count = specialityList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                    mListAll.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is Speciality) {
                            mListAll.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    mListAll.addAll(filteredSpeciality)
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as Speciality).speciality
            }

        }
    }
}