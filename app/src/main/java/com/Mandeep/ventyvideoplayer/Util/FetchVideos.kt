package com.Mandeep.ventyvideoplayer.Util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.BitmapCompat
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FetchVideos @Inject constructor() {


    lateinit var arrayList:ArrayList<MediaClasss>
    @SuppressLint("Range", "Recycle")
   suspend fun fetchAllVideos(context: Context):ArrayList<MediaClasss> = withContext(Dispatchers.IO){
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

                try {
                    var bitmap: Bitmap? = null
                    var futureTarget = Glide.with(context).asBitmap().load(appendedUri).submit(100, 100)
                    bitmap = futureTarget.get()

                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos)
                    val byteArray = baos.toByteArray()

                    arrayList.add(MediaClasss(artist, title, bucket, relativePath, appendedUri.toString(), byteArray))

                }catch (e:Exception){
                    arrayList.add(MediaClasss(artist, title, bucket, relativePath, appendedUri.toString()))
                }
              //  arrayList.add(MediaClasss(artist,title,bucket,relativePath,appendedUri.toString()))

            }
        }
        return@withContext arrayList
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

                    try {
                        var bitmap: Bitmap? = null
                        var futureTarget = Glide.with(context).asBitmap().load(appendedUri).submit(100, 100)
                        bitmap = futureTarget.get()

                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos)
                        val byteArray = baos.toByteArray()

                        buckVideoList.add(MediaClasss(artist, title, bucket, relativePath, appendedUri.toString(), byteArray))

                    }catch (e:Exception){
                        buckVideoList.add(MediaClasss(artist, title, bucket, relativePath, appendedUri.toString()))
                    }

                   // buckVideoList.add(MediaClasss(title,artist,bucket,relativePath,appendedUri.toString()))
                }
            }
        return buckVideoList
    }

}