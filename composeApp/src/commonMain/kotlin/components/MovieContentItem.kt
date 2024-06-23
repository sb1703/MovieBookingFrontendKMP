package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import chaintech.videoplayer.ui.VideoPlayerView
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import screen.detail.DetailScreen

@Composable
fun MovieContentItem(
    navigator: Navigator?,
    userId: String,
    id: String,
    url: String,
    title: String,
    isImage: Boolean,
    onClickedItem: (String) -> Unit
) {

    Box(
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
            .clickable {
                // navigate to movie details screen
                if(navigator != null) {
                    navigator.push(DetailScreen(movieId = id, userId = userId))
                } else {
                    onClickedItem(url)
                }
            }
            .height(300.dp)
            .width(225.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        if(isImage) {
            CoilImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)),
                imageModel = { url },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
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
                },
                component = rememberImageComponent {
                    +CrossfadePlugin(
                        duration = 500
                    )
                }
            )
//            KamelImage(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(RoundedCornerShape(20.dp)),
//                contentDescription = "Movie Image",
//                contentScale = ContentScale.Crop,
//                resource = asyncPainterResource(url),
//                onLoading = {
//                    AnimatedShimmerItem(
//                        modifier = Modifier
//                            .width(225.dp)
//                            .height(300.dp)
//                            .clip(RoundedCornerShape(10.dp))
//                    )
//                },
//                onFailure = {
//                    Surface(
//                        modifier = Modifier
//                            .height(300.dp)
//                            .fillMaxWidth()
//                            .alpha(alpha = 0.38f),
//                        color = Color.Black
//                    ) {}
//                }
//            )
        } else {
            Surface(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .alpha(alpha = 0.38f)
                    .clickable {
                        onClickedItem(url)
                    },
                color = Color.Black
            ) {}
//            VideoPlayerView(
//                modifier = Modifier
//                    .fillMaxSize(),
//                url = path
//            )
        }
        if(title.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(title)
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis                // dealing with overflow
                )
            }
        }
    }
}