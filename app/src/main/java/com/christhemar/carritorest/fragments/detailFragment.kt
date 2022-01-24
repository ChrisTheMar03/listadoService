package com.christhemar.carritorest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.christhemar.carritorest.R
import com.christhemar.carritorest.interfaces.Navegar
import com.google.android.material.button.MaterialButton


class detailFragment : Fragment() {

    private var datoMarca:String?=""
    private var datoImagen:String?=""
    private var datoProducto:String?=""
    private var datoPrecio:String?=""

    var marca:TextView?=null
    var imagen:ImageView?=null
    var producto:TextView?=null
    var precio:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("key",this, FragmentResultListener { requestKey, result ->
            datoMarca=result.getString("marca")
            datoImagen=result.getString("imagen")
            datoProducto=result.getString("producto")
            datoPrecio=result.getDouble("precio").toString()

            //Adaptando datos
            marca?.text=datoMarca.toString()
            producto?.text=datoProducto.toString()
            precio?.text=datoPrecio.toString()
            imagen?.let { Glide.with(this).load(datoImagen).into(it) }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marca=view.findViewById<TextView>(R.id.txt_detaller_marca)
        producto=view.findViewById(R.id.txt_detaller_producto)
        imagen=view.findViewById(R.id.imageView)
        precio=view.findViewById(R.id.txt_detaller_Precio)
        val btnVolver=view.findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener {
            //(activity as Navegar).navegar(mainFragment(),false)
            Toast.makeText(context,"Boton en Mantenimiento!!",Toast.LENGTH_LONG).show()
        }

    }

}