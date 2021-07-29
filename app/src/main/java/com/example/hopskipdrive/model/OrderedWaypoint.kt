package com.example.hopskipdrive.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty


@Entity

data class OrderedWaypoint(
    @JsonProperty("anchor")
    val anchor: Boolean,
    @JsonProperty("id")
    @PrimaryKey
    val id: Int,
    @JsonProperty("location")
    val location: Location,
    @JsonProperty("passengers")
    val passengers: List<Passenger>
)