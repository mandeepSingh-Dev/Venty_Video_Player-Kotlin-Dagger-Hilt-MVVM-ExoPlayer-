package com.Mandeep.ventyvideoplayer.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.MVVM.MyAdapter
import com.Mandeep.ventyvideoplayer.Util.ItemLayouts
import com.Mandeep.ventyvideoplayer.databinding.FragmentBucketVideosBinding


class BucketVideosFragment : Fragment() {

    lateinit var binding: FragmentBucketVideosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBucketVideosBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val bucketVideoList:ArrayList<MediaClasss> = arguments?.getParcelableArrayList("BucketVideosList")!!
            Log.d("dfuue44gfy33",bucketVideoList.size.toString())

          val adapter = MyAdapter(requireContext(),bucketVideoList, ItemLayouts.VIDEO_ITEM_LAYOUT)
            binding.bucketVideoRectyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.bucketVideoRectyclerView.adapter = adapter

        }
    }
}