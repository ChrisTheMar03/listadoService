package com.christhemar.carritorest.interfaces

import androidx.fragment.app.Fragment

interface Navegar {

    fun navegar(fragment:Fragment, back:Boolean)
    fun remover(fragment: Fragment,fragmentTwo:Fragment)
}