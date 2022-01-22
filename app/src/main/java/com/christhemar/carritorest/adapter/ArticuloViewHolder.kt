package com.christhemar.carritorest.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christhemar.carritorest.R
import com.christhemar.carritorest.model.Articulo

class ArticuloViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val img=view.findViewById<ImageView>(R.id.img)
    val nombre=view.findViewById<TextView>(R.id.nombre)
    val marca=view.findViewById<TextView>(R.id.marca)
    val precio=view.findViewById<TextView>(R.id.precio)

    fun render(articulo:Articulo){
        Glide.with(img.context).load(articulo.img).error(R.drawable.ic_baseline_broken_image_24).into(img)
        nombre.text=articulo.producto.toString()
        marca.text=articulo.marca.toString()
        precio.text=articulo.precio.toString()
    }

}