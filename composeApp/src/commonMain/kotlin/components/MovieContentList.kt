package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import domain.model.MovieObject

@Composable
fun MovieContentList(
    navigator: Navigator?,
    userId: String,
    status: String,
    items: List<MovieObject>,
    isImage: Boolean,
    onClickedItem: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(350.dp)
    ) {
        Text(
//                    buildAnnotatedString
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append(status)
                }
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,                // dealing with overflow
            modifier = Modifier.padding(start = 13.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = items,
                key = { movie ->
                    movie.id
                }
            ) { movie ->
                movie.let { MovieContentItem(id = it.id, userId = userId, url = it.url, title = it.title, navigator = navigator, isImage = isImage, onClickedItem = onClickedItem) }
            }
        }
    }
}