package com.phycare.residentbeacon_kotlin.ui.residents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.model.States

class ResidentsAdapterFilter( mContext: Context, mLayoutResourceId: Int,var stateList: MutableList<States>) : ArrayAdapter<States>(mContext,mLayoutResourceId,stateList) {
      val mListAll : ArrayList<States> = ArrayList(stateList)
      val filteredPeople: ArrayList<States> = ArrayList(stateList)

    override fun getCount(): Int {
        return stateList.size
    }
    override fun getItem(position: Int): States? {
        return stateList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_row,null)
        val tv1 = view.findViewById<TextView>(R.id.textViewRow)
        tv1.text = stateList[position].location

        return view
    }

    override fun getFilter(): Filter {
        return object :Filter(){

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val stateNamesSuggestion: MutableList<States> = ArrayList()
                    for (country in filteredPeople) {
                        if (country.location.lowercase().startsWith(constraint.toString().lowercase())
                        ) {
                            stateNamesSuggestion.add(country)
                        }
                    }
                    filterResults.values = stateNamesSuggestion
                    filterResults.count = stateNamesSuggestion.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                mListAll.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is States) {
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
                return (resultValue as States).location
            }

        }
    }

}