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
import com.Mandeep.ventyvideoplayer.databinding.FragmentVideoFoldersBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VideoFoldersFragment  @Inject constructor(): Fragment()  {

    lateinit var binding : FragmentVideoFoldersBinding
    var arrayList:ArrayList<MediaClasss>?=null
    var bucketList:ArrayList<MediaClasss>?=null
    var isShow:Boolean = false

    @Inject
    lateinit var  fetchVideos: FetchVideos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayList= ArrayList()
        bucketList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVideoFoldersBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        fetchVideos.fetchAllVideos(requireContext())
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!isShow) {
                arrayList = fetchVideos.fetchAllVideos(requireContext())
               bucketList = fetchVideos.gettingFolderFromArayList(arrayList!!)
                if(!arrayList?.isEmpty()!!)
                {
                    val adapter = MyAdapter(requireContext(),bucketList,ItemLayouts.BUCKET_ITEM_LAYOUT)
                    binding.bucketRectyclerView2.layoutManager = LinearLayoutManager(requireContext())
                    binding.bucketRectyclerView2.adapter = adapter
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