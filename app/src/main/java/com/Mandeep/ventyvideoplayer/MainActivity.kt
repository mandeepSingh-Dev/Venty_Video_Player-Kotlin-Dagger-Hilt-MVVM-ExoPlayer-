package com.Mandeep.ventyvideoplayer

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.Mandeep.ventyvideoplayer.Fragment.AllVideosFragment
import com.Mandeep.ventyvideoplayer.Fragment.VideoFoldersFragment
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   lateinit  var binding:ActivityMainBinding
    var isAllowed = false
    var isShow = false
    var arrayList:ArrayList<MediaClasss>?=null


    @Inject
    lateinit var allVideosFragment:AllVideosFragment
    @Inject
    lateinit var videoFoldersFragment:VideoFoldersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.hide()

        arrayList = ArrayList()
    }

    fun setUpViewPager(){
      /*  val adapter = MyViewPagerAdpater(this)
        binding.viewpagerr.adapter = adapter

        val texts= arrayOf("All Videos","Folders")
        TabLayoutMediator(binding.tablayout,binding.viewpagerr,object: TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                tab.text = texts[position]
            }
        }).attach()
        binding?.viewpagerr?.offscreenPageLimit=2*/
    }

    private fun requestPermissions(){

        val readPermission =   ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if(!readPermission)
        {
            isAllowed = false
            launcher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
          //  allVideosFragment.skdhs?.onSendBoolean(isAllowed)
            //  allVideosFragment.onSendArrayListListener(arrayList!!)

            //videoFoldersFragment.onSendBoolean(isAllowed)

        }
        else{
            isAllowed = true
            isShow = true
          //  allVideosFragment.skdhs?.onSendBoolean(isAllowed)
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
        val cursor = contentResolver.query(uri,null,null,null,null);

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

          //  allVideosFragment.onSendArrayListListener(arrayList!! )
          //  videoFoldersFragment.onSendArrayListListener(arrayList!!)

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
          //  requestPermissions()
        }

        Log.d("r3tyrf73uf",isAllowed.toString())
    }


}


