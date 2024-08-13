package com.phycare.residentbeacon_kotlin.ui.programs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.model.States

class ProgramStatesAdapterFilter( context: Context,myResourceId: Int,val programStateList: MutableList<ProgramState>) : ArrayAdapter<ProgramState>(context,myResourceId,programStateList) {
     val mListAll: ArrayList<ProgramState> = ArrayList()
      var filteredLocation: ArrayList<ProgramState> = ArrayList()

    override fun getCount(): Int {
        return programStateList.size
    }

    override fun getItem(position: Int): ProgramState? {
        return programStateList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false);
        val textView = view.findViewById<TextView>(R.id.textViewRow)
        textView.text = programStateList.get(position).location
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                  val filterResults = FilterResults()
                  if (constraint!=null){
                      val locationNamesSuggestion : MutableList<ProgramState> = ArrayList()
                      for (location in filteredLocation){
                          if (location.location.lowercase().startsWith(constraint.toString().lowercase())) {
                              locationNamesSuggestion.add(location)
                          }
                      }
                      filterResults.values = locationNamesSuggestion
                      filterResults.count = locationNamesSuggestion.size
                  }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                mListAll.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is ProgramState) {
                            mListAll.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    mListAll.addAll(filteredLocation)
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as ProgramState).location
            }

        }
    }

}