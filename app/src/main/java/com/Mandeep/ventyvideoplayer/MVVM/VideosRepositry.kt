package com.Mandeep.ventyvideoplayer.MVVM

import android.content.Context
import com.Mandeep.ventyvideoplayer.Util.FetchVideos
import javax.inject.Inject

class VideosRepositry @Inject constructor(val fetchVideos: FetchVideos)
{
   suspend fun fetchAllVidoes(context: Context):ArrayList<MediaClasss> = fetchVideos.fetchAllVideos(context)
    fun gettingFolderFromArayList(arrayList: ArrayList<MediaClasss>):ArrayList<MediaClasss> = fetchVideos.gettingFolderFromArayList(arrayList)
    fun fetchBucketVideos(context: Context,bucket:String):ArrayList<MediaClasss> = fetchVideos.fetchBucketVideos(context,bucket)
}