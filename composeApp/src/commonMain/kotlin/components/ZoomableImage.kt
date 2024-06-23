package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ZoomableImage(
    selectedImageUrl: String,
    onCloseClicked: () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = maxOf(1f, minOf(scale * zoom, 5f))
                    val maxX = (size.width * (scale - 1)) / 2
                    val minX = -maxX
                    offsetX = maxOf(minX, minOf(maxX, offsetX + pan.x))
                    val maxY = (size.height * (scale - 1)) / 2
                    val minY = -maxY
                    offsetY = maxOf(minY, minOf(maxY, offsetY + pan.y))
                }
            }
            .clickable {
                onCloseClicked()
            }
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale)),
                    scaleY = maxOf(.5f, minOf(3f, scale)),
                    translationX = offsetX,
                    translationY = offsetY
                ),
            imageModel = { selectedImageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                contentDescription = "Movie Image"
            ),
            loading = {
                AnimatedShimmerItem(
                    modifier = Modifier
                        .width(225.dp)
                        .height(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            },
            failure = {
                Surface(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .alpha(alpha = 0.38f),
                    color = Color.Black
                ) {}
            }
        )
//        KamelImage(
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer(
//                    scaleX = maxOf(.5f, minOf(3f, scale)),
//                    scaleY = maxOf(.5f, minOf(3f, scale)),
//                    translationX = offsetX,
//                    translationY = offsetY
//                ),
//            resource = asyncPainterResource(selectedImageUrl),
//            contentScale = ContentScale.Fit,
//            contentDescription = "Movie Image",
//            onLoading = {
//                AnimatedShimmerItem(
//                    modifier = Modifier
//                        .width(225.dp)
//                        .height(300.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                )
//            },
//            onFailure = {
//                Surface(
//                    modifier = Modifier
//                        .height(300.dp)
//                        .fillMaxWidth()
//                        .alpha(alpha = 0.38f),
//                    color = Color.Black
//                ) {}
//            }
//        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Button(onClick = onCloseClicked) {
//                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
//                Text(text = "Close")
//            }
            IconButton(
                modifier = Modifier
//                    .statusBarsPadding()
                    .size(48.dp),
                onClick = {
                    onCloseClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White
                )
            }
        }
    }
}