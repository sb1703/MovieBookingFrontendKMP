package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val tokenId: String = "",
    val userId: String = "",
    val movieId: String = "",
    val ticketId: String = "",
    val date: String = "",
    val time: String = "",
    val audi: String = "",
    val seats: String = "",
    val query: String = "",
    val emailAddress: String = "",
    val barcode: String = "",
)
