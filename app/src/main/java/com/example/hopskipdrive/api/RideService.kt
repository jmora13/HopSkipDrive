package com.example.hopskipdrive.api

import com.example.hopskipdrive.model.MyRidesModel
import retrofit2.Response
import retrofit2.http.GET

interface RideService {

    @GET("hsd-interview-resources/simplified_my_rides_response.json")
    suspend fun getRides(): Response<MyRidesModel>
}