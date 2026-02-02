package lu.post.eval.data.repository

import lu.post.eval.data.api.MoviesApiService
import lu.post.eval.domain.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [MoviesRepositoryInterface] that fetches data from the OMDB API.
 * Handles the conversion from DTOs to domain models.
 *
 * @property apiService The Retrofit service for API calls
 */
@Singleton
class MoviesRepository @Inject constructor(
    private val apiService: MoviesApiService
) : MoviesRepositoryInterface {

    /**
     * Search for movies by querying the OMDB API.
     * Maps the API response DTOs to domain models.
     */
    override suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies(search = query)
        return response.search?.map { it.toDomain() } ?: emptyList()
    }
}
