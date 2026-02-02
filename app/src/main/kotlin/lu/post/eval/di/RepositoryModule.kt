package lu.post.eval.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lu.post.eval.data.repository.MoviesRepository
import lu.post.eval.data.repository.MoviesRepositoryInterface
import javax.inject.Singleton

/**
 * Hilt module for binding repository interfaces to their implementations.
 * Uses @Binds for efficient interface-to-implementation binding.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /** Binds [MoviesRepository] implementation to [MoviesRepositoryInterface] */
    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        moviesRepository: MoviesRepository
    ): MoviesRepositoryInterface
}
