package data.repository

import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.repository.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import utils.Constants.BASE_URL

class RepositoryImpl(
    private val httpClient: HttpClient
): Repository {
    override suspend fun userTokenVerification(tokenId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/userTokenVerification") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(tokenId = tokenId))
        }
        return response.body()
    }

    override suspend fun userLogout(): ApiResponse {
        val response: HttpResponse = httpClient.get("$BASE_URL/userLogout")
        return response.body()
    }

    override suspend fun userVerifySession(): ApiResponse {
        val response: HttpResponse = httpClient.get("$BASE_URL/userVerifySession")
        return response.body()
    }

    override suspend fun getUser(userId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/getUser") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId))
        }
        return response.body()
    }

    override suspend fun getUserByMail(emailAddress: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/getUserByMail") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(emailAddress = emailAddress))
        }
        return response.body()
    }

    override suspend fun getTickets(userId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/getTickets") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId))
        }
        return response.body()
    }

    override suspend fun getWatchLater(userId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/getWatchLater") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId))
        }
        return response.body()
    }

    override suspend fun addWatchLater(userId: String, movieId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/addWatchLater") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId, movieId = movieId))
        }
        return response.body()
    }

    override suspend fun removeWatchLater(userId: String, movieId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/removeWatchLater") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId, movieId = movieId))
        }
        return response.body()
    }

    override suspend fun addTicket(
        userId: String,
        movieId: String,
        date: String,
        time: String,
        seats: List<String>
    ): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/addTicket") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId, movieId = movieId, date = date, time = time, seats = seats.joinToString(",")))
        }
        return response.body()
    }

    override suspend fun removeTicket(userId: String, ticketId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/removeTicket") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(userId = userId, ticketId = ticketId))
        }
        return response.body()
    }

    override suspend fun getMovies(): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/movieDBAll") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest())
        }
        return response.body()
    }

    override suspend fun getMovie(movieId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/users/movieDBDetails") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(movieId = movieId))
        }
        println(response.body() as ApiResponse)
        return response.body()
    }

    override suspend fun getDates(movieId: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/audis/getDates") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(movieId = movieId))
        }
        return response.body()
    }

    override suspend fun getTimes(movieId: String, date: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/audis/getTimes") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(movieId = movieId, date = date))
        }
        return response.body()
    }

    override suspend fun getAudis(movieId: String, date: String, time: String): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/audis/getAudis") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(movieId = movieId, date = date, time = time))
        }
        println(response.body() as ApiResponse)
        return response.body()
    }

    override suspend fun getSeats(
        movieId: String,
        date: String,
        time: String,
        audi: String
    ): ApiResponse {
        val response: HttpResponse = httpClient.post("$BASE_URL/audis/getSeats") {
            contentType(ContentType.Application.Json)
            setBody(ApiRequest(movieId = movieId, date = date, time = time, audi = audi))
        }
        return response.body()
    }


}