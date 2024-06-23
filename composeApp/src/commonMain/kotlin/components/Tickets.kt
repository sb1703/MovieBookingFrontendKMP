package components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.model.Ticket

@Composable
fun Tickets(
    tickets: List<Ticket>,
    removeFirstAndAddToLast: () -> Unit = {},
    removeFirstTicket: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        propagateMinConstraints = true
    ) {
        tickets.forEachIndexed { index, ticket ->
            Ticket(
                width = this.maxWidth,
                height = this.maxHeight,
                index = index,
                tickets = tickets,
                id = ticket._id,
                title = ticket.movieTitle,
                image = ticket.image,
                barcode = ticket.barcode,
                date = ticket.date,
                time = ticket.time,
                seats = ticket.seats,
                removeFirstAndAddToLast = removeFirstAndAddToLast,
                removeFirstTicket = removeFirstTicket
            )
        }
    }
}