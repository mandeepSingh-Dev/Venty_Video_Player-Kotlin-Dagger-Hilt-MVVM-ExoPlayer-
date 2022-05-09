package com.Mandeep.ventyvideoplayer.MVVM

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MediaClasss(
    var artistName: String?,
    var songName:String?,
    val bucket:String,
    val relativePath:String,
    var link:String?,
    var baos: ByteArray?
)  :Serializable {
    constructor(artistName: String?,
                songName:String?,
                bucket:String,
                relativePath:String,
                link: String?,
                  ):this(artistName,songName,bucket,relativePath,link,null)
    }

