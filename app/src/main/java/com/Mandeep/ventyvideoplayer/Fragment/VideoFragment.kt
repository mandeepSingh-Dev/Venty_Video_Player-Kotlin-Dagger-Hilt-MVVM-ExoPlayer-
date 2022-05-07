package com.Mandeep.ventyvideoplayer.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.MVVM.MyAdapter
import com.Mandeep.ventyvideoplayer.R
import com.Mandeep.ventyvideoplayer.databinding.FragmentVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import javax.inject.Inject

class VideoFragment: Fragment() {

    lateinit var binding:FragmentVideoBinding
    private var arrayList:ArrayList<MediaClasss>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayList = ArrayList()
        val stringarr =  resources.getStringArray(R.array.Videos_string_array)
        stringarr.forEach {
          //  arrayList?.add(MediaClasss("No Artist", "No Name", it))
        }


       /* val myAdapter = MyAdapter(requireContext(),arrayList!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter*/

    }



}