package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean = true,
    val message: String = "",
    val user: User = User(),
    val tickets: List<Ticket> = emptyList(),
    val watchLater: List<String> = emptyList(),
    val movies: List<MovieObject> = emptyList(),
    val movie: Movie? = null,
    val dates: List<String> = emptyList(),
    val times: List<String> = emptyList(),
    val audis: List<Audi> = emptyList()
)
