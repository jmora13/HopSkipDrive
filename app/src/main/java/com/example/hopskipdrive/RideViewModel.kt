package com.example.hopskipdrive

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.hopskipdrive.data.RideRepository

import com.example.hopskipdrive.model.MyRidesModel
import com.example.hopskipdrive.model.Ride
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RideViewModel @Inject constructor(private val repository: RideRepository): ViewModel() {
    //LIVE DATA FOR RIDE LIST
    val allRides: LiveData<List<Ride>> = repository.allRides.asLiveData()

    //CALL TO REPOSITORY FOR NETWORK CALL
    suspend fun getRides(): Response<MyRidesModel> {
        return repository.getRideResponse()
    }

    //GET RIDE FROM DATABASE USING STARTSAT TIME
    suspend fun getRide(startsAt: String) : Ride {
        return repository.getRide(startsAt)
    }

    //DELETE RIDE USING STARTSAT TIME
    suspend fun deleteRide(startsAt: String) {
        return repository.deleteRide(startsAt)
    }
}