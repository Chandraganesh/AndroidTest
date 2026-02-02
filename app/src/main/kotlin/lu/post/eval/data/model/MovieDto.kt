package lu.post.eval.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lu.post.eval.domain.model.Movie

/**
 * Data Transfer Object for movie data from OMDB API.
 * Maps JSON fields to Kotlin properties using @SerialName annotations.
 */
@Serializable
data class MovieDto(
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("imdbID") val imdbId: String,
    @SerialName("Type") val type: String,
    @SerialName("Poster") val poster: String
) {
    /**
     * Converts this DTO to a domain model.
     * @return The corresponding [Movie] domain object
     */
    fun toDomain(): Movie = Movie(
        title = title,
        year = year,
        imdbId = imdbId,
        type = type,
        poster = poster
    )
}

/**
 * Response wrapper for OMDB API search results.
 */
@Serializable
data class MoviesResponseDto(
    @SerialName("Search") val search: List<MovieDto>? = null,
    @SerialName("totalResults") val totalResults: String? = null,
    @SerialName("Response") val response: String? = null
)
