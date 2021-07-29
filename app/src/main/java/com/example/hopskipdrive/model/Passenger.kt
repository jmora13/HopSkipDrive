package com.example.hopskipdrive.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
@Entity
data class Passenger(
    @JsonProperty("booster_seat")
    val boosterSeat: Boolean,
    @JsonProperty("first_name")
    val firstName: String,
    @PrimaryKey
    @JsonProperty("id")
    val id: Int
)