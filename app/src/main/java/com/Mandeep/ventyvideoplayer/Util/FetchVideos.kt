package com.Mandeep.ventyvideoplayer.Util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.media.MediaDataSource
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.google.android.exoplayer2.MetadataRetriever
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.OutputStream
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.Path
import kotlin.io.path.name

class FetchVideos @Inject constructor() {


    lateinit var arrayList:ArrayList<MediaClasss>
    @SuppressLint("Range", "Recycle")
    fun fetchAllVideos(context: Context):ArrayList<MediaClasss>{
        arrayList = ArrayList()
        val uri = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val cursor = context.contentResolver.query(uri,null,null,null,null);

        cursor?.let {
        val titleColumn =   it.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
        val artistColumn =it.getColumnIndex(MediaStore.Video.VideoColumns.ARTIST)
        val bucketColumn =it.getColumnIndex(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME)
        val relativepathColumn =it.getColumnIndex(MediaStore.Video.VideoColumns.RELATIVE_PATH)


            val IdColumn = it.getColumnIndex(MediaStore.Video.VideoColumns._ID)

            while(it.moveToNext())
            {
                val title =    it.getString(titleColumn)
                val artist =   it.getString(artistColumn)
                val bucket =   it.getString(bucketColumn)
                val relativePath = it.getString(relativepathColumn)
                val id =    it.getString(IdColumn)


                val appendedUri = ContentUris.withAppendedId(uri,id.toLong())
                arrayList.add(MediaClasss(title,artist,bucket,relativePath,appendedUri.toString()))
            }
        }
        return  arrayList
    }

    fun gettingFolderFromArayList(arrayList: ArrayList<MediaClasss>):ArrayList<MediaClasss>{
        var arrayListy = ArrayList<String>()
        var arrayListttt = ArrayList<MediaClasss>()

        for(i in 0 until arrayList.size)
        {
            if(!arrayListy.contains(arrayList.get(i).bucket))
            {
                arrayListy.add(arrayList.get(i).bucket)
                arrayListttt.add(MediaClasss(null,null,arrayList.get(i).bucket,arrayList.get(i).relativePath,null))

            }
        }
        return  arrayListttt
    }

    @SuppressLint("Range")
    fun fetchBucketVideos(context: Context, bucket:String):ArrayList<MediaClasss>
    {
        var buckVideoList = ArrayList<MediaClasss>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME+ " LIKE?"
        val selectionArgs = arrayOf("%$bucket%")
        val cursor = context.contentResolver.query(uri,null,selection,selectionArgs,null,null)
            cursor?.let {
                val titleColumn = it.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
                val artistColumn =it.getColumnIndex(MediaStore.Video.VideoColumns.ARTIST)
                val bucketColumn = it.getColumnIndex(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME)
                val relativepathColumn =it.getColumnIndex(MediaStore.Video.VideoColumns.RELATIVE_PATH)
                val IdColumn = it.getColumnIndex(MediaStore.Video.VideoColumns._ID)

                while(it.moveToNext())
                {
                    val title =    cursor.getString(titleColumn)
                    val artist =   cursor.getString(artistColumn)
                    val bucket =   cursor.getString(bucketColumn)
                    val relativePath =   cursor.getString(relativepathColumn)
                    val id =    cursor.getString(IdColumn)

                    val appendedUri = ContentUris.withAppendedId(uri,id.toLong())
                    buckVideoList.add(MediaClasss(title,artist,bucket,relativePath,appendedUri.toString()))
                }
            }
        return buckVideoList
    }

}