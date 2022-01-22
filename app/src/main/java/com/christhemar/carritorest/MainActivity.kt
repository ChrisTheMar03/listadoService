package com.christhemar.carritorest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.christhemar.carritorest.fragments.LoginFragment
import com.christhemar.carritorest.interfaces.Navegar

class MainActivity : AppCompatActivity(),Navegar {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container,LoginFragment())
                .commit()
        }



    }

    override fun navegar(fragment: Fragment, back: Boolean) {
        val transaction:FragmentTransaction=supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if(back){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}