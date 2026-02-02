package lu.post.eval.data.repository

import kotlinx.coroutines.test.runTest
import lu.post.eval.data.api.MoviesApiService
import lu.post.eval.data.model.MovieDto
import lu.post.eval.data.model.MoviesResponseDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepository
    private lateinit var fakeApiService: FakeMoviesApiService

    @Before
    fun setup() {
        fakeApiService = FakeMoviesApiService()
        repository = MoviesRepository(fakeApiService)
    }

    @Test
    fun `repository should be created`() {
        assertTrue(repository != null)
    }

    @Test
    fun `should search movies`() = runTest {
        val movies = repository.searchMovies("Star")
        assertTrue(movies.isNotEmpty())
    }

    @Test
    fun `should return empty list when no results`() = runTest {
        fakeApiService.shouldReturnEmpty = true
        val movies = repository.searchMovies("xyznotfound")
        assertTrue(movies.isEmpty())
    }

    @Test
    fun `should map dto to domain model correctly`() = runTest {
        val movies = repository.searchMovies("Star")
        val movie = movies.first()
        assertEquals("Star Wars", movie.title)
        assertEquals("1977", movie.year)
        assertEquals("tt0076759", movie.imdbId)
    }
}

class FakeMoviesApiService : MoviesApiService {
    var shouldReturnEmpty = false

    override suspend fun searchMovies(apiKey: String, search: String): MoviesResponseDto {
        if (shouldReturnEmpty) {
            return MoviesResponseDto(search = null, response = "False")
        }
        return MoviesResponseDto(
            search = listOf(
                MovieDto(
                    title = "Star Wars",
                    year = "1977",
                    imdbId = "tt0076759",
                    type = "movie",
                    poster = "https://example.com/poster.jpg"
                ),
                MovieDto(
                    title = "Star Trek",
                    year = "2009",
                    imdbId = "tt0796366",
                    type = "movie",
                    poster = "https://example.com/poster2.jpg"
                )
            ),
            totalResults = "2",
            response = "True"
        )
    }
}
