package com.Mandeep.ventyvideoplayer.MVVM

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel*/
class MyViewmodel @AssistedInject constructor(val videosRepositry: VideosRepositry, val context:Context, @Assisted val buckets:String):ViewModel(){

     private val videosList:MutableLiveData<ArrayList<MediaClasss>>
     private val bucketsList:MutableLiveData<ArrayList<MediaClasss>>
      private val folderVideosList:MutableLiveData<ArrayList<MediaClasss>>


    init {
        videosList = MutableLiveData()
        bucketsList = MutableLiveData()
        folderVideosList=  MutableLiveData()

        Log.d("8ry38",buckets+"s")
        viewModelScope.launch {
          //  videosList = MutableLiveData()
            videosList.value = videosRepositry.fetchAllVidoes(context)
            videosList.value?.let {
                bucketsList.value = videosRepositry.gettingFolderFromArayList(it)
            }
            folderVideosList.value = videosRepositry.fetchBucketVideos(context,buckets)
        }
    }


    fun getVideosList():LiveData<ArrayList<MediaClasss>>{
        return videosList
    }
    fun getBucketsList():LiveData<ArrayList<MediaClasss>>{
        return bucketsList
    }
    fun getFolderVideos():LiveData<ArrayList<MediaClasss>>{
        return folderVideosList
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("skdnsjkd","CLEARED")
    }
}