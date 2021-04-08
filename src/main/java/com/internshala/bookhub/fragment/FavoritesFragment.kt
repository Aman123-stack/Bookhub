package com.internshala.bookhub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.internshala.bookhub.R


class FavoritesFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_favorites, container, false)
        return view
    }


}