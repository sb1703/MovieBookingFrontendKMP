package screen.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import components.Tickets
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import domain.model.Ticket
import org.jetbrains.compose.ui.tooling.preview.Preview

val backgroundColor = Color(red = 0.180f, green = 0.070f, blue = 0.440f, alpha = 1.000f)
val backgroundColor2 = Color(red = 0.070f, green = 0.040f, blue = 0.170f, alpha = 1.000f)
val greenCircle = Color(red = 0.380f, green = 1.000f, blue = 0.790f, alpha = 0.400f)
val pinkCircle = Color(red = 1.000f, green = 0.330f, blue = 0.750f, alpha = 0.400f)

class TicketScreen(val userId: String): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val ticketViewModel = getScreenModel<TicketViewModel>()

        LaunchedEffect(key1 = Unit) {
            ticketViewModel.fetchTickets(userId)
        }

        val tickets by ticketViewModel.tickets.collectAsState()

        TicketContent(
            navigator = navigator,
            tickets = tickets,
            removeFirstAndAddToLast = ticketViewModel::removeFirstAndAddToLast,
            removeFirstTicket = ticketViewModel::removeFirstTicket
        )
    }
}

@Composable
fun TicketContent(
    navigator: Navigator?,
    tickets: List<Ticket>,
    removeFirstAndAddToLast: () -> Unit = {},
    removeFirstTicket: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, backgroundColor2)
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        CircleBackground(hazeState = hazeState, color = greenCircle, offsetX = -130, offSetY = -100)
//        CircleBackground(hazeState = hazeState, color = pinkCircle, offsetX = 130, offSetY = 100)
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 13.dp)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                modifier = Modifier,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mobile Ticket")
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.widthIn(max = 248.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Once you buy a movie ticket simply scan the barcode to access to your movie.")
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textAlign = TextAlign.Center
            )

            Tickets(
                tickets = tickets,
                removeFirstAndAddToLast = removeFirstAndAddToLast,
                removeFirstTicket = removeFirstTicket
            )
        }
    }
}

@Composable
fun CircleBackground(
    hazeState: HazeState,
    offsetX: Int,
    offSetY: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .offset(x = offsetX.dp, y = offSetY.dp)
            .size(300.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(1f), color.copy(.75f), color.copy(.50f), color.copy(.25f), color.copy(0f)),
                    radius = 400.dp.value
                )
            )
            .hazeChild(
                state = hazeState,
                shape = CircleShape,
            )
    )
}

@Preview
@Composable
fun TicketContentPreview() {
    TicketContent(
        navigator = null,
        tickets = emptyList()
    )
}