package com.example.hopskipdrive.converters

import androidx.room.TypeConverter
import com.example.hopskipdrive.model.Location
import com.example.hopskipdrive.model.OrderedWaypoint
import com.example.hopskipdrive.model.Passenger
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    //TYPE CONVERTER FOR COMPLEX TYPES
    @TypeConverter
    fun restoreWaypointList(waypointList: String?): List<OrderedWaypoint?>? {
        return Gson().fromJson(waypointList, object : TypeToken<List<OrderedWaypoint?>?>() {}.type)
    }

    @TypeConverter
    fun saveWaypointListOfString(waypointList: List<OrderedWaypoint?>?): String? {
        return Gson().toJson(waypointList)
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return if (location == null) null else ""
    }

    @TypeConverter
    fun locationFromString(s: String?): Location? {
        return if (s == null) null else Location()
    }


    @TypeConverter
    fun restorePassengerList(passengerList: String?): List<Passenger?>? {
        return Gson().fromJson(passengerList, object : TypeToken<List<Passenger?>?>() {}.type)
    }

    @TypeConverter
    fun savePassengerListOfString(passengerList: List<Passenger?>?): String? {
        return Gson().toJson(passengerList)
    }

}