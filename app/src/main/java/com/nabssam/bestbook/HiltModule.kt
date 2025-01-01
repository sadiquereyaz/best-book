package com.nabssam.bestbook

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideCoffees():Coffee{
        return Coffee()
    }
}

class Coffee{
    fun makeCoffee(){
        Log.d("Coffee", "Making")
    }
}