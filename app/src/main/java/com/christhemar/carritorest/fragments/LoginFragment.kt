package com.christhemar.carritorest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.christhemar.carritorest.MainActivity
import com.christhemar.carritorest.R
import com.christhemar.carritorest.interfaces.Navegar

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn=view.findViewById<Button>(R.id.btnIr)

        btn.setOnClickListener(View.OnClickListener(){
            //Redireccionar
            (activity as Navegar).navegar(mainFragment(),false)
        })

    }

}