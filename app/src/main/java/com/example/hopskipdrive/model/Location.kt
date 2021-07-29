package com.example.hopskipdrive.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
@Entity
data class Location(
    @PrimaryKey
    @JsonProperty("address")
    val address: String,
    @JsonProperty("lat")
    val lat: Double,
    @JsonProperty("lng")
    val lng: Double
) {
    constructor() : this("",0.0,0.0)
}