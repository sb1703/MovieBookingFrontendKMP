package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieObject(
    val id: String,
    val url: String,
    val title: String,
)
