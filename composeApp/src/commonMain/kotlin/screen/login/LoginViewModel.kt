package screen.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.repository.RepositoryImpl
import domain.model.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: RepositoryImpl
): ScreenModel {

    private val _authReady = MutableStateFlow(false)
    val authReady = _authReady.asStateFlow()

    fun verifyTokenOnBackend(token: String): Deferred<User> {
        return screenModelScope.async(Dispatchers.IO) {
            repository.userTokenVerification(token).user
        }
    }

    fun setAuthReady(value: Boolean) {
        _authReady.value = value
    }

}