import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController

//fun MainViewController() = ComposeUIViewController { App() }

fun MainViewController(
    topSafeArea: Float,
    bottomSafeArea: Float
) = ComposeUIViewController {
    Surface(
        modifier = Modifier
            .padding(
                top = topSafeArea.dp,
                bottom = bottomSafeArea.dp
            )
            .fillMaxSize()
    ) {
        App()
    }
}