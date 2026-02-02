package lu.post.eval.data.repository

import lu.post.eval.domain.model.Movie

/**
 * Repository interface for movie data operations.
 * Defines the contract for fetching movies, allowing for different implementations
 * (e.g., remote API, local cache, mock for testing).
 */
interface MoviesRepositoryInterface {

    /**
     * Search for movies matching the given query.
     * @param query The search term (movie title)
     * @return List of movies matching the query
     */
    suspend fun searchMovies(query: String): List<Movie>
}
