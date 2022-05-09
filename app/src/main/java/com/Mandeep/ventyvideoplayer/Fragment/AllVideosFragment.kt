package com.Mandeep.ventyvideoplayer.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mandeep.ventyvideoplayer.MVVM.*
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

    @Inject
    lateinit var assistedFactory: MyViewModelAssistedFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayList=ArrayList()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentAllVideosBinding.inflate(LayoutInflater.from(context))
        return binding?.root!!
    }
   /* val myViewmodel: MyViewmodel by lazy{
        ViewModelProvider(this).get(MyViewmodel::class.java)
    }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  Log.d("ifubengf3FRAGMENT",myViewmodel.toString())
       // Log.d("ifubengf3FRAGMENT",myViewmodel.no.toString())


    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!isShow) {


                val myViewmodel: MyViewmodel by viewModels {
                  //  ViewModelProvider(this).get(MyViewmodel::class.java)
                    MyViewModelFactory(assistedFactory,"null")
                }
                myViewmodel.getVideosList().observe(requireActivity(), Observer {
                    Log.d("ihjfi3fFRAGMENT",it.size.toString())

                    //if(it!=null) {
                        val adapter = MyAdapter(requireContext(), it, ItemLayouts.VIDEO_ITEM_LAYOUT)
                        binding?.AllVideoRectyclerView?.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding?.AllVideoRectyclerView?.adapter = adapter
                  //  }
                })
           /*  arrayList = fetchVideos.fetchAllVideos(requireContext())
                if(!arrayList?.isEmpty()!!)
                {

                    val adapter = MyAdapter(requireContext(),arrayList!!, ItemLayouts.VIDEO_ITEM_LAYOUT)
                    binding?.AllVideoRectyclerView?.layoutManager = LinearLayoutManager(requireContext())
                    binding?.AllVideoRectyclerView?.adapter = adapter
                }*/
                isShow = true
            }
        }
       else
       {
        Toast.makeText(requireContext(), "permission not allowed", Toast.LENGTH_SHORT).show()

       }
        }




}