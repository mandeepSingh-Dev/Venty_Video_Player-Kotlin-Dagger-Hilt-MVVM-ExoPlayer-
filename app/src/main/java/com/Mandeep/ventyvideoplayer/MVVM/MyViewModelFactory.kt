package com.Mandeep.ventyvideoplayer.MVVM

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

// No use of this factory as of now..............
class MyViewModelFactory @Inject constructor(val assistedFactory: MyViewModelAssistedFactory, val buckets:String) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(buckets) as T
    }
}