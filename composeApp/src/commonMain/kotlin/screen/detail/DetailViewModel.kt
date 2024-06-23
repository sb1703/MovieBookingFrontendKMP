package screen.detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.repository.RepositoryImpl
import domain.model.Movie
import domain.model.ZoomableObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: RepositoryImpl
): ScreenModel {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _zoomables = MutableStateFlow(ZoomableObject.NOTHING)
    val zoomables = _zoomables.asStateFlow()

    private val _url = MutableStateFlow("")
    val url = _url.asStateFlow()

    private val _dates = MutableStateFlow<List<String>>(emptyList())
    val dates = _dates.asStateFlow()

    private val _times = MutableStateFlow<List<String>>(emptyList())
    val times = _times.asStateFlow()

    fun setZoomables(zoomables: ZoomableObject) {
        _zoomables.value = zoomables
    }

    fun setUrl(url: String) {
        _url.value = url
    }

    fun getMovie(movieId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            _movie.value = repository.getMovie(movieId).movie
        }
    }

    fun getDates(movieId: String) {
        screenModelScope.launch(Dispatchers.IO) {
            _dates.value = repository.getDates(movieId).dates
        }
    }

    fun getTimes(movieId: String, date: String) {
        screenModelScope.launch(Dispatchers.IO) {
            _times.value = repository.getTimes(movieId, date).times
        }
    }

}