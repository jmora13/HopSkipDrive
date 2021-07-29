package com.example.hopskipdrive.data

import com.example.hopskipdrive.api.RideService
import com.example.hopskipdrive.model.MyRidesModel
import com.example.hopskipdrive.model.Ride
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RideRepository @Inject constructor(private val rideService: RideService, private val rideDao: RideDao) {
    val allRides: Flow<List<Ride>> = rideDao.getAllRides()

    //INSERT RIDE ITEM
    suspend fun insert(ride: Ride){
        rideDao.insert(ride)
    }
    //DELETE RIDE ITEM
    suspend fun deleteRide(startsAt: String){
        rideDao.deleteRide(startsAt)
    }

    //RETURNS RIDE ITEM
    suspend fun getRide(startsAt: String): Ride{
        return rideDao.getRide(startsAt)
    }

    //NETWORK RESPONSE TO GET RIDE RESPONSE
    suspend fun getRideResponse(): Response<MyRidesModel> {
        val response = rideService.getRides()

        //INSERT RIDES TO DATABASE
        for(i in response.body()!!.rides.indices){
            insert(response.body()!!.rides[i])
        }
        return response
    }
}