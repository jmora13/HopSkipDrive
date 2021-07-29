package com.example.hopskipdrive.di

import android.content.Context
import com.example.hopskipdrive.data.RideDao
import com.example.hopskipdrive.data.RideDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context?): RideDatabase {
        return RideDatabase.getDatabase(context!!)
    }

    @Provides
    @Singleton
    fun provideRideDao(rideDatabase: RideDatabase): RideDao {
        return rideDatabase.rideDao()
    }
}