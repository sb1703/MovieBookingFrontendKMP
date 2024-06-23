package components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import domain.model.Ticket
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun Ticket(
    width: Dp,
    height: Dp,
    index: Int,
    tickets: List<Ticket>,
    id: Int,
    title: String,
    date: String,
    time: String,
    seats: List<String>,
    image: String,
    barcode: String,
    removeFirstAndAddToLast: () -> Unit = {},
    removeFirstTicket: () -> Unit = {}
) {

    var isDragging by rememberSaveable { mutableStateOf(false) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var height by rememberSaveable { mutableStateOf(0f) }

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 100, easing = EaseInOut)
    )

    val animatedHeight by animateFloatAsState(
        targetValue = height,
        animationSpec = tween(durationMillis = 100, easing = EaseInOut)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                if(id == tickets.first()._id) {
                    translationX = animatedOffsetX
                    rotationZ = getRotation(10.0, width, animatedOffsetX).toFloat()
                }
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        val swipedRight = offsetX.dp > width/2
                        val swipedLeft = -offsetX.dp > width/2
                        if(swipedLeft) {
                            offsetX *= 2
                            removeFirstTicket()
                        } else {
                            if(swipedRight) {
                                offsetX *= 2
                                removeFirstAndAddToLast()
                            }
                        }
                        offsetX = 0f
                        height = 0f
                        isDragging = false
                    },
                    onDragCancel = {
                        val swipedRight = offsetX.dp > width/2
                        val swipedLeft = -offsetX.dp > width/2
                        if(swipedLeft) {
                            offsetX *= 2
                            removeFirstTicket()
                        } else {
                            if(swipedRight) {
                                offsetX *= 2
                                removeFirstAndAddToLast()
                            }
                        }
                        offsetX = 0f
                        height = 0f
                        isDragging = false
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount
                        height += -dragAmount/5
                    }
                )
            }
            .zIndex(
                if(index == 0 && offsetX > 100f) {
                    (tickets.size-index).toFloat()-1f
                } else {
                    (tickets.size-index).toFloat()
                }
            )
            .rotate(
                if(index == 1) {
                    -6f
                } else if(index == 2) {
                    6f
                } else {
                    0f
                }
            )
            .scale(
                if(index == 0) {
                    1f
                } else {
                    0.9f
                }
            )
            .offset(
                x = if(index == 1) {
                    -40.dp
                } else if(index == 2) {
                    40.dp
                } else {
                    0.dp
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(350.dp)
                .clip(shape = TicketShape(cornerRadius = 100f))
                .clip(shape = RoundedCornerShape(topStart = 100f, topEnd = 100f))
        ) {
            val painter = when (val resource = asyncPainterResource(image)) {
                is Resource.Loading -> {
                    AnimatedShimmerItem(
                        modifier = Modifier
                            .width(250.dp)
                            .height(350.dp)
                            .clip(shape = TicketShape(cornerRadius = 100f))
                            .clip(shape = RoundedCornerShape(topStart = 100f, topEnd = 100f))
                    )
                }
                is Resource.Success -> {
                    val painter: Painter = resource.value
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .width(250.dp)
                            .height(350.dp)
                            .paint(
                                painter = painter,
                                contentScale = ContentScale.Crop,
                                alpha = 0.9f
                            )
                    ) {
                        Text(
                            modifier = Modifier,
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(title)
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                is Resource.Failure -> {
//                val fallbackPainter = painterResource()
//                Image(fallbackPainter, contentDescription = "Profile")
                }
            }
        }
        Spacer(modifier = Modifier.height(if(animatedHeight.dp > 0.dp) animatedHeight.dp else 0.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier
                .width(250.dp)
                .clip(shape = InvertedTicketShape(cornerRadius = 100f))
                .clip(shape = RoundedCornerShape(bottomStart = 100f, bottomEnd = 100f))
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .width(250.dp-33.dp-33.dp)
                    .drawBehind {
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 525f, y = 0f),
                            color = Color.Black,
                            strokeWidth = 3.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(45f, 45f), 0f)
                        )
                    }
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(250.dp-33.dp-33.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append("Date: $date")
                        }
                    },
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append("Time: $time")
                        }
                    },
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(250.dp-33.dp-33.dp)
                    .padding(horizontal = 5.dp)
            ) {
//                Text(
//                    modifier = Modifier,
//                    text = buildAnnotatedString {
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
//                            append("Row: $row")
//                        }
//                    },
//                    color = Color.Black,
//                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append("Seats: $seats")
                        }
                    },
                    color = Color.Black,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            CoilImage(
                modifier = Modifier
                    .width(250.dp-28.dp-28.dp)
                    .height(65.dp)
                    .padding(bottom = 20.dp),
                imageModel = { barcode },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    contentDescription = "Barcode",
                ),
                loading = {
                    Text("Loading...")
                }
            )
//            KamelImage(
//                resource = asyncPainterResource(barcode),
//                contentDescription = "Barcode",
//                modifier = Modifier
//                    .width(250.dp-28.dp-28.dp)
//                    .height(65.dp)
//                    .padding(bottom = 20.dp),
//                contentScale = ContentScale.Crop,
//                onLoading = {
//                    Text("Loading...")
//                }
//            )
        }
    }
}

fun getRotation(angle: Double, width: Dp, offset: Float): Double {
    val progress = offset.dp/width
    return progress * angle
}

class TicketShape(private val cornerRadius: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTicketPath(size = size, cornerRadius = cornerRadius)
        )
    }
}

fun drawTicketPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        lineTo(x = size.width, y = 0f)
        lineTo(x = size.width, y = size.height - cornerRadius)
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = size.height - cornerRadius,
                right = size.width + cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = cornerRadius, y = size.height)
        // Bottom left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = size.height - cornerRadius,
                right = cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}


class InvertedTicketShape(private val cornerRadius: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawInvertedTicketPath(size = size, cornerRadius = cornerRadius)
        )
    }
}

fun drawInvertedTicketPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = -cornerRadius,
                right = cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width - cornerRadius, y = 0f)
        // Top right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = -cornerRadius,
                right = size.width + cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width, y = size.height)
        lineTo(x = 0f, y = size.height)
        lineTo(x = 0f, y = 0f)
        close()
    }
}

//class TicketShape2(private val cornerRadius: Float) : Shape {
//
//    override fun createOutline(
//        size: Size,
//        layoutDirection: LayoutDirection,
//        density: Density
//    ): Outline {
//        return Outline.Generic(
//            path = drawTicketPath2(size = size, cornerRadius = cornerRadius)
//        )
//    }
//}
//
//fun drawTicketPath2(size: Size, cornerRadius: Float): Path {
//    return Path().apply {
//        reset()
//        lineTo(x = size.width, y = 0f)
//        lineTo(x = size.width, y = 3*size.height/4)
//        moveTo(x = size.width, y = 3*size.height/4 + cornerRadius)
//        // right arc
//        arcTo(
//            rect = Rect(
//                top = 3*size.height/4,
//                left = size.width - cornerRadius,
//                right = size.width + cornerRadius,
//                bottom = 3*size.height/4 + 2*cornerRadius
//            ),
//            startAngleDegrees = 270.0f,
//            sweepAngleDegrees = -180.0f,
//            forceMoveTo = false
//        )
//        moveTo(x = size.width, y = 3*size.height/4 + 2*cornerRadius)
//        lineTo(x = size.width, y = size.height)
//        lineTo(x = 0f, y = size.height)
//        lineTo(x = 0f, y = 3*size.height/4 + 2*cornerRadius)
//        moveTo(x = 0f, y = 3*size.height/4 + cornerRadius)
//        // left arc
//        arcTo(
//            rect = Rect(
//                top = 3*size.height/4,
//                left =  -cornerRadius,
//                right = cornerRadius,
//                bottom = 3*size.height/4 + 2*cornerRadius
//            ),
//            startAngleDegrees = 90.0f,
//            sweepAngleDegrees = 180.0f,
//            forceMoveTo = false
//        )
//        moveTo(x = 0f, y = 3*size.height/4)
//        lineTo(x = 0f, y = 0f)
//        close()
//    }
//}