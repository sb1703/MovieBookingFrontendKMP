package screen.ticket

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.RepositoryImpl
import domain.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketViewModel(
    private val repository: RepositoryImpl
): ScreenModel {

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets = _tickets.asStateFlow()

    suspend fun fetchTickets(userId: String) {
        _tickets.value = repository.getTickets(userId).tickets
    }

    fun removeFirstAndAddToLast() {
        val first = _tickets.value.first()
        _tickets.value = _tickets.value.drop(1) + first
    }

    fun removeFirstTicket() {
        _tickets.value = _tickets.value.drop(1)
    }

}