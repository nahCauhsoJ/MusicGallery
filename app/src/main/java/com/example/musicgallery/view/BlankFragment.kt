package com.example.musicgallery.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.musicgallery.R

class BlankFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findNavController().navigate(R.id.navigation_rock)
    }

}