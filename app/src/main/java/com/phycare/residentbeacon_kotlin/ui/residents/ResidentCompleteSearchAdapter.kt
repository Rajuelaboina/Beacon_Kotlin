package com.phycare.residentbeacon_kotlin.ui.residents

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.phycare.residentbeacon_kotlin.R
import com.phycare.residentbeacon_kotlin.listeners.ItemClickListener
import com.phycare.residentbeacon_kotlin.utils.Utility

class ResidentCompleteSearchAdapter(var mContext: Context, var residentCList: List<ResidentCompleteSearch>) :
    RecyclerView.Adapter<ResidentCompleteSearchAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item_row,parent,false))
    }

    override fun getItemCount(): Int {
        return residentCList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(residentCList[position]!!)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(residentCList[position])
        }
    }


    class MyViewHolder(itemView: View) : ViewHolder(itemView){

        lateinit var tv1: TextView
        lateinit var tv2:TextView
        lateinit var imageView: ImageView

        fun bind(residentCompleteSearch: ResidentCompleteSearch) {
            tv1 = itemView.findViewById(R.id.textViewName)
            tv2 = itemView.findViewById<TextView>(R.id.textViewLocation)
            imageView = itemView.findViewById(R.id.imageViewResident)
            tv1.setText(residentCompleteSearch.residentName.toString().replace("\n", " "))
            tv2.setText("Location: " + residentCompleteSearch.location.toString())
            // image Loading
            val _imgUrl: String = Utility.ImageURL + residentCompleteSearch.photo
            Log.e("Image url", "ImageUrl: $_imgUrl")
            Glide.with(itemView.context)
                .load(_imgUrl) // image url
                .placeholder(R.drawable.doctor) // any placeholder to load at start
                // .error(R.drawable.imagenotfound)  // any image in case of error
                //.override(200, 200) // resizing
                .centerCrop()
                .circleCrop()
                .into(imageView)
        }
    }
    companion object {
        lateinit var itemClickListener: ItemClickListener
        fun OnItemClickListener(ClickListener: ItemClickListener) {
            itemClickListener = ClickListener
        }
    }
}