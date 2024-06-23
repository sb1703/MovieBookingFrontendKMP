package screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import domain.model.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.main.MainScreen
import utils.Constants.GOOGLE_WEB_CLIENT_ID

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val loginViewModel = getScreenModel<LoginViewModel>()
        val authReady by loginViewModel.authReady.collectAsState()

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(
                credentials = GoogleAuthCredentials(
                    serverId = GOOGLE_WEB_CLIENT_ID
                )
            )
            loginViewModel.setAuthReady(true)
        }
        LoginContent(
            authReady = authReady,
            navigator = navigator,
            verifyTokenOnBackend = loginViewModel::verifyTokenOnBackend,
        )
    }


}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LoginContent(
    authReady: Boolean,
    navigator: Navigator?,
    verifyTokenOnBackend: (String) -> Deferred<User>,
) {

    if(authReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            GoogleButtonUiContainer(
                onGoogleSignInResult = { googleUser ->
                    val tokenId = googleUser?.idToken
                    println("TOKEN: $tokenId")
                    tokenId?.let {
                        val user = verifyTokenOnBackend(it)
                        user.invokeOnCompletion {
                            if(it == null) {
                                navigator?.replaceAll(MainScreen(user.getCompleted()))
                            }
                        }
                    }
                }
            ) {
                GoogleSignInButton(
                    onClick = {
                        this.onClick()
                    }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    }

}

@Preview
@Composable
fun LoginContentPreview() {
    LoginContent(
        authReady = true,
        navigator = null,
        verifyTokenOnBackend = { TODO() },
    )
}