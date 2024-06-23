package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val _id: String,
    val Title: String,
    val Year: String,
    val Released: String = "",
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Poster: String = "",
    val imdbRating: String,
    val imdbId: String = "",
    val Type: String = "",
    val Videos: List<String> = emptyList(),
    val Images: List<String> = emptyList()
)
