package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val _id: String = "",
    val name: String = "",
    val emailAddress: String = "",
    val profilePhoto: String = "",
    val moviesWatchLater: List<String> = emptyList(),
    val tickets: List<Ticket> = emptyList()
)
