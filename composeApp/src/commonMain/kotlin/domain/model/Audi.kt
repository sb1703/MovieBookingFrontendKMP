package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Audi(
    val Number: Int,
    val Seats: List<Seat>,
    val _id: String = ""
)
