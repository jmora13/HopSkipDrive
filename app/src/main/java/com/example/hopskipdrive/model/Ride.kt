package com.example.hopskipdrive.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Entity

data class Ride(
    @JsonProperty("ends_at")
    val endsAt: String,
    @JsonProperty("estimated_earnings_cents")
    val estimatedEarningsCents: Int,
    @JsonProperty("estimated_ride_miles")
    val estimatedRideMiles: Double,
    @JsonProperty("estimated_ride_minutes")
    val estimatedRideMinutes: Int,
    @JsonProperty("in_series")
    val inSeries: Boolean,
    @JsonProperty("ordered_waypoints")
    val orderedWaypoints: List<OrderedWaypoint>,
    @PrimaryKey
    @JsonProperty("starts_at")
    val startsAt: String,
    @JsonProperty("trip_id")
    val tripId: Int
)