package com.Mandeep.ventyvideoplayer.MVVM

import dagger.assisted.AssistedFactory

@AssistedFactory
interface MyViewModelAssistedFactory {
    fun create(buckets:String) : MyViewmodel
}