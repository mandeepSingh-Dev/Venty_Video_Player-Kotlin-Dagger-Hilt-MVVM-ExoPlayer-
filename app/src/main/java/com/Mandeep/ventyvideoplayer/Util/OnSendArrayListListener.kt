package com.Mandeep.ventyvideoplayer.Util

import android.content.Context
import com.Mandeep.ventyvideoplayer.MVVM.MediaClasss
import com.Mandeep.ventyvideoplayer.MainActivity

interface OnSendArrayListListener {

    fun onSendArrayListListener(arrayList: ArrayList<MediaClasss>)

    fun onSendBoolean(isAllowed:Boolean)
}