package com.Mandeep.ventyvideoplayer.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mandeep.ventyvideoplayer.MVVM.*
import com.Mandeep.ventyvideoplayer.Util.ItemLayouts
import com.Mandeep.ventyvideoplayer.databinding.FragmentBucketVideosBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BucketVideosFragment : Fragment() {

    lateinit var binding: FragmentBucketVideosBinding

    @Inject
    lateinit var assistedFactory:MyViewModelAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBucketVideosBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
           val bucketname = arguments?.getString("BUCKET_NAME")
           // val bucketVideoList:ArrayList<MediaClasss> = arguments?.getParcelableArrayList("BucketVideosList")!!
          //  Log.d("dfuue44gfy33",bucketVideoList.size.toString())

            Log.d("37rf3tyf",bucketname+"skods")
            val myViewmodel : MyViewmodel by viewModels {
                MyViewModelFactory(assistedFactory,bucketname!!)
            }
            myViewmodel.getFolderVideos().observe(viewLifecycleOwner, Observer {
                val adapter = MyAdapter(requireContext(),it, ItemLayouts.VIDEO_ITEM_LAYOUT)
                binding.bucketVideoRectyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.bucketVideoRectyclerView.adapter = adapter
            })

        }
    }
}