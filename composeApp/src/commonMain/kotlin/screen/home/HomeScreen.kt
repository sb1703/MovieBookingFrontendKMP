package screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import components.MovieContentList
import domain.model.MovieObject
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextFieldDefaults
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.ticket.backgroundColor
import screen.ticket.backgroundColor2

class HomeScreen(val userId: String): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val homeViewModel = getScreenModel<HomeViewModel>()

        LaunchedEffect(key1 = Unit) {
            homeViewModel.getMovies()
        }

        val nowPlayingList by homeViewModel.nowPlayingList.collectAsState()
        val searchText by homeViewModel.searchText.collectAsState()

        HomeContent(
            navigator = navigator,
            userId = userId,
            nowPlayingList = nowPlayingList,
            searchText = searchText,
            updateSearchText = homeViewModel::updateSearchText
        )
    }


}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun HomeContent(
    navigator: Navigator?,
    userId: String,
    nowPlayingList: List<MovieObject>,
    searchText: String,
    updateSearchText: (String) -> Unit
) {
    val scrollState = rememberScrollState(initial = 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                colors = listOf(backgroundColor, backgroundColor2)
            )),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 13.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Choose Movie")
                }
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CupertinoSearchTextField(
                value = searchText,
                onValueChange = { updateSearchText(it) },
                placeholder = { Text("Search", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White.copy(0.7f)) },
                keyboardActions = KeyboardActions(onSearch = {}),
                colors = CupertinoSearchTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                )
            )
            MovieContentList(
                navigator = navigator,
                userId = userId,
                status = "Now Playing",
                items = nowPlayingList.map {
                    MovieObject(
                        id = it.id.toString(),
                        url = it.url,
                        title = it.title
                    )
                },
                onClickedItem = {},
                isImage = true
            )
            MovieContentList(
                navigator = navigator,
                userId = userId,
                status = "Coming Soon",
                items = nowPlayingList.map {
                    MovieObject(
                        id = it.id.toString(),
                        url = it.url,
                        title = it.title
                    )
                },
                onClickedItem = {},
                isImage = true
            )
            MovieContentList(
                navigator = navigator,
                userId = userId,
                status = "Top Movies",
                items = nowPlayingList.map {
                    MovieObject(
                        id = it.id.toString(),
                        url = it.url,
                        title = it.title
                    )
                },
                onClickedItem = {},
                isImage = true
            )
            MovieContentList(
                navigator = navigator,
                userId = userId,
                status = "Favourite",
                items = nowPlayingList.map {
                    MovieObject(
                        id = it.id.toString(),
                        url = it.url,
                        title = it.title
                    )
                },
                onClickedItem = {},
                isImage = true
            )
        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(backgroundColor, backgroundColor2)
//                )),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Text(
//            modifier = Modifier.padding(top = 12.dp),
//            text = buildAnnotatedString {
//                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                    append("Choose Movie")
//                }
//            },
//            color = Color.White,
//            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//        CustomSearchBar(
//            text = "",
//            updateText = {},
//            onSearchClicked = {}
//        )
//        Column(
//            modifier = Modifier
//                .padding(top = 30.dp)
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState()),
//            ver
//        ) {
//            MovieContentList(
//                navigator = navigator,
//                status = "Now Playing",
//                items = nowPlayingList
//            )
//            MovieContentList(
//                navigator = navigator,
//                status = "Coming Soon",
//                items = nowPlayingList
//            )
//            MovieContentList(
//                navigator = navigator,
//                status = "Top Movies",
//                items = nowPlayingList
//            )
//            MovieContentList(
//                navigator = navigator,
//                status = "Favourite",
//                items = nowPlayingList
//            )
//        }
//    }
}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent(
        navigator = null,
        nowPlayingList = listOf(),
        searchText = "",
        updateSearchText = {},
        userId = ""
    )
}