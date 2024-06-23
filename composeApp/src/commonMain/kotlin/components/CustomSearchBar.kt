package components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
fun CustomSearchBar(
    text: String,
    updateText: (String) -> Unit,
    onSearchClicked: () -> Unit,
    scrollState: ScrollState
) {

    val animatedHeight by animateDpAsState(
        targetValue = if(scrollState.value > 10) 0.dp else 30.dp,
        label = "animatedHeight"
    )

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .height(animatedHeight)
            .fillMaxWidth(0.8f)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.6f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 5.dp),
            imageVector = Icons.Filled.Search,
            contentDescription = "Search",
            tint = Color.White
        )
        BasicTextField(
            value = text,
            onValueChange = { updateText(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester)
                .focusable(),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked()
                    focusManager.clearFocus()
                }
            )
        )
    }
}