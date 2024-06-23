package components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

val majenta = Color(red = 0.710f, green = 0.070f, blue = 0.420f, alpha = 1.000f)

@Composable
fun DateTimeButton(
    idx: Int,
    modifier: Modifier = Modifier,
    weekDay: String = "",
    numDay: String = "",
    time: String = "",
    selectedDateTime: Int,
    updateSelectedDateTime: (Int) -> Unit,
    isDate: Boolean
) {

    var width by remember { mutableStateOf( 50.dp ) }
    var height by remember { mutableStateOf( if(isDate) 90.dp else 30.dp ) }

//    var widthDate by remember { mutableStateOf(50.dp) }
//    var widthTime by remember { mutableStateOf(50.dp) }
//    var heightDate by remember { mutableStateOf(90.dp) }
//    var heightTime by remember { mutableStateOf(30.dp) }

    val currentBorderColors = listOf(Color.Cyan, Color.Cyan.copy(alpha = 0f), Color.Cyan.copy(alpha = 0f))
    val currentGradient = listOf(backgroundColor, Color.DarkGray)

    val selectedBorderColors = listOf(Color.Magenta, Color.Magenta.copy(alpha = 0f), Color.Magenta.copy(alpha = 0f))
    val selectedGradient = listOf(majenta, backgroundColor)

//    val animatedWidthDate by animateDpAsState(
//        targetValue = widthDate,
//    )
//
//    val animatedWidthTime by animateDpAsState(
//        targetValue = widthTime,
//    )
//
//    val animatedHeightDate by animateDpAsState(
//        targetValue = heightDate,
//    )
//
//    val animatedHeightTime by animateDpAsState(
//        targetValue = heightTime,
//    )

    val animatedWidth by animateDpAsState(
        targetValue = width,
    )

    val animatedHeight by animateDpAsState(
        targetValue = height,
    )

//    LaunchedEffect(selectedDate) {
//        if(selectedDate == idx) {
//            widthDate = 60.dp
//            heightDate = 100.dp
//        } else {
//            widthDate = 50.dp
//            heightDate = 90.dp
//        }
//    }
//
//    LaunchedEffect(selectedTime) {
//        if(selectedTime == idx) {
//            widthTime = 60.dp
//            heightTime = 40.dp
//        } else {
//            widthTime = 50.dp
//            heightTime = 30.dp
//        }
//    }

    LaunchedEffect(selectedDateTime) {
        if(isDate) {
            if(selectedDateTime == idx) {
                width = 60.dp
                height = 100.dp
            } else {
                width = 50.dp
                height = 90.dp
            }
        } else {
            if(selectedDateTime == idx) {
                width = 60.dp
                height = 40.dp
            } else {
                width = 50.dp
                height = 30.dp
            }
        }
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = modifier
//                .width(animatedWidthDate)
//                .height(animatedHeightDate)
                .width(animatedWidth)
                .height(animatedHeight)
                .clip(RoundedCornerShape(10.dp))
                .background(
//                    brush = if(idx == selectedDate) {
//                        Brush.linearGradient(selectedGradient)
//                    } else {
//                        Brush.linearGradient(currentGradient)
//                    }
                    brush = if(idx == selectedDateTime) {
                        Brush.linearGradient(selectedGradient)
                    } else {
                        Brush.linearGradient(currentGradient)
                    }
                )
                .border(
                    width = Dp.Hairline,
//                    brush = if(idx == selectedDate) {
//                        Brush.linearGradient(selectedBorderColors)
//                    } else {
//                        Brush.linearGradient(currentBorderColors)
//                    }
                    brush = if(idx == selectedDateTime) {
                        Brush.linearGradient(selectedBorderColors)
                    } else {
                        Brush.linearGradient(currentBorderColors)
                    },
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
//                    updateSelectedDate(idx)
                    updateSelectedDateTime(idx)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isDate) {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(weekDay)
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
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(numDay)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(time)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
//        Column(
//            modifier = Modifier
//                .width(animatedWidthTime)
//                .height(animatedHeightTime)
//                .clip(RoundedCornerShape(10.dp))
//                .background(
//                    brush = if(idx == selectedTime) {
//                        Brush.linearGradient(selectedGradient)
//                    } else {
//                        Brush.linearGradient(currentGradient)
//                    }
//                )
//                .border(
//                    width = Dp.Hairline,
//                    brush = if(idx == selectedTime) {
//                        Brush.linearGradient(selectedBorderColors)
//                    } else {
//                        Brush.linearGradient(currentBorderColors)
//                    },
//                    shape = RoundedCornerShape(10.dp)
//                )
//                .clickable {
//                    updateSelectedTime(idx)
//                },
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                modifier = Modifier,
//                text = buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append(time)
//                    }
//                },
//                color = Color.White,
//                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
    }
}