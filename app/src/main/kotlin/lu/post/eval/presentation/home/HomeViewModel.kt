package lu.post.eval.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lu.post.eval.data.repository.MoviesRepositoryInterface
import lu.post.eval.domain.model.Movie
import javax.inject.Inject

/**
 * UI state for the Home screen.
 *
 * @property searchQuery Current text in the search field
 * @property movies List of movies returned from search
 * @property isLoading Whether a search is in progress
 * @property error Error message if the search failed
 */
data class HomeUiState(
    val searchQuery: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Home screen.
 * Manages the search functionality and exposes UI state as a StateFlow.
 *
 * @property moviesRepository Repository for fetching movie data
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    /** Observable UI state for the Home screen */
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Updates the search query in the UI state.
     * @param query The new search query text
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    /**
     * Triggers a movie search using the current search query.
     * Updates the UI state with loading, results, or error states.
     */
    fun searchMovies() {
        val query = _uiState.value.searchQuery
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val movies = moviesRepository.searchMovies(query)
                _uiState.update { it.copy(movies = movies, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
