package screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.RepositoryImpl
import domain.model.MovieObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: RepositoryImpl
): ScreenModel {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _nowPlayingList = MutableStateFlow<List<MovieObject>>(emptyList())
    val nowPlayingList = _nowPlayingList.asStateFlow()

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    suspend fun getMovies() {
        _nowPlayingList.value = repository.getMovies().movies
    }

}