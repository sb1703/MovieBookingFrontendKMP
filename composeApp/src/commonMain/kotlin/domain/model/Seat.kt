package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    val Number: String,
    val Status: String,
    val UserId: String = "",
    val PaidStatus: String = "",
    val _id: String = ""
)
