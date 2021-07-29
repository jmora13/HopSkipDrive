package com.example.hopskipdrive.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hopskipdrive.converters.Converters
import com.example.hopskipdrive.model.Location
import com.example.hopskipdrive.model.OrderedWaypoint
import com.example.hopskipdrive.model.Passenger
import com.example.hopskipdrive.model.Ride

@Database(entities = arrayOf(
    Ride::class,
    Passenger::class,
    OrderedWaypoint::class,
    Location::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RideDatabase : RoomDatabase() {

    abstract fun rideDao(): RideDao

    companion object {
        @Volatile
        private var INSTANCE: RideDatabase? = null

        fun getDatabase(context: Context): RideDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RideDatabase::class.java,
                    "ride_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}