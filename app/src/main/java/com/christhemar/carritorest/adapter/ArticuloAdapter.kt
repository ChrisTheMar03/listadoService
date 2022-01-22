package com.christhemar.carritorest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christhemar.carritorest.R
import com.christhemar.carritorest.model.Articulo

class ArticuloAdapter(private val articuloList:List<Articulo>):RecyclerView.Adapter<ArticuloViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticuloViewHolder {

        val layoutInflater=LayoutInflater.from(parent.context)
        return ArticuloViewHolder(layoutInflater.inflate(R.layout.item_product,parent,false))
    }

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) {
        val item=articuloList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return articuloList.size
    }
}