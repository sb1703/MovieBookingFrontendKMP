package screen.audis

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.repository.RepositoryImpl
import domain.model.Audi
import domain.model.BookingEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import webSocket.AppSocket
import webSocket.ConnectionState

class AudisViewModel(
    private val repository: RepositoryImpl,
    private val appSocket: AppSocket
): ScreenModel {

    private val _audi = MutableStateFlow(listOf(Audi(Number = 1, Seats = emptyList())))
    val audi = _audi.asStateFlow()

    private val _selectedAudi = MutableStateFlow(0)
    val selectedAudi = _selectedAudi.asStateFlow()

    private val _selectedSeats = MutableStateFlow(emptyList<String>())
    val selectedSeats = _selectedSeats.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _movieId = MutableStateFlow("")
    val movieId = _movieId.asStateFlow()

    private val _userId = MutableStateFlow("")
    val userId = _userId.asStateFlow()

    val json = Json {
        serializersModule = SerializersModule {
            polymorphic(BookingEvent::class) {
                subclass(BookingEvent.UpdateSelectedSeats::class)
                subclass(BookingEvent.ClearOutSeats::class)
            }
        }
        classDiscriminator = "event"
    }

    init {
        appSocket.stateListener = { state ->
            println("state received: $state")
            if (state == ConnectionState.CONNECTED) {
                appSocket.messageListener = {
                    println("Message received from appSocket.messageListener: $it")
                    when (val event = json.decodeFromString(BookingEvent.serializer(), it)) {
                        is BookingEvent.UpdateSelectedSeats -> {
                            println("Event: $event")
                            println("movieId: ${movieId.value}, date: ${date.value}, time: ${time.value}, audi: ${audi.value[selectedAudi.value].Number}")
//                                    && event.audi == audi.value[selectedAudi.value].Number.toString()
                            if(event.movieId == movieId.value && event.date == date.value && event.time == time.value) {
                                println("updateSelectedSeats: same movieId, date, time, audi")
                                _audi.value = _audi.value.map {
                                    if(it.Number.toString() == event.audi) {
                                        Audi(Number = it.Number, Seats = it.Seats.map { seat ->
                                            if(event.seats.contains(seat.Number)) {
                                                println("updated seat: ${seat.Number}")
                                                seat.copy(Status = "Reserved", UserId = event.userId)
                                            } else {
                                                if(seat.PaidStatus == "" && seat.UserId == event.userId) {
                                                    seat.copy(Status = "Available", UserId = "")
                                                } else {
                                                    seat
                                                }
                                            }
                                        })
                                    } else {
                                        it
                                    }
                                }
                                println("updated audi: ${_audi.value}")
                            }
                        }
                        is BookingEvent.ClearOutSeats -> {
                            println("Event: $event")
//                                    && event.audi == audi.value[selectedAudi.value].Number.toString()
                            if(event.movieId == movieId.value && event.date == date.value && event.time == time.value) {
                                _audi.value = _audi.value.map {
                                    if(it.Number.toString() == event.audi) {
                                        Audi(Number = it.Number, Seats = it.Seats.map { seat ->
                                            if(event.seats.contains(seat.Number)) {
                                                seat.copy(Status = "Available", UserId = "")
                                            } else {
                                                seat
                                            }
                                        })
                                    } else {
                                        it
                                    }
                                }
                            }
                        }

                        is BookingEvent.PaidSeats -> {

                        }
                    }
                }
            } else if (state == ConnectionState.CLOSED) {
                println("WebSocket closed abruptly or due to an error")
                appSocket.socketError?.let {
                    println("Specific error: ${it.message}")
                    println("Error details: ${it.toString()}")
//                    it.stackTrace.forEach { println(it.toString()) }
                }
            } else if (state == ConnectionState.CLOSED_NORMAL) {
                println("WebSocket closed normally")
            } else if (state == ConnectionState.CONNECTING) {
                println("WebSocket connecting")
            } else if (state == ConnectionState.READY) {
                println("WebSocket ready")
            }
        }
        appSocket.connect()
    }

//    init {
//        appSocket.connect()
//        if(appSocket.currentState == ConnectionState.CONNECTED) {
//            appSocket.messageListener = {
//                println("Message received: $it")
//                when (val event = json.decodeFromString(BookingEvent.serializer(), it)) {
//                    is BookingEvent.UpdateSelectedSeats -> {
//
//                    }
//                    is BookingEvent.ClearOutSeats -> {
//
//                    }
//                }
//            }
//        }
//    }

    fun setInit(date: String, time: String, movieId: String, userId: String) {
        _date.value = date
        _time.value = time
        _movieId.value = movieId
        _userId.value = userId
    }

    fun setSelectedAudi(audi: Int) {
        _selectedAudi.value = audi
    }

    fun setSelectedSeats(seats: List<String>) {
        _selectedSeats.value = seats
    }

    fun clearSelectedSeats() {
        _selectedSeats.value = emptyList()
    }

    fun updateSelectedSeatsSend() {
        val msg = BookingEvent.UpdateSelectedSeats(
            userId = userId.value,
            movieId = movieId.value,
            date = date.value,
            time = time.value,
            audi = audi.value[selectedAudi.value].Number.toString(),
            seats = selectedSeats.value
        )
        val jsonText = json.encodeToString(BookingEvent.serializer(), msg)
        appSocket.send(jsonText)
    }

    fun clearoutSeatsSend() {
        val msg = BookingEvent.ClearOutSeats(
            movieId = movieId.value,
            date = date.value,
            time = time.value,
            audi = audi.value[selectedAudi.value].Number.toString(),
            seats = selectedSeats.value
        )
        val jsonText = json.encodeToString(BookingEvent.serializer(), msg)
        appSocket.send(jsonText)
    }

    fun getAudis(movieId: String, date: String, time: String) {
        screenModelScope.launch(Dispatchers.IO) {
            println("${repository.getAudis(movieId, date, time)}")
            _audi.value = repository.getAudis(movieId, date, time).audis
//            println("AUDI: ${_audi.value}")
        }
    }

    override fun onDispose() {
        println("AudisViewModel onDispose")
        clearoutSeatsSend()
        appSocket.disconnect()
        super.onDispose()
    }

}