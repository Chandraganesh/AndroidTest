package lu.post.eval.data.api

import lu.post.eval.BuildConfig
import lu.post.eval.data.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for the OMDB API.
 * Documentation: http://www.omdbapi.com
 */
interface MoviesApiService {

    /**
     * Search for movies by title.
     * @param apiKey The OMDB API key (defaults to BuildConfig value)
     * @param search The search query (movie title)
     * @return A response containing a list of matching movies
     */
    @GET(".")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY,
        @Query("s") search: String
    ): MoviesResponseDto

    companion object {
        /** Base URL for the OMDB API */
        val BASE_URL: String = BuildConfig.OMDB_BASE_URL
    }
}
