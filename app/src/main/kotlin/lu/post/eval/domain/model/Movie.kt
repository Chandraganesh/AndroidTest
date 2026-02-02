package lu.post.eval.domain.model

/**
 * Domain model representing a movie.
 * This is a clean domain object independent of data layer DTOs.
 *
 * @property title The movie title
 * @property year The release year
 * @property imdbId The unique IMDB identifier
 * @property type The type of media (movie, series, etc.)
 * @property poster URL to the movie poster image
 */
data class Movie(
    val title: String,
    val year: String,
    val imdbId: String,
    val type: String,
    val poster: String
)
