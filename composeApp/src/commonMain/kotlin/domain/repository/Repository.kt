package domain.repository

import domain.model.ApiResponse

interface Repository {

    suspend fun userTokenVerification(tokenId: String): ApiResponse

    suspend fun userLogout(): ApiResponse

    suspend fun userVerifySession(): ApiResponse

    suspend fun getUser(userId: String): ApiResponse

    suspend fun getUserByMail(emailAddress: String): ApiResponse

    suspend fun getTickets(userId: String): ApiResponse

    suspend fun getWatchLater(userId: String): ApiResponse

    suspend fun addWatchLater(userId: String, movieId: String): ApiResponse

    suspend fun removeWatchLater(userId: String, movieId: String): ApiResponse

    suspend fun addTicket(userId: String, movieId: String, date: String, time: String, seats: List<String>): ApiResponse

    suspend fun removeTicket(userId: String, ticketId: String): ApiResponse

    suspend fun getMovies(): ApiResponse

    suspend fun getMovie(movieId: String): ApiResponse

    suspend fun getDates(movieId: String): ApiResponse

    suspend fun getTimes(movieId: String, date: String): ApiResponse

    suspend fun getAudis(movieId: String, date: String, time: String): ApiResponse

    suspend fun getSeats(movieId: String, date: String, time: String, audi: String): ApiResponse

}