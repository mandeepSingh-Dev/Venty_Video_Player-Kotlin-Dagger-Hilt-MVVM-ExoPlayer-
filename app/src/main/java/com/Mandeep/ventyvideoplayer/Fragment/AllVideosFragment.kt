package com.Mandeep.ventyvideoplayer.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.MVVM.MyAdapter
import com.Mandeep.ventyvideoplayer.Util.FetchVideos
import com.Mandeep.ventyvideoplayer.Util.ItemLayouts
import com.Mandeep.ventyvideoplayer.databinding.FragmentAllVideosBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllVideosFragment @Inject constructor() : Fragment(){


     var binding:FragmentAllVideosBinding?=null
    var arrayList:ArrayList<MediaClasss>?=null
    var isShow:Boolean = false

    @Inject
    lateinit var fetchVideos: FetchVideos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayList=ArrayList()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentAllVideosBinding.inflate(LayoutInflater.from(context))
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!isShow) {
             arrayList = fetchVideos.fetchAllVideos(requireContext())
                if(!arrayList?.isEmpty()!!)
                {

                    val adapter = MyAdapter(requireContext(),arrayList!!, ItemLayouts.VIDEO_ITEM_LAYOUT)
                    binding?.AllVideoRectyclerView?.layoutManager = LinearLayoutManager(requireContext())
                    binding?.AllVideoRectyclerView?.adapter = adapter
                }
                isShow = true
            }
        }
       else
       {
        Toast.makeText(requireContext(), "permission not allowed", Toast.LENGTH_SHORT).show()
       }
        }




}