package com.example.hopskipdrive.di

import com.example.hopskipdrive.data.RideDao
import com.example.hopskipdrive.data.RideRepository
import com.example.hopskipdrive.api.RideService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    //REPOSITORY SINGLETON
    @Provides
    @ViewModelScoped
    fun providesRepo(rideService: RideService, rideDao: RideDao): RideRepository {
        return RideRepository(rideService, rideDao)
    }


}