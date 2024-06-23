package screen.audis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import components.ReservationButton
import domain.model.Audi
import domain.model.Seat
import moviebooking.composeapp.generated.resources.Res
import moviebooking.composeapp.generated.resources.frontseat
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.ticket.backgroundColor
import screen.ticket.backgroundColor2

class AudiScreen(val date: String, val time: String, val movieId: String, val userId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val audisViewModel = getScreenModel<AudisViewModel>()

        LaunchedEffect(key1 = Unit) {
            audisViewModel.getAudis(movieId = movieId, date = date, time = time)
            audisViewModel.setInit(date = date, time = time, movieId = movieId, userId = userId)
        }

        val audi by audisViewModel.audi.collectAsState()
        val selectedAudi by audisViewModel.selectedAudi.collectAsState()
        val selectedSeats by audisViewModel.selectedSeats.collectAsState()

        AudiContent(
            navigator = navigator,
            audi = audi,
            selectedAudi = selectedAudi,
            selectedSeat = selectedSeats,
            setSelectedAudi = audisViewModel::setSelectedAudi,
            clearSelectedSeats = audisViewModel::clearSelectedSeats,
            setSelectedSeats = audisViewModel::setSelectedSeats,
            updateSelectedSeatsSend = audisViewModel::updateSelectedSeatsSend,
            clearoutSeatsSend = audisViewModel::clearoutSeatsSend
        )
    }


}

@Composable
fun AudiContent(
    navigator: Navigator?,
    audi: List<Audi>,
    selectedAudi: Int,
    selectedSeat: List<String>,
    setSelectedAudi: (Int) -> Unit,
    clearSelectedSeats: () -> Unit,
    setSelectedSeats: (List<String>) -> Unit,
    updateSelectedSeatsSend: () -> Unit,
    clearoutSeatsSend: () -> Unit
) {

//    var selectedAudi by remember { mutableStateOf(0) }
//
//    val selectedSeat = remember {
//        mutableStateListOf<String>()
//    }

    LaunchedEffect(key1 = selectedAudi) {
//        selectedSeat.clear()
        clearSelectedSeats()
    }

    if(audi.isEmpty()) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.White, fontSize = MaterialTheme.typography.headlineMedium.fontSize)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(backgroundColor, backgroundColor2)
            )),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    clearoutSeatsSend()
                    navigator?.pop()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow Back Icon",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("Choose Seats")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(150.dp),
                    painter = painterResource(Res.drawable.frontseat),
                    contentDescription = "Front Seat Image",
                )
                SeatsContent(
                    audi = audi,
                    selectedAudi = selectedAudi,
//                    seats = audi[selectedAudi].Seats,
                    selectedSeat = selectedSeat,
                    setSelectedSeats = setSelectedSeats,
                    updateSelectedSeatsSend = updateSelectedSeatsSend
                )
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SeatComp(isEnabled = false)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("Reserved")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    SeatComp(isEnabled = true, isSelected = true)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("Selected")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    SeatComp(isEnabled = true, isSelected = false)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("Available")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(125.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
//                            selectedAudi = if(selectedAudi > 0) selectedAudi - 1 else audi.size - 1
                            clearoutSeatsSend()
                            setSelectedAudi(if(selectedAudi > 0) selectedAudi - 1 else audi.size - 1)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Audi ${audi[selectedAudi].Number}")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        onClick = {
//                            selectedAudi = if(selectedAudi < audi.size - 1) selectedAudi + 1 else 0
                            clearoutSeatsSend()
                            setSelectedAudi(if(selectedAudi < audi.size - 1) selectedAudi + 1 else 0)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Forward",
                            tint = Color.White
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("Total Price")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("\$${selectedSeat.size * 10}")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                ReservationButton(
                    width = 70.dp,
                    height = 50.dp,
                    text = "Continue",
                    onClickReservation = {  }
                )
            }
        }
    }
}

@Preview
@Composable
fun SeatsContentPreview() {
    AudiContent(
        navigator = null,
        audi = emptyList(),
        selectedAudi = 0,
        selectedSeat = mutableStateListOf(),
        setSelectedAudi = {},
        clearSelectedSeats = {},
        setSelectedSeats = {},
        updateSelectedSeatsSend = {},
        clearoutSeatsSend = {}
    )
}

@Composable
fun SeatsContent(
    audi: List<Audi>,
    selectedAudi: Int,
//    seats: List<Seat>,
    selectedSeat: List<String>,
    setSelectedSeats: (List<String>) -> Unit = { },
    updateSelectedSeatsSend: () -> Unit = { }
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        audi[selectedAudi].Seats.windowed(8, 8, true).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                row.forEachIndexed { index, seat ->
                    SeatComp(
                        isEnabled = if(seat.Status == "Reserved") false else true,
                        isSelected = selectedSeat.contains(seat.Number),
                        seatNumber = seat.Number
                    ) { selected, seatNumber ->
                        if(seat.Status != "Reserved") {
                            if (selected) {
                                val newSelectedSeat = selectedSeat.toMutableList().apply {
                                    remove(seatNumber)
                                }
                                setSelectedSeats(newSelectedSeat)
                                updateSelectedSeatsSend()
//                                selectedSeat.remove(seatNumber)
                            } else {
                                val newSelectedSeat = selectedSeat.toMutableList().apply {
                                    add(seatNumber)
                                }
                                setSelectedSeats(newSelectedSeat)
                                updateSelectedSeatsSend()
//                                selectedSeat.add(seatNumber)
                            }
                        }
                    }
                    if (index+1 != 8) Spacer(modifier = Modifier.width(if (index+1 == 4) 16.dp else 8.dp))
                }
            }
        }
    }
}

@Composable
fun SeatComp(
    isEnabled: Boolean = false,
    isSelected: Boolean = false,
    seatNumber: String = "",
    onClick: (Boolean, String) -> Unit = { _, _ -> },
) {
    val seatColor = when {
        !isEnabled -> Color.Gray
        isSelected -> Yellow
        else -> Color.White
    }

    val textColor = when {
        isSelected -> Color.White
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .size(32.dp)
            .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(color = seatColor)
            .clickable {
                onClick(isSelected, seatNumber);
            }
            .padding(4.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append(seatNumber)
                }
            },
            color = if(!isEnabled) Color.Black else textColor,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
        )
    }
}