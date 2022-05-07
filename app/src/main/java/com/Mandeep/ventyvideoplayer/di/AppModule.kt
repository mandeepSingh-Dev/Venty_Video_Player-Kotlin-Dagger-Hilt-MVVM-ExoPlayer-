package com.Mandeep.ventyvideoplayer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope


@Module
@InstallIn(SingletonComponent::class)
 class AppModule {

   /*lateinit var context:Context
   @Provides
    fun provideContext(@ApplicationContext context:Context):Context{
        this.context = context
      return context
    }*/
}