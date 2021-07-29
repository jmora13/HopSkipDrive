package com.example.hopskipdrive.model



import com.fasterxml.jackson.annotation.JsonProperty

data class MyRidesModel(
    @JsonProperty("rides")
    val rides: List<Ride>
)