package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CustomDrawer(
    name: String = "John Doe",
    image: String = "https://wallpapers.com/images/featured/cool-profile-picture-87h46gcobjl5e4xu.jpg",
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.6f)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
        CoilImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            imageModel = { image },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                contentDescription = "Profile Picture",
            ),
            loading = {
                AnimatedShimmerItem(
                    modifier = Modifier.size(100.dp)
                )
            },
            failure = {
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .alpha(alpha = 0.38f),
                    color = Color.Black
                ) {}
            }
        )
//        KamelImage(
//            resource = asyncPainterResource(image),
//            contentDescription = "Profile Picture",
//            modifier = Modifier
//                .size(100.dp)
//                .clip(CircleShape),
//            onLoading = {
////                Column(
////                    horizontalAlignment = Alignment.CenterHorizontally,
////                    verticalArrangement = Arrangement.Center,
////                    modifier = Modifier
////                        .size(100.dp)
////                ) {
////
////                }
//                AnimatedShimmerItem(
//                    modifier = Modifier.size(100.dp)
//                )
//            }
//        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append(name)
                }
            },
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}