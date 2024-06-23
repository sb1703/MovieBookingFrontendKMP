package domain.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed class BookingEvent {

    @Serializable
    @SerialName("updateSelectedSeats")
    data class UpdateSelectedSeats(
        val userId: String,
        val movieId: String,
        val date: String,
        val time: String,
        val audi: String,
        val seats: List<String>
    ) : BookingEvent()

    @Serializable
    @SerialName("clearoutSeats")
    data class ClearOutSeats(
        val movieId: String,
        val date: String,
        val time: String,
        val audi: String,
        val seats: List<String>
    ) : BookingEvent()

    @Serializable
    @SerialName("paidSeats")
    data class PaidSeats(
        val movieId: String,
        val date: String,
        val time: String,
        val audi: String,
        val seats: List<String>
    ) : BookingEvent()

}