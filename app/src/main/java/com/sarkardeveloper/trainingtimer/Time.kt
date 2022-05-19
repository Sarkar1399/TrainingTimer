package com.sarkardeveloper.trainingtimer

import java.io.Serializable

data class Time(
    var hours: String = "0",
    var minute: String = "0",
    val seconds: String = "0"
) : Serializable
