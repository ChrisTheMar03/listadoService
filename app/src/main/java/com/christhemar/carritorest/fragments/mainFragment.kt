package com.christhemar.carritorest.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.christhemar.carritorest.R
import com.christhemar.carritorest.adapter.ArticuloAdapter
import com.christhemar.carritorest.adapter.ArticuloViewHolder
import com.christhemar.carritorest.interfaces.ArticuloService
import com.christhemar.carritorest.interfaces.api
import com.christhemar.carritorest.model.Articulo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class mainFragment : Fragment() {


    private var articuloList= mutableListOf<Articulo>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Verificando el menu
        setHasOptionsMenu(true)

        val obtener:Call<List<Articulo>> = api.service.articleList()

        obtener.enqueue(object:Callback<List<Articulo>>{
            override fun onResponse(
                call: Call<List<Articulo>>,
                response: Response<List<Articulo>>
            ) {
                response.body()?.forEach {
                    articuloList.add(it)
                }
            }
            override fun onFailure(call: Call<List<Articulo>>, t: Throwable) {

            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_main,container,false)
        setUpToolbar(view)
        //RecyclerView
        recyclerView=view.findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(context)
        val adapter=ArticuloAdapter(articuloList)
        recyclerView.adapter=adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

}