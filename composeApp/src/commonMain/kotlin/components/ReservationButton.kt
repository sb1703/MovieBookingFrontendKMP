package components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import screen.ticket.backgroundColor

@Composable
fun ReservationButton(
    width: Dp = 90.dp,
    height: Dp = 50.dp,
    text: String = "Reservation",
    onClickReservation: () -> Unit
) {

    val selectedBorderColors = listOf(Color.Magenta, Color.Magenta.copy(alpha = 0f), Color.Magenta.copy(alpha = 0f))
    val selectedGradient = listOf(majenta, backgroundColor)
//    val selectedGradient = listOf(majenta, Color.hsl(277f, 0.87f, 0.53f))

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(height)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(selectedGradient)
            )
            .border(
                width = Dp.Hairline,
                brush = Brush.linearGradient(selectedBorderColors),
                shape = RoundedCornerShape(10.dp)
            ).clickable {
                onClickReservation()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(text)
                }
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}