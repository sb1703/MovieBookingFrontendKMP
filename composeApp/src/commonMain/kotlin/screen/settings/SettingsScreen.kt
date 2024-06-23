package screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.ticket.backgroundColor
import screen.ticket.backgroundColor2

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val settingsViewModel = getScreenModel<SettingsViewModel>()

        SettingsContent(
            navigator = navigator,
        )
    }


}

@Composable
fun SettingsContent(
    navigator: Navigator?,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(backgroundColor, backgroundColor2)
            )),
        contentAlignment = Alignment.Center
    ) {
        Text("Settings Tab", color = Color.White)
    }
}

@Preview
@Composable
fun SettingsContentPreview() {
    SettingsContent(
        navigator = null,
    )
}