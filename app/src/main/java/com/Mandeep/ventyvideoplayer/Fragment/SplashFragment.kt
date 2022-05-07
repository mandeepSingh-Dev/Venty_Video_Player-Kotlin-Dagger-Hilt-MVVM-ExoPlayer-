package com.Mandeep.ventyvideoplayer.Fragment

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.R
import com.Mandeep.ventyvideoplayer.databinding.ActivityMainBinding
import com.Mandeep.ventyvideoplayer.databinding.FragmentSplashBinding
import kotlinx.coroutines.*


class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    var isAllowed = false
    var isShow = false
    var arrayList:ArrayList<MediaClasss>?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)

            withContext(Dispatchers.Main) {
                requestPermissions()
            }
        }
    }

    private fun requestPermissions(){

        val readPermission =   ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if(!readPermission)
        {
            isAllowed = false
            launcher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
           // allVideosFragment.skdhs?.onSendBoolean(isAllowed)
            //  allVideosFragment.onSendArrayListListener(arrayList!!)

           // videoFoldersFragment.onSendBoolean(isAllowed)
        }
        else{
            isAllowed = true
            isShow = true
            findNavController().navigate(R.id.viewPagerFragment)
          //  allVideosFragment.skdhs?.onSendBoolean(isAllowed)
          //  fetchAndShowAllVideoss()
            // videoFoldersFragment.skdhs?.onSendBoolean(isAllowed,this)

        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission(), ActivityResultCallback {
            if(it) {
                isAllowed = true
                isShow = true
                findNavController().navigate(R.id.viewPagerFragment)

           //     fetchAndShowAllVideoss()
                // allVideosFragment.onSendBoolean(isAllowed)
                // videoFoldersFragment.onSendBoolean(isAllowed)

            }else{
                isAllowed = false
                isShow = true
                findNavController().navigate(R.id.viewPagerFragment)
            }
        })



    override fun onResume() {
        super.onResume()
        if(!isShow)
        {
          //  requestPermissions()
        }
        Log.d("r3tyrf73uf",isAllowed.toString())
    }

}