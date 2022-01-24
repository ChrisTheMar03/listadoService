package com.christhemar.carritorest.fragments

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.christhemar.carritorest.R
import com.christhemar.carritorest.adapter.ArticuloAdapter
import com.christhemar.carritorest.adapter.ArticuloViewHolder
import com.christhemar.carritorest.interfaces.ArticuloService
import com.christhemar.carritorest.interfaces.Navegar
//import com.christhemar.carritorest.interfaces.api
import com.christhemar.carritorest.model.Articulo
import com.christhemar.carritorest.model.ArticuloProveedor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class mainFragment : Fragment() {

    private val URL:String="http://192.168.1.42:3000/"
    lateinit var imgUri:Uri
    lateinit var nombreUser:String
    lateinit var img_detail_user:ImageView
    lateinit var txtnombreMain:TextView
    var lista = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_main,container,false)
        setUpToolbar(view)


        val recyclerView=view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=GridLayoutManager(context,2)

        val retrofit= Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val call = retrofit.create(ArticuloService::class.java).articleList()

        call.enqueue(object:Callback<List<Articulo>>{
            override fun onResponse(call: Call<List<Articulo>>, response: Response<List<Articulo>>) {
                var lista:List<Articulo>
                if(response.isSuccessful){
                    lista= response.body()!!
                    recyclerView.adapter=ArticuloAdapter(lista,{Articulo->itemSeleccionado(Articulo)})
                }else {
                    recyclerView.adapter = ArticuloAdapter(ArticuloProveedor.articuloList,{Articulo -> itemSeleccionado(Articulo)})
                }
            }

            override fun onFailure(call: Call<List<Articulo>>, t: Throwable) {

            }

        })
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_detail_user=view.findViewById(R.id.img_main_user)
        txtnombreMain=view.findViewById(R.id.txtnombre_main)
        txtnombreMain=view.findViewById(R.id.txtnombre_main)
        parentFragmentManager.setFragmentResultListener("foto",this, FragmentResultListener {
                requestKey, result ->
            if(!result.isEmpty){
                lista.add(0,result.getString("imgUri").toString())
                lista.add(1,result.getString("nombreUser").toString())
                imgUri=Uri.parse(result.getString("imgUri"))
                nombreUser=result.getString("nombreUser").toString()
                img_detail_user.setImageURI(imgUri)
                txtnombreMain.text=nombreUser
            }
        })

        img_detail_user.setOnClickListener {
            if(!lista.isEmpty()) {
                imgUri = Uri.parse(lista.get(0))
                nombreUser=lista.get(1)
                img_detail_user.setImageURI(imgUri)
                txtnombreMain.text=nombreUser.toString()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if(!lista.isEmpty()) {
            imgUri = Uri.parse(lista.get(0))
            nombreUser=lista.get(1)
            img_detail_user.setImageURI(imgUri)
            txtnombreMain.text=nombreUser.toString()
        }
    }

    private fun setUpToolbar(view: View) {
        val toolbar:Toolbar=view.findViewById(R.id.app_bar)
        val activity:AppCompatActivity= getActivity() as AppCompatActivity
        if(activity!=null){
            activity.setSupportActionBar(toolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun itemSeleccionado(articulo:Articulo){
        val bundle=Bundle()
        bundle.putString("marca",articulo.marca)
        bundle.putString("producto",articulo.producto)
        bundle.putDouble("precio",articulo.precio)
        bundle.putString("imagen",articulo.img)
        parentFragmentManager.setFragmentResult("key",bundle)
        (activity as Navegar).navegar(detailFragment(),true)
    }

}