package com.example.hopskipdrive.data

import androidx.room.*
import com.example.hopskipdrive.model.Ride
import kotlinx.coroutines.flow.Flow

@Dao
interface RideDao {

    @Query("SELECT * FROM ride ORDER BY startsAt")
    fun getAllRides(): Flow<List<Ride>>

    @Query("SELECT * FROM ride WHERE startsAt =:startsAt ")
    fun getRide(startsAt: String): Ride

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ride: Ride)

    @Query("DELETE FROM ride")
    suspend fun deleteAll()

    @Query("DELETE FROM ride WHERE startsAt =:startsAt")
    suspend fun deleteRide(startsAt: String)
}