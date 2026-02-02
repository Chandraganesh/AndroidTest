package lu.post.eval.presentation.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import lu.post.eval.data.repository.MoviesRepositoryInterface
import lu.post.eval.domain.model.Movie
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeRepository: FakeMoviesRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeMoviesRepository()
        viewModel = HomeViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel should be created`() {
        assertTrue(viewModel != null)
    }

    @Test
    fun `should update search query`() {
        viewModel.onSearchQueryChange("Star")
        assertEquals("Star", viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `should search movies`() = runTest {
        viewModel.onSearchQueryChange("Star")
        viewModel.searchMovies()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.movies.isNotEmpty())
    }

    @Test
    fun `should not search with empty query`() = runTest {
        viewModel.onSearchQueryChange("")
        viewModel.searchMovies()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.movies.isEmpty())
    }
}

class FakeMoviesRepository : MoviesRepositoryInterface {
    override suspend fun searchMovies(query: String): List<Movie> {
        return listOf(
            Movie(
                title = "Star Wars",
                year = "1977",
                imdbId = "tt0076759",
                type = "movie",
                poster = "https://example.com/poster.jpg"
            ),
            Movie(
                title = "Star Trek",
                year = "2009",
                imdbId = "tt0796366",
                type = "movie",
                poster = "https://example.com/poster2.jpg"
            )
        )
    }
}
