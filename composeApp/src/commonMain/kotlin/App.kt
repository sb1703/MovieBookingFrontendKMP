import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.login.LoginScreen

@Composable
@Preview
fun App() {

    MaterialTheme {
        Navigator(LoginScreen()) {
            SlideTransition(it)
        }
    }
}