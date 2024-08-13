package com.phycare.residentbeacon_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.model.Pgy_model
import com.phycare.residentbeacon_kotlin.model.States

class ResidentsPGYFilterAdapter(context: Context, mLayoutResourceId: Int, var pgyList: MutableList<Pgy_model>) : ArrayAdapter<Pgy_model>(context,mLayoutResourceId) {
    val mListAll : ArrayList<Pgy_model> = ArrayList(pgyList)
    val filteredPeople: ArrayList<Pgy_model> = ArrayList(pgyList)

    override fun getCount(): Int {
        return pgyList.size
    }

    override fun getItem(position: Int): Pgy_model? {
        return pgyList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_row,null)
        val tv1 = view.findViewById<TextView>(R.id.textViewRow)
        tv1.text = pgyList[position].pgy

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter(){

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val stateNamesSuggestion: MutableList<Pgy_model> = ArrayList()
                    for (country in filteredPeople) {
                        if (country.pgy.lowercase().startsWith(constraint.toString().lowercase())
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
                        if (result is Pgy_model) {
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
                return (resultValue as Pgy_model).pgy
            }

        }
    }
}