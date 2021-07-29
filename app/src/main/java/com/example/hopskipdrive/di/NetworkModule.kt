package com.example.hopskipdrive.di

import com.example.hopskipdrive.api.RideService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    //RETROFIT INSTANCE SINGLETON
    @Provides
    @Singleton
    fun provideForecastService(): RideService {
        return Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/")
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(RideService::class.java)
    }

}