package screen.main

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.RepositoryImpl
import domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val repository: RepositoryImpl
): ScreenModel {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    fun setUser(user: User) {
        _user.value = user
    }

}