package lu.post.eval.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import lu.post.eval.BuildConfig
import lu.post.eval.data.api.MoviesApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt module providing network-related dependencies.
 * Configures JSON parsing, HTTP client, and Retrofit for API communication.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** Network timeout duration in seconds */
    private const val NETWORK_TIMEOUT_SECONDS = 30L

    /**
     * Provides a configured Json instance for Kotlinx Serialization.
     * - ignoreUnknownKeys: Allows API to add new fields without breaking the app
     * - coerceInputValues: Handles null values for non-nullable fields
     * - isLenient: Allows lenient parsing of malformed JSON
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    /**
     * Provides a configured OkHttpClient with:
     * - HTTP logging in debug builds
     * - 30 second timeouts for connect, read, and write operations
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides a Retrofit instance configured with:
     * - OMDB API base URL
     * - OkHttp client for network calls
     * - Kotlinx Serialization converter for JSON parsing
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MoviesApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    /** Provides the MoviesApiService implementation using Retrofit */
    @Provides
    @Singleton
    fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }
}
