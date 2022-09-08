package com.example.myshoppal.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppal.R
import com.myshoppal.models.Item

class ItemListAdapter(private val itemList:ArrayList<Item>):RecyclerView.Adapter<ItemListAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemListAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_layout_list,parent,false)
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: ItemListAdapter.MyViewHolder, position: Int) {
val item:Item=itemList[position]
        holder.itemDescription.text=item.description
        holder.possibleLocation.text=item.location
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }
    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemDescription:TextView=itemView.findViewById(R.id.itemDescription)
        val possibleLocation:TextView=itemView.findViewById(R.id.possibleLocation)
    }


}
