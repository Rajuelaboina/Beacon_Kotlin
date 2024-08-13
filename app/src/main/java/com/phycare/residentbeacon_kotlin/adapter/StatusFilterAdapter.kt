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

class StatusFilterAdapter(context: Context, mLayoutResourceId: Int, private val statusList: Array<String>) : ArrayAdapter<String>(context,mLayoutResourceId) {
    val mListAll: ArrayList<String> = ArrayList()
    val filteredPeople: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return statusList.size
    }

    override fun getItem(position: Int): String? {
        return statusList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_row, null)
        val tv1 = view.findViewById<TextView>(R.id.textViewRow)
        tv1.text = statusList[position].toString()

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val stateNamesSuggestion: MutableList<String> = ArrayList()
                    for (country in filteredPeople) {
                        if (country.lowercase().startsWith(constraint.toString().lowercase())
                        ) {
                            stateNamesSuggestion.add(country.toString())
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
                            mListAll.add(result.toString())
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    mListAll.addAll(filteredPeople)
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as String)
            }

        }
    }
}