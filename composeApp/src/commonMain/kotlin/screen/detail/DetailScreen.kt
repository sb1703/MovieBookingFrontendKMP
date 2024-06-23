package screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import chaintech.videoplayer.ui.VideoPlayerView
import com.skydoves.landscapist.coil3.CoilImage
import components.AnimatedShimmerItem
import components.MovieContentList
import components.ReservationButton
import components.SelectDateTimeComponent
import components.ZoomableImage
import domain.model.Movie
import domain.model.MovieObject
import domain.model.ZoomableObject
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import screen.audis.AudiScreen
import screen.ticket.backgroundColor2

class DetailScreen(val movieId: String, val userId: String): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val detailViewModel = getScreenModel<DetailViewModel>()

        LaunchedEffect(key1 = Unit) {
            detailViewModel.getMovie(movieId)
            detailViewModel.getDates(movieId)
        }

        val movie by detailViewModel.movie.collectAsState()
        val zoombales by detailViewModel.zoomables.collectAsState()
        val url by detailViewModel.url.collectAsState()
        val dates by detailViewModel.dates.collectAsState()
        val times by detailViewModel.times.collectAsState()

        DetailContent(
            navigator,
            movieId = movieId,
            userId = userId,
            movie = movie,
            zoomables = zoombales,
            url = url,
            dates = dates,
            times = times,
            setZoomable = detailViewModel::setZoomables,
            setUrl = detailViewModel::setUrl,
            getTimes = detailViewModel::getTimes
        )
    }

}

@Composable
fun DetailContent(
    navigator: Navigator?,
    movieId: String,
    userId: String,
    movie: Movie?,
    zoomables: ZoomableObject,
    url: String,
    dates: List<String>,
    times: List<String>,
    setZoomable: (ZoomableObject) -> Unit,
    setUrl: (String) -> Unit,
    getTimes: (String, String) -> Unit
) {

    val gradient = listOf(backgroundColor2.copy(alpha = 0f), backgroundColor2.copy(alpha = 0f), backgroundColor2.copy(alpha = 0.5f), backgroundColor2)
    val scrollState = rememberScrollState()

    var selectedDate by remember { mutableStateOf(0) }
    var selectedTime by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = selectedDate) {
        if(dates.isNotEmpty()) {
            getTimes(movieId, dates[selectedDate])
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor2),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                imageModel = { movie?.Poster ?: "" },
                success = { state, painter ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painter = painter,
                                alignment = Alignment.TopCenter,
                                contentScale = ContentScale.FillWidth,
                            )
                            .background(brush = Brush.verticalGradient(
                                colors = gradient
                            )),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                    }
                },
                loading = {
                    AnimatedShimmerItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                },
                failure = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .alpha(alpha = 0.38f),
                        color = Color.Black
                    ) {}
                }
            )
//            val resource = when(val painter = asyncPainterResource(movie?.Poster ?: "")) {
//                is Resource.Success -> {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .paint(
//                                painter = painter.value,
//                                alignment = Alignment.TopCenter,
//                                contentScale = ContentScale.FillWidth,
//                            )
//                            .background(brush = Brush.verticalGradient(
//                                colors = gradient
//                            )),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//
//                    }
//                }
//                is Resource.Loading -> {
//                    AnimatedShimmerItem(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                    )
//                }
//                is Resource.Failure -> {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .alpha(alpha = 0.38f),
//                        color = Color.Black
//                    ) {}
//                }
//            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(movie?.Title ?: "")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append((movie?.Genre ?: "") + "  •  " + (movie?.Year ?: ""))
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
                                append(movie?.Runtime ?: "")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(movie?.Plot ?: "")
                        }
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                // star component
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = Color.White.copy(alpha = 0.5f),
                    thickness = Dp.Hairline
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Starring")
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        (movie?.Actors ?: "").split(", ").forEach { actor ->
                            Text(
                                modifier = Modifier,
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(actor)
                                    }
                                },
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Column {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Director(s)")
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        (movie?.Director ?: "").split(", ").forEach { director ->
                            Text(
                                modifier = Modifier,
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(director)
                                    }
                                },
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Writer(s)")
                                }
                            },
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        (movie?.Writer ?: "").split(", ").forEach { writer ->
                            Text(
                                modifier = Modifier,
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                        append(writer)
                                    }
                                },
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = Color.White.copy(alpha = 0.5f),
                    thickness = Dp.Hairline
                )
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MovieContentList(
                        navigator = null,
                        userId = userId,
                        status = "Images",
                        items = (movie?.Images ?: emptyList()).map {
                            MovieObject(
                                id = it,
                                url = it,
                                title = ""
                            )
                        },
                        onClickedItem = { imageUrl ->
                            setZoomable(ZoomableObject.ZOOMABLE_IMAGE)
                            setUrl(imageUrl)
                        },
                        isImage = true
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        color = Color.White.copy(alpha = 0.5f),
                        thickness = Dp.Hairline
                    )
                    MovieContentList(
                        navigator = null,
                        userId = userId,
                        status = "Trailers",
                        items = (movie?.Videos ?: emptyList()).map {
                            MovieObject(
                                id = it,
                                url = it,
                                title = ""
                            )
                        },
                        onClickedItem = { videoUrl ->
                            setZoomable(ZoomableObject.ZOOMABLE_VIDEO)
                            setUrl(videoUrl)
                        },
                        isImage = false
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = Color.White.copy(alpha = 0.5f),
                    thickness = Dp.Hairline
                )
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Select date and time")
                            }
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    SelectDateTimeComponent(
                        isDate = true,
                        selectedDateTime = selectedDate,
                        updateSelectedDateTime = { selectedDate = it },
                        dates = dates,
                        times = times
                    )
                    AnimatedVisibility(visible = dates.isNotEmpty() && times.isNotEmpty()) {
                        SelectDateTimeComponent(
                            isDate = false,
                            selectedDateTime = selectedTime,
                            updateSelectedDateTime = { selectedTime = it },
                            dates = dates,
                            times = times
                        )
                    }
                    AnimatedVisibility(visible = dates.isNotEmpty() && times.isNotEmpty()) {
                        ReservationButton(
                            onClickReservation = {
                                navigator?.push(AudiScreen(date = dates[selectedDate], time = times[selectedTime], movieId = movieId, userId = userId))
                            }
                        )
                    }
                }
            }
        }
        if(zoomables == ZoomableObject.NOTHING) {
            IconButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .size(48.dp),
                onClick = {
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
        if(zoomables == ZoomableObject.ZOOMABLE_IMAGE) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable {
                        setZoomable(ZoomableObject.NOTHING)
                    }
            )
            ZoomableImage(
                selectedImageUrl = url,
                onCloseClicked = {
                    setZoomable(ZoomableObject.NOTHING)
                }
            )
        }
        if(zoomables == ZoomableObject.ZOOMABLE_VIDEO) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable {
                        setZoomable(ZoomableObject.NOTHING)
                    },
                contentAlignment = Alignment.Center
            ) {
                VideoPlayerView(
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp),
                    url = url
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp),
                    onClick = {
                        setZoomable(ZoomableObject.NOTHING)
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
}