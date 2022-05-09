package com.Mandeep.ventyvideoplayer.Fragment

import android.annotation.SuppressLint
import android.content.*
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.Util.MyViewPagerAdpater
import com.Mandeep.ventyvideoplayer.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    lateinit var binding:FragmentViewPagerBinding
    var isAllowed = false
    var isShow = false
    var arrayList:ArrayList<MediaClasss>?=null

    @Inject
    lateinit var allVideosFragment:AllVideosFragment
    @Inject
    lateinit var videoFoldersFragment:VideoFoldersFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayList = ArrayList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }
    private val reciever = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("dhdu444fd","dfdhfdjh")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setUpViewPager()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(reciever, IntentFilter("SENDING_LIST"))

        setUpViewPager()


    }// onViewCreate finished
    fun setUpViewPager(){
        val adapter = MyViewPagerAdpater(requireActivity())
        binding.viewpagerr.adapter = adapter

        val texts= arrayOf("All Videos","Folders")
        TabLayoutMediator(binding.tablayout,binding.viewpagerr,object:TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                    tab.text = texts[position]
            }
        }).attach()
        binding.viewpagerr.offscreenPageLimit =2
        }

    private fun requestPermissions(){

        val readPermission =   ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if(!readPermission)
        {
            isAllowed = false
            launcher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            //  allVideosFragment.onSendArrayListListener(arrayList!!)


        }
        else{
            isAllowed = true
            isShow = true
            fetchAndShowAllVideoss()
            // videoFoldersFragment.skdhs?.onSendBoolean(isAllowed,this)

        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {


            if(it) {
                isAllowed = true
                isShow = true
                fetchAndShowAllVideoss()
                // allVideosFragment.onSendBoolean(isAllowed)
                // videoFoldersFragment.onSendBoolean(isAllowed)

            }else{
                isAllowed = false
                isShow = true

            }
        })

    @SuppressLint("Range", "Recycle")
    fun fetchAndShowAllVideoss(){
        val uri = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val cursor = requireActivity().contentResolver.query(uri,null,null,null,null);

        val titleColumn =   cursor?.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
        val artistColumn =cursor?.getColumnIndex(MediaStore.Video.VideoColumns.ARTIST)
        val bucketColumn = cursor?.getColumnIndex(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME)
        val relativePathColumn = cursor?.getColumnIndex(MediaStore.Video.VideoColumns.RELATIVE_PATH)
        val IdColumn = cursor?.getColumnIndex(MediaStore.Video.VideoColumns._ID)


        if(cursor!=null){
            while(cursor.moveToNext())
            {
                val title =    cursor.getString(titleColumn!!)
                val artist =   cursor.getString(artistColumn!!)
                val bucket =    cursor.getString(bucketColumn!!)
                val relativepath =    cursor.getString(relativePathColumn!!)
                val id =    cursor.getString(IdColumn!!)

                val appendedUri = ContentUris.withAppendedId(uri,id.toLong())


                arrayList?.add(MediaClasss(title,artist,bucket,relativepath,appendedUri.toString()))
            }
        }
        if(!arrayList?.isEmpty()!!)
        {
         //   val intent = Intent("SENDING_LIST").putParcelableArrayListExtra("ArrayLIST",arrayList)
         //   LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            /* val adapter = MyAdapter(requireContext(),arrayList!!)
             binding?.AllVideoRectyclerView?.layoutManager = LinearLayoutManager(requireContext())
             binding?.AllVideoRectyclerView?.adapter = adapter*/

            // arrayList?.removeAll(arrayList!!)
        }


    }

    override fun onResume() {
        super.onResume()
        if(!isShow)
        {
           // requestPermissions()
        }
        Log.d("r3tyrf73uf",isAllowed.toString())
    }






}