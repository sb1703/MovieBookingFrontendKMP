package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val _id: Int,
    val movieId: String,
    val movieTitle: String,
    val image: String,
    val barcode: String,
    val date: String,
    val time: String,
    val seats: List<String>
)
